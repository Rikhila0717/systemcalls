package com.systemcalls.systemcalls.service;

import com.systemcalls.systemcalls.service.iface.iMemoryService;
import org.springframework.stereotype.Service;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class MemoryService implements iMemoryService {

    OperatingSystemMXBean operatingSystemMXBean =
            ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
    @Override
    public long getMemoryUsedInMb() {

        long totalMemory = operatingSystemMXBean.getTotalMemorySize();
        long freeMemory = operatingSystemMXBean.getFreeMemorySize();
        long memoryUsedInMb = (totalMemory-freeMemory)/(1024*1024);
        return memoryUsedInMb;

    }

    @Override
    public BigDecimal getMemoryPercentageUsed() {
        double totalMemory = operatingSystemMXBean.getTotalMemorySize();
        double freeMemory = operatingSystemMXBean.getFreeMemorySize();
        double memoryUsed = totalMemory-freeMemory;
        double memoryUsedInMb = memoryUsed/(1024*1024);
        double totalMemoryInMb = totalMemory/(1024*1024);
        double memoryPercentageUsage = (memoryUsedInMb/totalMemoryInMb)*100;
        BigDecimal formattedMemoryPercentageUsage = BigDecimal.valueOf(memoryPercentageUsage)
                .setScale(2, RoundingMode.HALF_UP);
        return formattedMemoryPercentageUsage;
    }





}
