package com.encuesta.security;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.encuesta.exception.ErrorResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class SaphrivRestExceptionHandler extends ResponseEntityExceptionHandler {

	/*
	 * @Override protected ResponseEntity<Object> handleMethodArgumentNotValid(
	 * MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status,
	 * WebRequest request) { List<String> errors = new ArrayList<String>(); for
	 * (FieldError error : ex.getBindingResult().getFieldErrors()) {
	 * errors.add(error.getField() + ": " + error.getDefaultMessage()); } for
	 * (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
	 * errors.add(error.getObjectName() + ": " + error.getDefaultMessage()); }
	 * 
	 * ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST,
	 * ex.getLocalizedMessage(), errors); return handleExceptionInternal( ex,
	 * apiError, headers, apiError.getStatus(), request); }
	 */

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append("Metodo ");
		builder.append(ex.getMethod());
		builder.append(" no es soportado. Invoca esta url usando metodo ");
		ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

		return new ResponseEntity<>(ErrorResponse.of(builder.toString(), HttpStatus.INTERNAL_SERVER_ERROR),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Override
	protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		String error = ex.getParameterName() + " parameter es requerido.";

		return new ResponseEntity<>(ErrorResponse.of(error, HttpStatus.INTERNAL_SERVER_ERROR),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
