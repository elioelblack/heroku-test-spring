package com.encuesta.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.encuesta.entity.Encuesta;

public class EncuestaValidator implements Validator{

	public EncuestaValidator() {
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
		Encuesta patient = (Encuesta)target;

        if (patient.getClass() == null){
			errors.rejectValue("Encuesta", "id", "Id No puede ser null");
		}
		
	}

}
