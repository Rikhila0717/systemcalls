package com.systemcalls.systemcalls.service;

import static com.systemcalls.systemcalls.util.LoggerUtil.logger;
import com.sun.management.OperatingSystemMXBean;
import com.systemcalls.systemcalls.domain.constants.Constants;
import com.systemcalls.systemcalls.domain.response.ErrorResponse;
import com.systemcalls.systemcalls.service.iface.iCpuService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CpuService implements iCpuService {

    @Override
    public BigDecimal getCpuUsage() {
        try{
            logger.info("Getting CPU usage...");
            OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(
                    OperatingSystemMXBean.class);
            double cpuPercentageUsage = operatingSystemMXBean.getCpuLoad()*100;
            BigDecimal formattedCpuPercentageUsage = BigDecimal.valueOf(cpuPercentageUsage).setScale(2,
                    RoundingMode.HALF_UP);
            logger.info("CPU Percentage Usage: {}",formattedCpuPercentageUsage);
            return formattedCpuPercentageUsage;
        }
        catch(IllegalArgumentException e){
            logger.error("Error occurred while getting platform MXBean",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(HttpStatus.BAD_GATEWAY.value(),
                    Constants.PLATFORM_MXBEAN_ERROR,e.getMessage())));
        }
        catch(Exception e){
            logger.error("Error occurred while getting CPU Usage",e);
            throw new RuntimeException(String.valueOf(new ErrorResponse(
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),Constants.CPU_USAGE_ERROR,
                    e.getMessage())));
        }

    }
}
