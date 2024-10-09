package com.systemcalls.systemcalls.service.iface;


import java.math.BigDecimal;

public interface iBandwidthService {
    public BigDecimal getDownloadBandwidth();
    public BigDecimal getUploadBandwidth();

}
