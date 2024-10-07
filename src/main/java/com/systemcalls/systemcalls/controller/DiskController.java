package com.systemcalls.systemcalls.controller;


import com.systemcalls.systemcalls.domain.response.BandwidthUsageResponse;
import com.systemcalls.systemcalls.domain.response.DiskSpaceUsageResponse;
import com.systemcalls.systemcalls.domain.response.SuccessResponse;
import com.systemcalls.systemcalls.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/systemcalls/disk")
public class DiskController {

    @Autowired
    DiskService diskService;

    @GetMapping("/space/usage")
    public SuccessResponse getDiskSpaceUsage(){
        long diskSpaceUsedInMb = diskService.getDiskSpaceUsedInMb();
        BigDecimal diskSpacePercentageUsage = diskService.getDiskSpacePercentageUsed();
        DiskSpaceUsageResponse diskSpaceUsageResponse = buildDiskSpaceUsageResponse(
                diskSpaceUsedInMb,diskSpacePercentageUsage);
        SuccessResponse successResponse = buildSuccessResponse(diskSpaceUsageResponse);
        return successResponse;
    }

    public DiskSpaceUsageResponse buildDiskSpaceUsageResponse(long diskSpaceUsedInMb,
                                                              BigDecimal diskSpacePercentageUsage){
        DiskSpaceUsageResponse diskSpaceUsageResponse = new DiskSpaceUsageResponse();
        diskSpaceUsageResponse.setDiskSpaceUsedInMb(diskSpaceUsedInMb);
        diskSpaceUsageResponse.setDiskSpacePercentageUsage(diskSpacePercentageUsage);
        return diskSpaceUsageResponse;
    }

    public SuccessResponse buildSuccessResponse(DiskSpaceUsageResponse diskSpaceUsageResponse){
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK.value(),diskSpaceUsageResponse);
        return successResponse;
    }
}

