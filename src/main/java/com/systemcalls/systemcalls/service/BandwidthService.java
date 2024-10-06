package com.systemcalls.systemcalls.service;


import com.systemcalls.systemcalls.service.iface.iBandwidthService;


import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class BandwidthService implements iBandwidthService {

    private HardwareAbstractionLayer hardwareAbstractionLayer;
    private double previousTime;
    private double previousSentBytes;
    private double previousReceivedBytes;

    public BandwidthService() {
        SystemInfo systemInfo = new SystemInfo();
        hardwareAbstractionLayer = systemInfo.getHardware();
        this.previousTime = System.currentTimeMillis();
        this.previousReceivedBytes=0;
        this.previousSentBytes=0;
    }

    @Override
    public BigDecimal getReceivedBandwidth() {
        double currentReceivedBytes=0;
        for (NetworkIF networkIF: hardwareAbstractionLayer.getNetworkIFs()){
                currentReceivedBytes+=getReceivedBytes(networkIF);
        }
        double currentTime = System.currentTimeMillis();
        double timeIntervalInSeconds = (currentTime-previousTime)/1000;
        if(currentReceivedBytes<previousReceivedBytes){
            previousReceivedBytes=currentReceivedBytes;
            previousTime=currentTime;
            return BigDecimal.ZERO;
        }
        double receivedBandwidth = (currentReceivedBytes-previousReceivedBytes)*8/timeIntervalInSeconds;
        double receivedBandwidthInMbps = receivedBandwidth/1000000;
        previousReceivedBytes = receivedBandwidth;
        previousTime=currentTime;
        return BigDecimal.valueOf(receivedBandwidthInMbps).setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public BigDecimal getSentBandwidth() {
        double currentSentBytes = 0;
        for(NetworkIF networkIF: hardwareAbstractionLayer.getNetworkIFs()){
            currentSentBytes+=getSentBytes(networkIF);
        }
        double currentTime = System.currentTimeMillis();
        double timeIntervalInSeconds = (currentTime-previousTime)/1000;
        double sentBandwidth = (currentSentBytes-previousSentBytes)*8/timeIntervalInSeconds;
        double sentBandwidthInMbps = sentBandwidth/1000000;
        previousSentBytes = currentSentBytes;
        previousTime = currentTime;
        return BigDecimal.valueOf(sentBandwidth).setScale(2,RoundingMode.HALF_UP);
    }

    public double getReceivedBytes(NetworkIF networkIF){
        return networkIF.getBytesRecv();
    }

    public double getSentBytes(NetworkIF networkIF){
        return networkIF.getBytesSent();
    }


}
