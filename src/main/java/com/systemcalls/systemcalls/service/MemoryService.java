package com.systemcalls.systemcalls.service;

import com.systemcalls.systemcalls.domain.constants.Constants;
import com.systemcalls.systemcalls.domain.response.ErrorResponse;
import com.systemcalls.systemcalls.service.iface.iMemoryService;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.systemcalls.systemcalls.util.LoggerUtil.logger;

@Service
public class MemoryService implements iMemoryService {

    private OperatingSystemMXBean operatingSystemMXBean;
    @PostConstruct
    public void init(){
        try{
            operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);

        }catch(IllegalArgumentException e){
            logger.error("Error occurred while getting platform MXBean",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.PLATFORM_MXBEAN_ERROR,e.getMessage())));
        }
    }

    @Override
    public long getMemoryUsedInMb() {

        try{
            logger.info("Getting memory usage...");
            long totalMemory = operatingSystemMXBean.getTotalMemorySize();
            long freeMemory = operatingSystemMXBean.getFreeMemorySize();
            long memoryUsedInMb = (totalMemory-freeMemory)/ Constants.MB_CONVERSION_FACTOR;
            logger.info("Memory Used In MB:{}", memoryUsedInMb);
            return memoryUsedInMb;
        }
        catch(Exception e){
            logger.error("Error occurred while getting Memory Usage",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),Constants.MEMORY_USAGE_ERROR,
                    e.getMessage())));
        }
    }

    @Override
    public BigDecimal getMemoryPercentageUsed() {
        try{
            logger.info("Getting memory percentage usage...");
            double totalMemory = operatingSystemMXBean.getTotalMemorySize();
            double freeMemory = operatingSystemMXBean.getFreeMemorySize();
            double memoryUsed = totalMemory-freeMemory;
            double memoryPercentageUsage = (memoryUsed/totalMemory)*100;
            logger.info("Memory Percentage Used :{}",memoryPercentageUsage);
            return BigDecimal.valueOf(memoryPercentageUsage).setScale(2, RoundingMode.HALF_UP);
        } catch(Exception e){
            logger.error("Error occurred while getting Memory Percentage Usage",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.MEMORY_PERCENTAGE_USAGE_ERROR, e.getMessage())));
        }
    }
}
