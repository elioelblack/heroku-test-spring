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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.encuesta.entity.CategoriaPregunta;
import com.encuesta.entity.Pregunta;
import com.encuesta.exception.ErrorResponse;
import com.encuesta.repository.PreguntaRepository;
import com.encuesta.validators.PreguntaValidator;


@RestController
@RequestMapping("/pregunta")
public class PreguntaController {

	public PreguntaController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private PreguntaRepository preguntaRepository;
	private PreguntaValidator rolValidator;
	
	@RequestMapping("")
	public List<Pregunta> getAll() {		
		return preguntaRepository.findAll();
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable(name = "id") Integer id)
			throws Exception {		
		Optional<Pregunta> model = preguntaRepository.findById(id);		
		if (!model.isPresent()) {            
			return notFound();
        }

		return new ResponseEntity<>(model.get(), HttpStatus.OK);
	}
	
	@GetMapping("/categoria/{id}")
	public ResponseEntity<?> finByIdCategoria(@PathVariable(name = "id") Integer id)
			throws Exception {		
		List<Pregunta> model = preguntaRepository.findByidCategoria(new CategoriaPregunta(id));		
		if (model.isEmpty()) {            
			return notFound();
        }

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> create(
			@RequestBody Pregunta model) throws Exception {
	
		Errors errors = validate(model);
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (errors.hasErrors()) {
			return ResponseEntity.status(500).body(errors.getAllErrors());
		}

		model.setLastUser(auth.getName());
		model = preguntaRepository.save(model);

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(			
			@PathVariable Integer id, 
			@RequestBody Pregunta model)
			throws Exception {
		
		Optional<Pregunta> optionalFromDataBase =  
				preguntaRepository.findById(id);
		
		if (!optionalFromDataBase.isPresent()) {            
           return notFound();
        }
		
		Pregunta modelFromDatabase = optionalFromDataBase.get();
		
		//Todo lo que viene en el json lo copio al extraido de la base, de esa manera
		//lo que venga null en el json no se sobreescribira.
		BeanUtils.copyProperties(model, modelFromDatabase, getNullPropertyNames(model));
		
		Errors errors = validate(modelFromDatabase);
		
		if (errors!=null && errors.hasErrors()) {
			return ResponseEntity.status(500).body(errors.getAllErrors());
		}

		model =  preguntaRepository.save(modelFromDatabase);

		return new ResponseEntity<>(model, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Optional<Pregunta> workgruoup = preguntaRepository.findById(id);
		if (workgruoup != null) {
			preguntaRepository.deleteById(id);
		}else {
			return new ResponseEntity<Pregunta>(HttpStatus.NO_CONTENT);
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
	
	private Errors validate(Pregunta model) throws Exception {
		Errors errors = new BeanPropertyBindingResult(model, model.getClass().getSimpleName());
		//rolValidator.validate(model, errors);
		return errors;		
	}
	
	private ResponseEntity<?> notFound() {
		return new ResponseEntity<>(ErrorResponse.of("No hay ningun registro.", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

}
