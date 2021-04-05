package com.encuesta.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encuesta.entity.Pregunta;
import com.encuesta.entity.Respuesta;
import com.encuesta.exception.ErrorResponse;
import com.encuesta.repository.RespuestaRepository;
import com.encuesta.validators.RespuestaValidator;


@RestController
@RequestMapping("/respuesta")
public class RespuestaController {

	public RespuestaController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private RespuestaRepository respuestaRepository;
	private RespuestaValidator respuestaValidator;
	
	@RequestMapping("")
	public List<Respuesta> getAll() {		
		return respuestaRepository.findAll();
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable(name = "id") Integer id)
			throws Exception {		
		Optional<Respuesta> model = respuestaRepository.findById(id);		
		if (!model.isPresent()) {            
			return notFound();
        }

		return new ResponseEntity<>(model.get(), HttpStatus.OK);
	}
	
	@GetMapping("/pregunta/{id}")
	public ResponseEntity<?> findByIdPregunta(@PathVariable(name = "id") Integer id)
			throws Exception {		
		List<Respuesta> model = respuestaRepository.findByidPregunta(new Pregunta(id));		
		if (model.isEmpty()) {            
			return notFound();
        }

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> create(
			@RequestBody Respuesta model) throws Exception {
	
		Errors errors = validate(model);
		
		if (errors.hasErrors()) {
			return ResponseEntity.status(500).body(errors.getAllErrors());
		}

		model = respuestaRepository.save(model);

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(			
			@PathVariable Integer id, 
			@RequestBody Respuesta model)
			throws Exception {
		
		Optional<Respuesta> optionalFromDataBase =  
				respuestaRepository.findById(id);
		
		if (!optionalFromDataBase.isPresent()) {            
           return notFound();
        }
		
		Respuesta modelFromDatabase = optionalFromDataBase.get();
		
		//Todo lo que viene en el json lo copio al extraido de la base, de esa manera
		//lo que venga null en el json no se sobreescribira.
		BeanUtils.copyProperties(model, modelFromDatabase, getNullPropertyNames(model));
		
		Errors errors = validate(modelFromDatabase);
		
		if (errors!=null && errors.hasErrors()) {
			return ResponseEntity.status(500).body(errors.getAllErrors());
		}

		model =  respuestaRepository.save(modelFromDatabase);

		return new ResponseEntity<>(model, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Optional<Respuesta> workgruoup = respuestaRepository.findById(id);
		if (workgruoup != null) {
			respuestaRepository.deleteById(id);
		}else {
			return new ResponseEntity<Respuesta>(HttpStatus.NO_CONTENT);
		}
		
		return new ResponseEntity<>(workgruoup.get(), HttpStatus.OK);
		
	}
	
	public static String[] getNullPropertyNames (Object source) {
	    final BeanWrapper src = new BeanWrapperImpl(source);
	    java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

	    Set<String> emptyNames = new HashSet<String>();
	    for(java.beans.PropertyDescriptor pd : pds) {
	        Object srcValue = src.getPropertyValue(pd.getName());
	        if (srcValue == null) emptyNames.add(pd.getName());
	    }
	    String[] result = new String[emptyNames.size()];
	    return emptyNames.toArray(result);
	}
	
	private Errors validate(Respuesta model) throws Exception {
		Errors errors = new BeanPropertyBindingResult(model, model.getClass().getSimpleName());
		//respuestaValidator.validate(model, errors);
		return errors;		
	}
	
	private ResponseEntity<?> notFound() {
		return new ResponseEntity<>(ErrorResponse.of("No hay ningun registro.", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

}
