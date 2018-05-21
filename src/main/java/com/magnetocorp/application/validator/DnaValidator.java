package com.magnetocorp.application.validator;

import org.springframework.stereotype.Component;

import com.magnetocorp.application.enums.ErrorType;
import com.magnetocorp.application.exception.MalformedDnaException;
import com.magnetocorp.application.service.MutantService;

/**
 * Validator that decides if a DNA is valid or not
 * 
 * @author Nadia Mirra
 */
@Component
public class DnaValidator {

    private static final char A = 'A';
    private static final char T = 'T';
    private static final char C = 'C';
    private static final char G = 'G';

    /**
     * Decides if a DNA is valid, depending on the size and format.
     * 
     * @param dna contains the DNA to be validated
     * @throws MalformedDnaException if the DNA is null, or if its format does not match compliances
     */
    public void validateDNA(String[] dna) {

        if (dna == null) {
           throw new MalformedDnaException(ErrorType.NULL_DNA.getCode(), ErrorType.NULL_DNA.getMessage());
        }

        if (dna.length < MutantService.MUTANT_CANDIDATE_OCCURRENCES) {
            throw new MalformedDnaException(ErrorType.INVALID_FORMAT.getCode(), ErrorType.INVALID_FORMAT.getMessage());
        }

        for(int i = 0; i < dna.length; i++) {
            if (dna[i].length() != dna.length) {
                throw new MalformedDnaException(ErrorType.INVALID_FORMAT.getCode(),
                                                ErrorType.INVALID_FORMAT.getMessage());
            }
        }
    }

    /**
     * Decides if a base is valid, depending on its value.
     * 
     * @param base the DNA base to be analyzed
     * @throws MalformedDnaException if the base value does not match with any of the expected ones.
     */
    public void validateBase(char base){
        if (A != Character.toUpperCase(base) && T != Character.toUpperCase(base) 
                && C != Character.toUpperCase(base) && G != Character.toUpperCase(base)) {
            throw new MalformedDnaException(ErrorType.INVALID_BASE.getCode(), ErrorType.INVALID_BASE.getMessage());
        }
    }

}
