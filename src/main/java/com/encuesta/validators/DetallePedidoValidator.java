package com.encuesta.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.encuesta.entity.DetallePedido;

public class DetallePedidoValidator implements Validator{

	public DetallePedidoValidator() {
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
		DetallePedido patient = (DetallePedido)target;

        if (patient.getClass() == null){
			errors.rejectValue("DetallePedido", "id", "Id No puede ser null");
		}
		
	}

}
