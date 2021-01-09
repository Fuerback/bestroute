package com.bexstech.exam.exception.handler;

import com.bexstech.exam.exception.BadRequestException;
import com.bexstech.exam.exception.api.ApiError;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ApiErrorHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {BadRequestException.class})
    public ApiError onError(BadRequestException exception) {
        return buildApiError(exception);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ApiError onError(IllegalArgumentException exception) {
        return buildApiError(exception);
    }

    private ApiError buildApiError(Exception exception) {
        return new ApiError(exception.getMessage());
    }
}
