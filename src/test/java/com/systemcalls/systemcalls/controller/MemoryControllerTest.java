package com.systemcalls.systemcalls.controller;

import com.systemcalls.systemcalls.service.MemoryService;
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
class MemoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MemoryService memoryService;

    @InjectMocks
    private MemoryController memoryController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(memoryController).build();
    }


    @Test
    void getMemoryUsage_ReturnsSuccessResponse() throws Exception{
        long testMemoryUsedInMb = 10000L;
        BigDecimal testMemoryPercentageUsage = new BigDecimal("70.0");
        when(memoryService.getMemoryUsedInMb()).thenReturn(testMemoryUsedInMb);
        when(memoryService.getMemoryPercentageUsed()).thenReturn(testMemoryPercentageUsage);
        ResultActions resultActions = mockMvc.perform(get("/systemcalls/memory/usage")
                .accept(MediaType.APPLICATION_JSON));
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.body.memoryUsedInMb").value(testMemoryUsedInMb))
                .andExpect(jsonPath("$.body.memoryPercentageUsage").value(testMemoryPercentageUsage));

    }
}