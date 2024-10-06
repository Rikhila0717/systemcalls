package com.systemcalls.systemcalls.service;

import com.sun.management.OperatingSystemMXBean;
import com.systemcalls.systemcalls.service.iface.iCpuControllerService;
import org.springframework.stereotype.Service;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class CpuControllerService implements iCpuControllerService {

    @Override
    public BigDecimal getCpuUsage() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double cpuPercentageUsage = operatingSystemMXBean.getCpuLoad()*100;
        BigDecimal formattedCpuPercentageUsage = BigDecimal.valueOf(cpuPercentageUsage).setScale(2,
                RoundingMode.HALF_UP);
        return formattedCpuPercentageUsage;
    }
}
