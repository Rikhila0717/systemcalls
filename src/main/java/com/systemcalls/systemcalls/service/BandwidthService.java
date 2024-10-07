package com.systemcalls.systemcalls.service;


import com.systemcalls.systemcalls.domain.constants.Constants;
import com.systemcalls.systemcalls.domain.response.ErrorResponse;
import com.systemcalls.systemcalls.service.iface.iBandwidthService;


import jakarta.annotation.PostConstruct;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.systemcalls.systemcalls.util.LoggerUtil.logger;


@Service
public class BandwidthService implements iBandwidthService {

    private HardwareAbstractionLayer hardwareAbstractionLayer;
    private double previousTime;

    @PostConstruct
    public void init(){
        try{
            logger.info("Getting system and hardware info...");
            SystemInfo systemInfo = new SystemInfo();
            hardwareAbstractionLayer = systemInfo.getHardware();
            this.previousTime = System.currentTimeMillis();
        }catch(Exception e){
            logger.error(Constants.PLATFORM_SYSTEM_ERROR,e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(), Constants.PLATFORM_SYSTEM_ERROR,
                    e.getMessage())));
        }
    }

    @Override
    public BigDecimal getDownloadBandwidth() {
        try{
            logger.info("Getting download bandwidth...");
            double currentReceivedBytes=0;
            for (NetworkIF networkIF: hardwareAbstractionLayer.getNetworkIFs()){
                currentReceivedBytes+=getReceivedBytes(networkIF);
            }
            double currentTime = System.currentTimeMillis();
            double timeIntervalInSeconds = (currentTime-previousTime)/1000;
            double downloadBandwidth = currentReceivedBytes*8/timeIntervalInSeconds;
            double downloadBandwidthInMbps = downloadBandwidth/1000000;
            previousTime=currentTime;
            return BigDecimal.valueOf(downloadBandwidthInMbps).setScale(2, RoundingMode.HALF_UP);
        }catch(Exception e){
            logger.error("Error occurred while getting download bandwidth",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.DOWNLOAD_BANDWIDTH_ERROR, e.getMessage())));
        }

    }

    @Override
    public BigDecimal getUploadBandwidth() {
        try{
            logger.info("Getting upload bandwidth...");
            double currentSentBytes = 0;
            for(NetworkIF networkIF: hardwareAbstractionLayer.getNetworkIFs()){
                currentSentBytes+=getSentBytes(networkIF);
            }
            double currentTime = System.currentTimeMillis();
            double timeIntervalInSeconds = (currentTime-previousTime)/1000;
            double uploadBandwidth = (currentSentBytes)*8/timeIntervalInSeconds;
            double uploadBandwidthInMbps = uploadBandwidth/1000000;
            previousTime = currentTime;
            return BigDecimal.valueOf(uploadBandwidthInMbps).setScale(2,RoundingMode.HALF_UP);
        }catch(Exception e){
            logger.error("Error occurred while getting upload bandwidth",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.UPLOAD_BANDWIDTH_ERROR, e.getMessage())));
        }

    }

    public double getReceivedBytes(NetworkIF networkIF){
        try{
            return networkIF.getBytesRecv();
        }catch(Exception e){
            logger.error(Constants.RECEIVE_BYTES_ERROR,e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.RECEIVE_BYTES_ERROR, e.getMessage())));
        }
    }

    public double getSentBytes(NetworkIF networkIF){
        try{
            return networkIF.getBytesSent();
        }catch(Exception e){
            logger.error(Constants.SEND_BYTES_ERROR,e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.SEND_BYTES_ERROR, e.getMessage())));
        }
    }


}
