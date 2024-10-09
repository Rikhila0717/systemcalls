package com.systemcalls.systemcalls.service;

import com.systemcalls.systemcalls.domain.constants.Constants;
import com.systemcalls.systemcalls.domain.response.ErrorResponse;
import com.systemcalls.systemcalls.service.iface.iMemoryService;
import com.systemcalls.systemcalls.util.ObjectUtils;
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
    @Override
    public long getMemoryUsedInMb() {

        try{
            operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);
            logger.info("Getting memory usage...");
            long totalMemory = operatingSystemMXBean.getTotalMemorySize();
            long freeMemory = operatingSystemMXBean.getFreeMemorySize();
            long memoryUsedInMb = (totalMemory-freeMemory)/ Constants.MB_CONVERSION_FACTOR;
            logger.info("Memory Used In MB:{}", memoryUsedInMb);
            return memoryUsedInMb;
        }
        catch(IllegalArgumentException e){
            logger.error("Error occurred while getting platform MXBean",e);
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_GATEWAY.value(),
                    Constants.PLATFORM_MXBEAN_ERROR,e.getMessage());
            String errorMessage = ObjectUtils.serialize(errorResponse);
            throw new RuntimeException(errorMessage);
        }
        catch(Exception e){
            logger.error("Error occurred while getting Memory Usage",e);
            ErrorResponse errorResponse = new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),Constants.MEMORY_USAGE_ERROR,
                    e.getMessage());
            String errorMessage = ObjectUtils.serialize(errorResponse);
            throw new RuntimeException(errorMessage);
        }
    }

    @Override
    public BigDecimal getMemoryPercentageUsed() {
        try{
            operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);
            logger.info("Getting memory percentage usage...");
            double totalMemory = operatingSystemMXBean.getTotalMemorySize();
            double freeMemory = operatingSystemMXBean.getFreeMemorySize();
            double memoryUsed = totalMemory-freeMemory;
            double memoryPercentageUsage = (memoryUsed/totalMemory)*100;
            logger.info("Memory Percentage Used :{}",memoryPercentageUsage);
            return BigDecimal.valueOf(memoryPercentageUsage).setScale(2, RoundingMode.HALF_UP);
        } catch(IllegalArgumentException e){
            logger.error("Error occurred while getting platform MXBean",e);
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_GATEWAY.value(),
                    Constants.PLATFORM_MXBEAN_ERROR,e.getMessage());
            String errorMessage = ObjectUtils.serialize(errorResponse);
            throw new RuntimeException(errorMessage);
        }
        catch(Exception e){
            logger.error("Error occurred while getting Memory Percentage Usage",e);
            ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    Constants.MEMORY_PERCENTAGE_USAGE_ERROR, e.getMessage());
            String errorMessage = ObjectUtils.serialize(errorResponse);
            throw new RuntimeException(errorMessage);
        }
    }
}
