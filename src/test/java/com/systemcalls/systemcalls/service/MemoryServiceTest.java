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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemoryServiceTest {

    @Mock
    private OperatingSystemMXBean operatingSystemMXBean;

    @InjectMocks
    private MemoryService memoryService;

    private MockedStatic<ManagementFactory> mocked;

    @BeforeEach
    void setup(){
        this.mocked = mockStatic(ManagementFactory.class);
    }

    @AfterEach
    void tearDown(){
        mocked.close();
    }

    @Test
    void getMemoryUsedInMb_ReturnsSuccessResponse() {
        long testTotalMemory = 25000;
        long testFreeMemory = 15000;
        mocked.when(() -> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class))
                .thenReturn(operatingSystemMXBean);
        when(operatingSystemMXBean.getTotalMemorySize()).thenReturn(testTotalMemory);
        when(operatingSystemMXBean.getFreeMemorySize()).thenReturn(testFreeMemory);
        long actualMemoryUsedInMb = memoryService.getMemoryUsedInMb();
        long expectedMemoryUsedInMb = (testTotalMemory-testFreeMemory)/ Constants.MB_CONVERSION_FACTOR;
        assertEquals(expectedMemoryUsedInMb,actualMemoryUsedInMb);
    }

    @Test
    void getMemoryUsedInMb_ReturnsIllegalArgumentException(){
        mocked.when(()-> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class)).
                thenThrow(new IllegalArgumentException());
        RuntimeException exception = assertThrows(RuntimeException.class,
                ()->memoryService.getMemoryUsedInMb());
        assertTrue(exception.toString().contains(Constants.PLATFORM_MXBEAN_ERROR));
    }

    @Test
    void getMemoryUsedInMb_ReturnsException(){
        mocked.when(()-> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class)).
                thenThrow(new RuntimeException());
        RuntimeException exception = assertThrows(RuntimeException.class,
                ()->memoryService.getMemoryUsedInMb());
        assertTrue(exception.toString().contains(Constants.MEMORY_USAGE_ERROR));
    }

    @Test
    void getMemoryPercentageUsed_ReturnsSuccessResponse(){
        long testTotalMemory = 25000;
        long testFreeMemory = 15000;
        mocked.when(()-> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class))
                .thenReturn(operatingSystemMXBean);
        when(operatingSystemMXBean.getTotalMemorySize()).thenReturn(testTotalMemory);
        when(operatingSystemMXBean.getFreeMemorySize()).thenReturn(testFreeMemory);
        double testUsedMemory = testTotalMemory-testFreeMemory;
        double testMemoryPercentageUsage = (testUsedMemory/testTotalMemory)*100;
        BigDecimal actualMemoryPercentageUsage = memoryService.getMemoryPercentageUsed();
        assertEquals(BigDecimal.valueOf(testMemoryPercentageUsage).setScale(2, RoundingMode.HALF_UP),
                actualMemoryPercentageUsage);
    }

    @Test
    void getMemoryPercentageUsed_ReturnsIllegalArgumentException(){
        mocked.when(()-> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class)).
                thenThrow(new IllegalArgumentException());
        RuntimeException exception = assertThrows(RuntimeException.class,
                ()->memoryService.getMemoryPercentageUsed());
        assertTrue(exception.toString().contains(Constants.PLATFORM_MXBEAN_ERROR));
    }

    @Test
    void getMemoryPercentageUsed_ReturnsException(){
        mocked.when(()-> ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class)).
                thenThrow(new RuntimeException());
        RuntimeException exception = assertThrows(RuntimeException.class,
                ()->memoryService.getMemoryPercentageUsed());
        assertTrue(exception.toString().contains(Constants.MEMORY_PERCENTAGE_USAGE_ERROR));
    }

}