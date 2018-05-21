package com.magnetocorp.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Custom exception for DNA validation
 *
 * @author Nadia Mirra
 */
@Getter
@AllArgsConstructor
public class MalformedDnaException extends RuntimeException {

    private static final long serialVersionUID = 1703417729413079419L;

    private final int errorCode;
    private final String errorMessage;

}