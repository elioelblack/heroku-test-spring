package com.encuesta.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.encuesta.entity.Usuario;

public class UsuarioValidator implements Validator{

	public UsuarioValidator() {
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
		Usuario patient = (Usuario)target;

        if (patient.getClass() == null){
			errors.rejectValue("Usuario", "id", "Id No puede ser null");
		}
		
	}

}
