package com.encuesta.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.encuesta.entity.Pregunta;

public class PreguntaValidator implements Validator{

	public PreguntaValidator() {
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
		Pregunta patient = (Pregunta)target;

        if (patient.getClass() == null){
			errors.rejectValue("Pregunta", "id", "Id No puede ser null");
		}
		
	}

}
