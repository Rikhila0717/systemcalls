package com.systemcalls.systemcalls.controller;


import com.systemcalls.systemcalls.domain.response.BandwidthUsageResponse;
import com.systemcalls.systemcalls.service.BandwidthService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<BandwidthUsageResponse> getBandwidthUsage(){
        BigDecimal receivedBandwidthInMbps = bandwidthService.getReceivedBandwidth();
        BigDecimal sentBandwidthInMbps = bandwidthService.getSentBandwidth();
        BandwidthUsageResponse bandwidthUsageResponse = buildBandwidthUsageResponse
                (sentBandwidthInMbps,receivedBandwidthInMbps);
        return ResponseEntity.ok(bandwidthUsageResponse);
    }

    public BandwidthUsageResponse buildBandwidthUsageResponse(
            BigDecimal sentBandwidthInMbps,BigDecimal receivedBandwidthInMbps){
        BandwidthUsageResponse bandwidthUsageResponse = new BandwidthUsageResponse();
        bandwidthUsageResponse.setReceivedBandwidthInMbps(receivedBandwidthInMbps);
        bandwidthUsageResponse.setSentBandwidthInMbps(sentBandwidthInMbps);
        return bandwidthUsageResponse;
    }
}
