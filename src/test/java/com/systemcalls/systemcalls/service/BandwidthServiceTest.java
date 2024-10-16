package com.systemcalls.systemcalls.service;


import com.sun.management.OperatingSystemMXBean;
import com.systemcalls.systemcalls.domain.constants.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;

import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BandwidthServiceTest {

    @Mock
    private SystemInfo systemInfo;

    @Mock
    private HardwareAbstractionLayer hardwareAbstractionLayer;

    @Mock
    private NetworkIF networkIF;

    @InjectMocks
    private BandwidthService bandwidthService;

    @Test
    void getDownloadBandwidth_ReturnsSuccessResponse() {
        long testReceivedBytes = 1024000L;
        double testDownloadBandwidthInMbps = testReceivedBytes * 8.0 / 1000000;
        BigDecimal testDownloadBandwidth = BigDecimal.valueOf(testDownloadBandwidthInMbps)
                .setScale(2, RoundingMode.HALF_UP);
        when(systemInfo.getHardware()).thenReturn(hardwareAbstractionLayer);
        when(hardwareAbstractionLayer.getNetworkIFs()).thenReturn(Arrays.asList(networkIF));
        BandwidthService bandwidthServiceSpy = spy(bandwidthService);
        when(bandwidthServiceSpy.getReceivedBytes(networkIF)).thenReturn(1024000L);
        when(networkIF.getBytesRecv()).thenReturn(1024L);
        when(bandwidthServiceSpy.getDownloadBandwidth()).thenReturn(testDownloadBandwidth);
        BigDecimal actualDownloadBandwidthInMbps = bandwidthServiceSpy.getDownloadBandwidth();
        assertEquals(testDownloadBandwidth, actualDownloadBandwidthInMbps);
    }

    @Test
    void getUploadBandwidth_ReturnsSuccessResponse() {
        long testSentBytes = 1024000L;
        double testUploadBandwidthInMbps = testSentBytes * 8.0 / 1000000;
        BigDecimal testUploadBandwidth = BigDecimal.valueOf(testUploadBandwidthInMbps)
                .setScale(2, RoundingMode.HALF_UP);
        when(systemInfo.getHardware()).thenReturn(hardwareAbstractionLayer);
        when(hardwareAbstractionLayer.getNetworkIFs()).thenReturn(Arrays.asList(networkIF));
        BandwidthService bandwidthServiceSpy = spy(bandwidthService);
        when(bandwidthServiceSpy.getSentBytes(networkIF)).thenReturn(1024000L);
        when(networkIF.getBytesRecv()).thenReturn(1024L);
        when(bandwidthServiceSpy.getUploadBandwidth()).thenReturn(testUploadBandwidth);
        BigDecimal actualUploadBandwidthInMbps = bandwidthServiceSpy.getUploadBandwidth();
        assertEquals(testUploadBandwidth, actualUploadBandwidthInMbps);
    }

    @Test
    void getDownloadBandwidth_ReturnsException(){
        when(systemInfo.getHardware()).thenThrow(new RuntimeException());
        RuntimeException exception = assertThrows(RuntimeException.class,()->
                bandwidthService.getDownloadBandwidth());
        assertTrue(exception.toString().contains(Constants.DOWNLOAD_BANDWIDTH_ERROR));
    }

    @Test
    void getUploadBandwidth_ReturnsException(){
        when(systemInfo.getHardware()).thenThrow(new RuntimeException());
        RuntimeException exception = assertThrows(RuntimeException.class,()->
                bandwidthService.getUploadBandwidth());
        assertTrue(exception.toString().contains(Constants.UPLOAD_BANDWIDTH_ERROR));
    }
}

