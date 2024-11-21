package com.systemcalls.systemcalls.controller;

import com.systemcalls.systemcalls.service.CpuService;
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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class CpuControllerTest {


    private MockMvc mockMvc;

    @Mock
    private CpuService cpuService;

    @InjectMocks
    private CpuController cpuController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(cpuController).build();
    }

    @Test
    void testGetCpuUsage_ReturnsSuccessResponse() throws Exception {
        BigDecimal testCpuUsage = new BigDecimal("75.5");
        when(cpuService.getCpuUsage()).thenReturn(testCpuUsage);
        ResultActions resultActions = mockMvc.perform(get("/systemcalls/cpu/usage")
                .accept(MediaType.APPLICATION_JSON));
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.body.cpuPercentageUsage").value(testCpuUsage));
    }

}