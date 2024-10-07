package com.systemcalls.systemcalls.util;

import com.systemcalls.systemcalls.domain.response.SuccessResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtil {
    public static SuccessResponse createSuccessResponse(HttpStatus httpStatus,Object body){
        return new SuccessResponse(httpStatus.value(),body);
    }
}
