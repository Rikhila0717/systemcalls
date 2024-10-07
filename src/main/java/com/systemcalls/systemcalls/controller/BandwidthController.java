package com.systemcalls.systemcalls.controller;


import com.systemcalls.systemcalls.domain.response.BandwidthUsageResponse;
import com.systemcalls.systemcalls.domain.response.SuccessResponse;
import com.systemcalls.systemcalls.service.BandwidthService;
import com.systemcalls.systemcalls.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/systemcalls/bandwidth")
public class BandwidthController {

    @Autowired
    BandwidthService bandwidthService;

    @GetMapping("/usage")
    public SuccessResponse getBandwidthUsage(){
        BigDecimal downloadBandwidthInMbps = bandwidthService.getDownloadBandwidth();
        BigDecimal uploadBandwidthInMbps = bandwidthService.getUploadBandwidth();
        BandwidthUsageResponse bandwidthUsageResponse = buildBandwidthUsageResponse
                (uploadBandwidthInMbps,downloadBandwidthInMbps);
        SuccessResponse successResponse = buildSuccessResponse(bandwidthUsageResponse);
        return successResponse;
    }

    public BandwidthUsageResponse buildBandwidthUsageResponse(
            BigDecimal uploadBandwidthInMbps,BigDecimal downloadBandwidthInMbps){
        BandwidthUsageResponse bandwidthUsageResponse = new BandwidthUsageResponse();
        bandwidthUsageResponse.setDownloadBandwidthInMbps(downloadBandwidthInMbps);
        bandwidthUsageResponse.setUploadBandwidthInMbps(uploadBandwidthInMbps);
        return bandwidthUsageResponse;
    }

    public SuccessResponse buildSuccessResponse(BandwidthUsageResponse bandwidthUsageResponse){
        SuccessResponse successResponse = new SuccessResponse(HttpStatus.OK.value(),bandwidthUsageResponse);
        return successResponse;
    }
}
