package com.systemcalls.systemcalls.controller;

import com.systemcalls.systemcalls.service.DiskService;
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
class DiskControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DiskService diskService;

    @InjectMocks
    private DiskController diskController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(diskController).build();
    }

    @Test
    void getDiskSpaceUsage_ReturnsSuccessResponse() throws Exception{

        long testDiskSpaceUsedInMb = 600L;
        BigDecimal testDiskSpacePercentageUsage = new BigDecimal("60.0");
        when(diskService.getDiskSpaceUsedInMb()).thenReturn(testDiskSpaceUsedInMb);
        when(diskService.getDiskSpacePercentageUsed()).thenReturn(testDiskSpacePercentageUsage);
        ResultActions resultActions = mockMvc.perform(get("/systemcalls/disk/space/usage")
                .accept(MediaType.APPLICATION_JSON));
        String responseContent = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("Response Content: " + responseContent);

        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.OK.value()))
                .andExpect(jsonPath("$.body.diskSpaceUsedInMb").value(testDiskSpaceUsedInMb))
                .andExpect(jsonPath("$.body.diskSpacePercentageUsage").value(testDiskSpacePercentageUsage));


    }
}