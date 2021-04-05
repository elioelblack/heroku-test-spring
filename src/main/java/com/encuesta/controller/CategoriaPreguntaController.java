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
import com.encuesta.entity.Encuesta;
import com.encuesta.exception.ErrorResponse;
import com.encuesta.repository.CategoriaPreguntaRepository;
import com.encuesta.validators.CategoriaPreguntaValidator;


@RestController
@RequestMapping("/categoria-pregunta")
public class CategoriaPreguntaController {

	public CategoriaPreguntaController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private CategoriaPreguntaRepository categoriaPreguntaRepository;
	private CategoriaPreguntaValidator rolValidator;
	
	@RequestMapping("")
	public List<CategoriaPregunta> getAll() {		
		return categoriaPreguntaRepository.findAll();
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable(name = "id") Integer id)
			throws Exception {		
		Optional<CategoriaPregunta> model = categoriaPreguntaRepository.findById(id);		
		if (!model.isPresent()) {            
			return notFound();
        }

		return new ResponseEntity<>(model.get(), HttpStatus.OK);
	}
	
	@GetMapping("/encuesta/{id}")
	public ResponseEntity<?> findByIdEncuesta(@PathVariable(name = "id") Integer id)
			throws Exception {		
		List<CategoriaPregunta> model = categoriaPreguntaRepository.findByidEncuesta(new Encuesta(id));		
		if (model.isEmpty()) {            
			return notFound();
        }

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> create(
			@RequestBody CategoriaPregunta model) throws Exception {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		

		model.setLastUser(auth.getName());
		model = categoriaPreguntaRepository.save(model);

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(			
			@PathVariable Integer id, 
			@RequestBody CategoriaPregunta model)
			throws Exception {
		
		Optional<CategoriaPregunta> optionalFromDataBase =  
				categoriaPreguntaRepository.findById(id);
		
		if (!optionalFromDataBase.isPresent()) {            
           return notFound();
        }
		
		CategoriaPregunta modelFromDatabase = optionalFromDataBase.get();
		
		//Todo lo que viene en el json lo copio al extraido de la base, de esa manera
		//lo que venga null en el json no se sobreescribira.
		BeanUtils.copyProperties(model, modelFromDatabase, getNullPropertyNames(model));
		
		Errors errors = validate(modelFromDatabase);
		
		if (errors!=null && errors.hasErrors()) {
			return ResponseEntity.status(500).body(errors.getAllErrors());
		}

		model =  categoriaPreguntaRepository.save(modelFromDatabase);

		return new ResponseEntity<>(model, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Optional<CategoriaPregunta> workgruoup = categoriaPreguntaRepository.findById(id);
		if (workgruoup != null) {
			categoriaPreguntaRepository.deleteById(id);
		}else {
			return new ResponseEntity<CategoriaPregunta>(HttpStatus.NO_CONTENT);
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
	
	private Errors validate(CategoriaPregunta model) throws Exception {
		Errors errors = new BeanPropertyBindingResult(model, model.getClass().getSimpleName());
		//rolValidator.validate(model, errors);
		return errors;		
	}
	
	private ResponseEntity<?> notFound() {
		return new ResponseEntity<>(ErrorResponse.of("No hay ningun registro.", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

}
