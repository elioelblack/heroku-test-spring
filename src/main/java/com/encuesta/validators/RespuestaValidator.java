package com.encuesta.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.encuesta.entity.Respuesta;

public class RespuestaValidator implements Validator{

	public RespuestaValidator() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Respuesta patient = (Respuesta)target;

        if (patient.getClass() == null){
			errors.rejectValue("Respuesta", "id", "Id No puede ser null");
		}
		
	}

}
