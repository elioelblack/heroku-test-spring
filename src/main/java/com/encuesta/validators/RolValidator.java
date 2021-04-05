package com.encuesta.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.encuesta.entity.Rol;

public class RolValidator implements Validator{

	public RolValidator() {
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
		Rol patient = (Rol)target;

        if (patient.getClass() == null){
			errors.rejectValue("Rol", "id", "Id No puede ser null");
		}
		
	}

}
