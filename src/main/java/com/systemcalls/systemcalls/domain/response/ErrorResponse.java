package com.systemcalls.systemcalls.domain.response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @JsonProperty
    private int httpStatus;

    @JsonProperty
    private String error;

    @JsonProperty
    private String message;

}
