package com.systemcalls.systemcalls.controller;

import com.systemcalls.systemcalls.domain.response.CpuUsageResponse;
import com.systemcalls.systemcalls.service.CpuService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<CpuUsageResponse> getCpuUsage(){
        BigDecimal cpuPercentageUsage = cpuService.getCpuUsage();
        CpuUsageResponse cpuUsageResponse = buildCpuUsageResponse(cpuPercentageUsage);
        return ResponseEntity.ok(cpuUsageResponse);
    }

    public CpuUsageResponse buildCpuUsageResponse(BigDecimal cpuPercentageUsage){
        CpuUsageResponse cpuUsageResponse = CpuUsageResponse.builder().build();
        cpuUsageResponse.setCpuPercentageUsage(cpuPercentageUsage);
        return cpuUsageResponse;
    }

}


