package com.smatech.backendapiservice.errorhandler;

import com.smatech.backendapiservice.common.SystemConstants;
import com.smatech.backendapiservice.common.response.CommonResponse;
import com.smatech.backendapiservice.errorhandler.exceptions.BadRequestException;
import com.smatech.backendapiservice.errorhandler.exceptions.UnauthorizedRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class ApplicationControllerAdvice {
    private static final String AN_ERROR_OCCURRED = "An error occurred";

    @ExceptionHandler(BadRequestException.class)
    public CommonResponse badRequest(BadRequestException ex) {
        log.error("A BadRequestException error occurred", ex);
        return new CommonResponse().buildErrorResponse(SystemConstants.FAILURE_MESSAGE,ex.getMessage());
    }

    @ExceptionHandler(UnauthorizedRequestException.class)
    public CommonResponse unauthorizedRequest(UnauthorizedRequestException ex) {
        log.error("A UnauthorizedRequestException error occurred", ex);
        return new CommonResponse().buildErrorResponse(SystemConstants.FAILURE_MESSAGE,ex.getMessage());
    }
}
