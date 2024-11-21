package com.systemcalls.systemcalls.service;

import com.systemcalls.systemcalls.domain.constants.Constants;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DiskServiceTest {

    @Mock
    private FileSystem fileSystem;

    @InjectMocks
    private DiskService diskService;


    @Test
    void testGetDiskSpaceUsedInMb_ReturnsSuccessResponse() {
        long testTotalSpace = 1000L * Constants.MB_CONVERSION_FACTOR;
        long testFreeSpace = 400L * Constants.MB_CONVERSION_FACTOR;
        when(fileSystem.getTotalSpace()).thenReturn(testTotalSpace);
        when(fileSystem.getFreeSpace()).thenReturn(testFreeSpace);
        long usedSpaceInMb = diskService.getDiskSpaceUsedInMb();
        assertEquals(600L, usedSpaceInMb);
    }

    @Test
    void testGetDiskSpaceUsedInMb_ReturnsException() {
        when(fileSystem.getTotalSpace()).thenThrow(new RuntimeException());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            diskService.getDiskSpaceUsedInMb();
        });
        assertTrue(exception.getMessage().contains(Constants.DISK_SPACE_USAGE_ERROR));
    }

    @Test
    void testGetDiskSpacePercentageUsed_ReturnsSuccessResponse() {
        double testTotalSpace = 1000L * Constants.MB_CONVERSION_FACTOR;
        double testFreeSpace = 400L * Constants.MB_CONVERSION_FACTOR;
        when(fileSystem.getTotalSpace()).thenReturn((long) testTotalSpace);
        when(fileSystem.getFreeSpace()).thenReturn((long) testFreeSpace);
        BigDecimal percentageUsed = diskService.getDiskSpacePercentageUsed();
        assertEquals(BigDecimal.valueOf(60.00).setScale(2), percentageUsed);
    }

    @Test
    void testGetDiskSpacePercentageUsed_ReturnsException() {
        when(fileSystem.getTotalSpace()).thenThrow(new RuntimeException());
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            diskService.getDiskSpacePercentageUsed();
        });
        assertTrue(exception.getMessage().contains(Constants.DISK_SPACE_PERCENTAGE_USAGE_ERROR));
    }
}