package com.smatech.backendapiservice.common.response;

import lombok.Data;

import java.io.Serializable;

import static com.smatech.backendapiservice.common.SystemConstants.FAILURE_INT_VALUE;
import static com.smatech.backendapiservice.common.SystemConstants.SUCCESS_INT_VALUE;


@Data
public class CommonResponse<T> implements Serializable {

    private int statusCode;
    private String message;
    private T result;

    public CommonResponse(){
    }

    public CommonResponse<T> buildSuccessResponse(String message) {
        this.statusCode = SUCCESS_INT_VALUE;
        this.message = message;
        this.result = null;
        return this;
    }

    public CommonResponse<T> buildSuccessResponse(String message, final T result) {
        this.statusCode = SUCCESS_INT_VALUE;
        this.message = message;
        this.result = result;
        return this;
    }

    public CommonResponse<T> buildErrorResponse(String message) {
        this.statusCode = FAILURE_INT_VALUE;
        this.message = message;
        this.result = null;
        return this;
    }

    public CommonResponse<T> buildErrorResponse(String message, final T result) {
        this.statusCode = FAILURE_INT_VALUE;
        this.message = message;
        this.result = result;
        return this;
    }
}
