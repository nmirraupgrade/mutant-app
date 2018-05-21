package com.magnetocorp.application.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the different types of invalid DNA
 * 
 * @author Nadia Mirra
 */

@RequiredArgsConstructor
@Getter
public enum ErrorType {

    NULL_DNA(1000, "The DNA sequence is a required field"),
    INVALID_FORMAT(1001, "The DNA format is invalid."),
    INVALID_BASE(1002, "The DNA sequence contains an invalid base");

    private final int code;
    private final String message;

}
