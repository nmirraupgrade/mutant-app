package com.magnetocorp.application.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * Request that contains the DNA to be analyzed
 * 
 * @author Nadia Mirra
 */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Request {
    private String[] dna;

}
