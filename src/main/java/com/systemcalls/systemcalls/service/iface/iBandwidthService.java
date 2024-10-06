package com.systemcalls.systemcalls.service.iface;


import java.math.BigDecimal;

public interface iBandwidthService {
    public BigDecimal getReceivedBandwidth();
    public BigDecimal getSentBandwidth();
}
