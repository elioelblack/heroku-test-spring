package com.encuesta.exception;

public class ReportFileNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReportFileNotFoundException(String message) {
		super(message);
	}

	public ReportFileNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}
}