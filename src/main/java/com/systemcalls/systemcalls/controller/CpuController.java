package com.systemcalls.systemcalls.controller;

import com.systemcalls.systemcalls.domain.response.CpuUsageResponse;
import com.systemcalls.systemcalls.service.CpuControllerService;
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
    public CpuControllerService cpuControllerService;

    @GetMapping("/usage")
    public ResponseEntity<CpuUsageResponse> getCpuUsage(){
        BigDecimal cpuPercentageUsage = cpuControllerService.getCpuUsage();
        CpuUsageResponse cpuUsageResponse = new CpuUsageResponse(cpuPercentageUsage);
        return ResponseEntity.ok(cpuUsageResponse);
    }

}


