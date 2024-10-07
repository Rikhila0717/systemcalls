package com.systemcalls.systemcalls.domain.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SuccessResponse {
    private int statusCode;
    private LocalDateTime timestamp;
    private Object body;

    public SuccessResponse(int statusCode,Object body){
        this.statusCode=statusCode;
        this.timestamp = LocalDateTime.now();
        this.body = body;
    }

}
