package com.systemcalls.systemcalls.service.iface;

import java.math.BigDecimal;

public interface iDiskService {
    public long getDiskSpaceUsedInMb();
    public BigDecimal getDiskSpacePercentageUsed();
}
