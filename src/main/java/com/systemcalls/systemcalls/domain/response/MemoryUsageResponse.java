package com.systemcalls.systemcalls.domain.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemoryUsageResponse {
    private long memoryUsedInMb;
    private BigDecimal memoryPercentageUsage;
}
