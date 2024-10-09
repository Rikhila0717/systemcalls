package com.systemcalls.systemcalls.service.iface;


import com.fasterxml.jackson.core.JsonProcessingException;

import java.math.BigDecimal;

public interface iCpuService {
     public BigDecimal getCpuUsage() throws JsonProcessingException;
}
