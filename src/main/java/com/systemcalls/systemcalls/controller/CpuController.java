package com.systemcalls.systemcalls.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.systemcalls.systemcalls.domain.response.BandwidthUsageResponse;
import com.systemcalls.systemcalls.domain.response.CpuUsageResponse;
import com.systemcalls.systemcalls.domain.response.SuccessResponse;
import com.systemcalls.systemcalls.service.CpuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;

@RestController
@RequestMapping("/systemcalls/cpu")
public class CpuController {
    @Autowired
    CpuService cpuService;

    @GetMapping("/usage")
    public SuccessResponse getCpuUsage() throws JsonProcessingException {
        BigDecimal cpuPercentageUsage = cpuService.getCpuUsage();
        CpuUsageResponse cpuUsageResponse = buildCpuUsageResponse(cpuPercentageUsage);
        SuccessResponse successResponse = buildSuccessResponse(cpuUsageResponse);
        return successResponse;
    }

    public CpuUsageResponse buildCpuUsageResponse(BigDecimal cpuPercentageUsage){
        CpuUsageResponse cpuUsageResponse = CpuUsageResponse.builder().build();
        cpuUsageResponse.setCpuPercentageUsage(cpuPercentageUsage);
        return cpuUsageResponse;
    }

    public SuccessResponse buildSuccessResponse(CpuUsageResponse cpuUsageResponse){
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK.value(),cpuUsageResponse);
        return successResponse;
    }

}


