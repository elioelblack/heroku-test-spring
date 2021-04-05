/**
 * 
 */
package com.encuesta.exception;

import org.springframework.http.HttpStatus;

/**
 * @author eliezer
 *
 */
public class ErrorResponse {

	// HTTP Response Status Code
    private final HttpStatus status;

    // General Error message
    private final String message;

    protected ErrorResponse(final String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public static ErrorResponse of(final String message, HttpStatus status) {
        return new ErrorResponse(message, status);
    }

    public Integer getStatus() {
        return status.value();
    }

    public String getMessage() {
        return message;
    }
}
