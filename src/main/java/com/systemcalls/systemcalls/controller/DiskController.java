package com.systemcalls.systemcalls.controller;


import com.systemcalls.systemcalls.domain.response.DiskSpaceUsageResponse;
import com.systemcalls.systemcalls.service.DiskService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<DiskSpaceUsageResponse> getDiskSpaceUsage(){
        long diskSpaceUsedInMb = diskService.getDiskSpaceUsedInMb();
        BigDecimal diskSpacePercentageUsage = diskService.getDiskSpacePercentageUsed();
        DiskSpaceUsageResponse diskSpaceUsageResponse = buildDiskSpaceUsageResponse(
                diskSpaceUsedInMb,diskSpacePercentageUsage);
        return ResponseEntity.ok(diskSpaceUsageResponse);
    }

    public DiskSpaceUsageResponse buildDiskSpaceUsageResponse(long diskSpaceUsedInMb,
                                                              BigDecimal diskSpacePercentageUsage){
        DiskSpaceUsageResponse diskSpaceUsageResponse = new DiskSpaceUsageResponse();
        diskSpaceUsageResponse.setDiskSpaceUsedInMb(diskSpaceUsedInMb);
        diskSpaceUsageResponse.setDiskSpacePercentageUsage(diskSpacePercentageUsage);
        return diskSpaceUsageResponse;
    }
}

