//package com.systemcalls.systemcalls.service;
//
//import com.systemcalls.systemcalls.domain.constants.Constants;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//
//
//import java.io.File;
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.when;
//
//class DiskServiceTest {
//
//    @Mock
//    private File file;
//    @InjectMocks
//    private DiskService diskService;
//
//
//    @Test
//    void getDiskSpaceUsedInMb_ReturnsSuccessResponse() {
//        long testTotalSpace = 1000;
//        long testFreeSpace = 500;
//        when(new File("/")).thenReturn(file);
//        when(file.getTotalSpace()).thenReturn(testTotalSpace);
//        when(file.getFreeSpace()).thenReturn(testFreeSpace);
//        long actualUsedSpaceInMb = diskService.getDiskSpaceUsedInMb();
//        assertEquals((testTotalSpace-testFreeSpace)/ Constants.MB_CONVERSION_FACTOR, actualUsedSpaceInMb);
//    }
//
//    @Test
//    void getDiskSpaceUsedInMb_ReturnsException(){
//        when(file.getTotalSpace()).thenThrow(new RuntimeException());
//        RuntimeException exception = assertThrows(RuntimeException.class,()-> diskService.getDiskSpaceUsedInMb());
//        assertTrue(exception.toString().contains(Constants.DISK_SPACE_USAGE_ERROR));
//    }
//
//    @Test
//    void getDiskSpaceUsedInMb() {
//    }
//
//    @Test
//    void getDiskSpacePercentageUsed() {
//    }
//}