package com.magnetocorp.application.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;


/**
 * Response to be returned if some error occurs
 * 
 * @author Nadia Mirra
 */
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "error")
public class ErrorResponse implements Serializable {
    private static final long serialVersionUID = 2493914763133897001L;

    private String erroMessage;
    private HttpStatus httpStatus;

}
