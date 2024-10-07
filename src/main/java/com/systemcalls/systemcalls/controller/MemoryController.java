package com.systemcalls.systemcalls.controller;

import com.systemcalls.systemcalls.domain.response.BandwidthUsageResponse;
import com.systemcalls.systemcalls.domain.response.MemoryUsageResponse;
import com.systemcalls.systemcalls.domain.response.SuccessResponse;
import com.systemcalls.systemcalls.service.MemoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/systemcalls/memory")
public class MemoryController {

    @Autowired
    MemoryService memoryService;

    @GetMapping("/usage")
    public SuccessResponse getMemoryUsage(){
        long memoryUsedInMb = memoryService.getMemoryUsedInMb();
        BigDecimal memoryPercentageUsage = memoryService.getMemoryPercentageUsed();
        MemoryUsageResponse memoryUsageResponse = buildMemoryUsageResponse(memoryUsedInMb,memoryPercentageUsage);
        SuccessResponse successResponse = buildSuccessResponse(memoryUsageResponse);
        return successResponse;
    }

    public MemoryUsageResponse buildMemoryUsageResponse(long memoryUsedInMb, BigDecimal memoryPercentageUsage){
        MemoryUsageResponse memoryUsageResponse = new MemoryUsageResponse();
        memoryUsageResponse.setMemoryUsedInMb(memoryUsedInMb);
        memoryUsageResponse.setMemoryPercentageUsage(memoryPercentageUsage);
        return memoryUsageResponse;
    }

    public SuccessResponse buildSuccessResponse(MemoryUsageResponse memoryUsageResponse){
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK.value(),memoryUsageResponse);
        return successResponse;
    }
}
