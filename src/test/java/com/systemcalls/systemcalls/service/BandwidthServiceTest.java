//package com.systemcalls.systemcalls.service;
//
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import oshi.SystemInfo;
//import oshi.hardware.HardwareAbstractionLayer;
//import oshi.hardware.NetworkIF;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.mockStatic;
//import static org.mockito.Mockito.spy;
//import static org.mockito.Mockito.when;
//
//@ExtendWith(MockitoExtension.class)
//class BandwidthServiceTest {
//
//    @Mock
//    private SystemInfo systemInfo;
//
//    @Mock
//    private HardwareAbstractionLayer hardwareAbstractionLayer;
//
//    @Mock
//    private NetworkIF networkIF;
//
//    @InjectMocks
//    private BandwidthService bandwidthService;
//
//    @Test
//    void getDownloadBandwidth_ReturnsSuccessResponse() {
//        long testReceivedBytes= 1024000L;
//        double testDownloadBandwidthInMbps = testReceivedBytes*8.0/1000000;
//        BigDecimal testDownloadBandwidth = BigDecimal.valueOf(testDownloadBandwidthInMbps)
//                .setScale(2, RoundingMode.HALF_UP);
//        when(systemInfo.getHardware()).thenReturn(hardwareAbstractionLayer);
//        when(hardwareAbstractionLayer.getNetworkIFs()).thenReturn(Arrays.asList(networkIF));
//        BandwidthService bandwidthServiceSpy = spy(bandwidthService);
//        when(bandwidthServiceSpy.getReceivedBytes(networkIF)).thenReturn(1024000L);
//        when(networkIF.getBytesRecv()).thenReturn(1024L);
//        when(bandwidthServiceSpy.getDownloadBandwidth()).thenReturn(testDownloadBandwidth);
//        BigDecimal actualDownloadBandwidthInMbps = bandwidthServiceSpy.getDownloadBandwidth();
//        assertEquals(testDownloadBandwidth,actualDownloadBandwidthInMbps);
//    }
//
//    @Test
//    void getUploadBandwidth() {
//    }
//
//    @Test
//    void getReceivedBytes() {
//    }
//
//    @Test
//    void getSentBytes() {
//    }
//}