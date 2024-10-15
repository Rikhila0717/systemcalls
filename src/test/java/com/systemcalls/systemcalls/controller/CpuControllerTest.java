package com.systemcalls.systemcalls.controller;

import com.systemcalls.systemcalls.domain.response.CpuUsageResponse;
import com.systemcalls.systemcalls.domain.response.SuccessResponse;
import com.systemcalls.systemcalls.service.CpuService;
import jdk.jfr.SettingDefinition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(MockitoExtension.class)
class CpuControllerTest {

    @Mock
    private CpuService cpuService;

    @InjectMocks
    private CpuController cpuController;
    private MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(cpuController).build();
    }

    @Test
    void getCpuUsage_SuccessResponse() throws Exception {

        BigDecimal testCpuPercentage = BigDecimal.valueOf(70.0).setScale(2, RoundingMode.HALF_UP);
        CpuUsageResponse testCpuUsageResponse = buildTestCpuUsageResponse(testCpuPercentage);
        when(cpuService.getCpuUsage()).thenReturn(testCpuPercentage);
        MvcResult mvcResponse = mockMvc.perform(get("/system/calls/cpu/usage")).andReturn();
        MockHttpServletResponse expectedResponse = mvcResponse.getResponse();
        SuccessResponse actualSuccessResponse = cpuController.getCpuUsage();
        assertEquals(expectedResponse.getContentAsString(),
                actualSuccessResponse.getStatusCode());
    }

//    @Test
//    void getCpuUsage_ReturnsRuntimeException(){
//        when(cpuService.getCpuUsage())
//    }
    public CpuUsageResponse buildTestCpuUsageResponse(BigDecimal testCpuPercentage){
        CpuUsageResponse testCpuUsageResponse = CpuUsageResponse.builder().build();
        testCpuUsageResponse.setCpuPercentageUsage(testCpuPercentage);
        return testCpuUsageResponse;
    }

}