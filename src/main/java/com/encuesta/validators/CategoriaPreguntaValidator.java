package com.encuesta.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.encuesta.entity.CategoriaPregunta;

public class CategoriaPreguntaValidator implements Validator{

	public CategoriaPreguntaValidator() {
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
		CategoriaPregunta patient = (CategoriaPregunta)target;

        if (patient.getClass() == null){
			errors.rejectValue("CategoriaPregunta", "id", "Id No puede ser null");
		}
		
	}

}
