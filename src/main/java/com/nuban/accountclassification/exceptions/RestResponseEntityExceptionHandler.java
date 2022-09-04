package com.nuban.accountclassification.exceptions;

import com.nuban.accountclassification.dto.CustomErrorResponseDto;
import com.nuban.accountclassification.enumerations.ErrorMessages;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Author : Todimu Isewon
 * Date : 04/09/2022
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class })
    protected ResponseEntity<Object> handleBadRequestAlert(RuntimeException ex, WebRequest request) {
        CustomErrorResponseDto bodyOfResponse = CustomErrorResponseDto
                .builder()
                .code(ErrorMessages.RUNTIME_EXCEPTION.getCode())
                .message(ErrorMessages.RUNTIME_EXCEPTION.getMessage())
                .build();
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
