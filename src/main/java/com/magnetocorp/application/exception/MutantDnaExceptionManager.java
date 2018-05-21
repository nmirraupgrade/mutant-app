package com.magnetocorp.application.exception;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.magnetocorp.application.response.ErrorResponse;

/**
 * A centralized exception manager which maps custom exceptions to the right HTTP Status code.
 *
 * @author Nadia Mirra
 */
@ControllerAdvice
public class MutantDnaExceptionManager {

    /**
     * Catch Invalid DNA validation
     */
    @ExceptionHandler({ MalformedDnaException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @Order(value = Ordered.HIGHEST_PRECEDENCE)
    public ErrorResponse invalidDNA(MalformedDnaException exception) {
        return new ErrorResponse(exception.getErrorMessage(), HttpStatus.BAD_REQUEST);
    }
}