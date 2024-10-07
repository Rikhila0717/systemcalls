package com.systemcalls.systemcalls.service.iface;


import com.systemcalls.systemcalls.service.BandwidthService;

import java.math.BigDecimal;

public interface iBandwidthService {
    public BigDecimal getDownloadBandwidth();
    public BigDecimal getUploadBandwidth();

}
