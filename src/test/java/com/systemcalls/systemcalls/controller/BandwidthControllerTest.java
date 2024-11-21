package com.systemcalls.systemcalls.controller;

import com.systemcalls.systemcalls.service.BandwidthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class BandwidthControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BandwidthService bandwidthService;

    @InjectMocks
    private BandwidthController bandwidthController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bandwidthController).build();
    }



    @Test
    void testGetBandwidthUsage_ReturnsSuccessResponse() throws Exception {
        BigDecimal testDownloadBandwidthInMbps = new BigDecimal("50.0");
        BigDecimal testUploadBandwidthInMbps = new BigDecimal("75.5");
        when(bandwidthService.getDownloadBandwidth()).thenReturn(testDownloadBandwidthInMbps);
        when(bandwidthService.getUploadBandwidth()).thenReturn(testUploadBandwidthInMbps);
        ResultActions resultActions = mockMvc.perform(get("/systemcalls/bandwidth/usage")
                .accept(MediaType.APPLICATION_JSON));
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.body.uploadBandwidthInMbps").value(testUploadBandwidthInMbps))
                .andExpect(jsonPath("$.body.downloadBandwidthInMbps").value(testDownloadBandwidthInMbps));

    }
}