package com.systemcalls.systemcalls.service;


import com.systemcalls.systemcalls.service.iface.iDiskService;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DiskService implements iDiskService {

    File file = new File("/");

    @Override
    public long getDiskSpaceUsedInMb() {

        long totalSpace = file.getTotalSpace();
        long freeSpace = file.getFreeSpace();
        long usedSpace = totalSpace-freeSpace;
        long usedSpaceInMb = usedSpace/(1024*1024);
        return usedSpaceInMb;
    }

    @Override
    public BigDecimal getDiskSpacePercentageUsed() {
        double totalSpace = file.getTotalSpace();
        double freeSpace = file.getFreeSpace();
        double usedSpace = totalSpace-freeSpace;
        double spacePercentageUsage = (usedSpace/totalSpace)*100;
        BigDecimal formattedMemoryPercentageUsage = BigDecimal.valueOf(spacePercentageUsage)
                .setScale(2, RoundingMode.HALF_UP);
        return formattedMemoryPercentageUsage;
    }
}
