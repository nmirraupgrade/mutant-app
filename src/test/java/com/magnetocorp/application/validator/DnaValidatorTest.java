package com.magnetocorp.application.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.magnetocorp.application.config.TestConfig;
import com.magnetocorp.application.enums.ErrorType;
import com.magnetocorp.application.exception.MalformedDnaException;
import com.magnetocorp.application.validator.DnaValidator;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class, loader = AnnotationConfigContextLoader.class)
@SpringBootTest
public class DnaValidatorTest {

    @Autowired
    private DnaValidator validator;

    @Test(expected = MalformedDnaException.class)
    public void validateNullDNA() {
        try {
            validator.validateDNA(null);
        } catch (MalformedDnaException exception) {
            Assert.assertEquals(exception.getErrorCode(), ErrorType.NULL_DNA.getCode());
            throw exception;
        }
    }

    @Test(expected = MalformedDnaException.class)
    public void validateDNAWithEmptySequence() {
        try {
            String[] malformedSequence = {"actg", "", "acct","ggtc"};
            validator.validateDNA(malformedSequence);
        } catch (MalformedDnaException exception) {
            Assert.assertEquals(exception.getErrorCode(), ErrorType.INVALID_FORMAT.getCode());
            throw exception;
        }
    }

    @Test(expected = MalformedDnaException.class)
    public void validateEmptyDNA() {
        try {
            String[] malformedSequence = {"", "", "",""};
            validator.validateDNA(malformedSequence);
        } catch (MalformedDnaException exception) {
            Assert.assertEquals(exception.getErrorCode(), ErrorType.INVALID_FORMAT.getCode());
            throw exception;
        }
    }

    @Test(expected = MalformedDnaException.class)
    public void validateMalformedDNA() {
        try {
            String[] malformedSequence = {"actg", "actg", "acct"};
            validator.validateDNA(malformedSequence);
        } catch (MalformedDnaException exception) {
            Assert.assertEquals(exception.getErrorCode(), ErrorType.INVALID_FORMAT.getCode());
            throw exception;
        }
    }

    @Test(expected = MalformedDnaException.class)
    public void validateTooSmallDNA() {
        try {
            String[] malformedSequence = {"act", "acg", "acc"};
            validator.validateDNA(malformedSequence);
        } catch (MalformedDnaException exception) {
            Assert.assertEquals(exception.getErrorCode(), ErrorType.INVALID_FORMAT.getCode());
            throw exception;
        }
    }

    @Test
    public void validateValidDNA() {
        String[] validSequence = {"actg", "actg", "acct","ggtc"};
        validator.validateDNA(validSequence);
    }

    @Test(expected = MalformedDnaException.class)
    public void validateInvalidBase() {
        try {
            validator.validateBase('S');
        } catch (MalformedDnaException exception) {
            Assert.assertEquals(exception.getErrorCode(), ErrorType.INVALID_BASE.getCode());
            throw exception;
        }
    }

    @Test(expected = MalformedDnaException.class)
    public void validateInvalidBaseTestEntireDNA() {
        String[] invalid6x6InvalidChar = {"atgcga", "cagtgc", "ttatgt", "agaagg", "ccccta", "tcacYg"};
        try {
            for (int i = 0; i < invalid6x6InvalidChar.length; i++) {
                for (int j = 0; j < invalid6x6InvalidChar[i].length(); j++) {
                    validator.validateBase(invalid6x6InvalidChar[i].charAt(j));
                }
            }
        } catch (MalformedDnaException exception) {
            Assert.assertEquals(exception.getErrorCode(), ErrorType.INVALID_BASE.getCode());
            throw exception;
        }
    }

    @Test
    public void validateValidBase() {
        validator.validateBase('G');
        validator.validateBase('A');
        validator.validateBase('C');
        validator.validateBase('T');
    }

}
