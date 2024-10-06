package com.systemcalls.systemcalls.service.iface;

import java.math.BigDecimal;

public interface iMemoryService {
    public long getMemoryUsedInMb();
    public BigDecimal getMemoryPercentageUsed();
}
