package com.encuesta.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.encuesta.entity.PedidoEncuesta;

public class PedidoEncuestaValidator implements Validator{

	public PedidoEncuestaValidator() {
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
		PedidoEncuesta patient = (PedidoEncuesta)target;

        if (patient.getClass() == null){
			errors.rejectValue("PedidoEncuesta", "id", "Id No puede ser null");
		}
		
	}

}
