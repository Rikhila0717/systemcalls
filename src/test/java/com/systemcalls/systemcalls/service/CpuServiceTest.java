package com.systemcalls.systemcalls.service;

import com.sun.management.OperatingSystemMXBean;
import com.systemcalls.systemcalls.domain.constants.Constants;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CpuServiceTest {
    @Mock
    private OperatingSystemMXBean operatingSystemMXBean;
    @InjectMocks
    private CpuService cpuService;
    private MockedStatic<ManagementFactory> mocked;

    @BeforeEach
    void setup(){
        this.mocked = mockStatic(ManagementFactory.class);
    }

    @AfterEach
    void tearDown() {
        mocked.close();
    }

    @Test
    void getCpuUsage_ReturnsSuccessResponse() {
        double testCpuPercentageUsage = 0.7;
        mocked.when(() -> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class))
                .thenReturn(operatingSystemMXBean);
        when(operatingSystemMXBean.getCpuLoad()).thenReturn(testCpuPercentageUsage);
        BigDecimal actualCpuPercentageUsage = cpuService.getCpuUsage();
        assertEquals(BigDecimal.valueOf(testCpuPercentageUsage*100)
                .setScale(2,RoundingMode.HALF_UP), actualCpuPercentageUsage);
    }

    @Test
    void getCpuUsage_ReturnsIllegalArgumentException(){
        mocked.when(() -> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class))
                .thenThrow(new IllegalArgumentException());
        RuntimeException exception = assertThrows(RuntimeException.class,()-> cpuService.getCpuUsage());
        assertTrue(exception.toString().contains(Constants.PLATFORM_MXBEAN_ERROR));
    }

    @Test
    void getCpuUsage_ReturnsException(){
        mocked.when(() -> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class))
                .thenThrow(new RuntimeException());
        RuntimeException exception = assertThrows(RuntimeException.class,()-> cpuService.getCpuUsage());
        assertTrue(exception.toString().contains(Constants.CPU_USAGE_ERROR));
    }
}