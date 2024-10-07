package com.systemcalls.systemcalls.service.iface;

import com.systemcalls.systemcalls.service.MemoryService;

import java.math.BigDecimal;

public interface iMemoryService {
    public long getMemoryUsedInMb();
    public BigDecimal getMemoryPercentageUsed();
}
