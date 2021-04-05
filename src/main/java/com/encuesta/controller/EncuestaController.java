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

import com.encuesta.entity.Encuesta;
import com.encuesta.entity.Usuario;
import com.encuesta.exception.ErrorResponse;
import com.encuesta.repository.EncuestaRepository;
import com.encuesta.repository.EncuestaRepository.NameOnly;
import com.encuesta.repository.EncuestaRepository.NameOnly2;
import com.encuesta.repository.UsuarioRepository;
import com.encuesta.validators.EncuestaValidator;


@RestController
@RequestMapping("/encuesta")
public class EncuestaController {

	public EncuestaController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private EncuestaRepository encuestaRepository;
	
	private EncuestaValidator encuestaValidator;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping("")
	public List<Encuesta> getAll() {		
		return encuestaRepository.findAll();
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable(name = "id") Integer id)
			throws Exception {		
		Optional<Encuesta> model = encuestaRepository.findById(id);		
		if (!model.isPresent()) {            
			return notFound();
        }

		return new ResponseEntity<>(model.get(), HttpStatus.OK);
	}
	
	@GetMapping("/dashboard")
	public ResponseEntity<?> loadDashboardInfo()
			throws Exception {		
		NameOnly model = encuestaRepository.loadInfoDashboard();
		
		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@GetMapping("/dashboard2")
	public ResponseEntity<?> loadDashboardInfo2()
			throws Exception {		
		List<NameOnly2> model = encuestaRepository.loadInfoDashboard2();
		
		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> create(
			@RequestBody Encuesta model) throws Exception {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			System.out.println(auth.getName());
			Usuario user = usuarioRepository.findByUsername(auth.getName());
			model.setElaboradoPor(user);
			model.setLastUser(auth.getName());
			
			

			model = encuestaRepository.save(model);

			return new ResponseEntity<>(model, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(			
			@PathVariable Integer id, 
			@RequestBody Encuesta model)
			throws Exception {
		
		Optional<Encuesta> optionalFromDataBase =  
				encuestaRepository.findById(id);
		
		if (!optionalFromDataBase.isPresent()) {            
           return notFound();
        }
		
		Encuesta modelFromDatabase = optionalFromDataBase.get();
		
		//Todo lo que viene en el json lo copio al extraido de la base, de esa manera
		//lo que venga null en el json no se sobreescribira.
		BeanUtils.copyProperties(model, modelFromDatabase, getNullPropertyNames(model));
		
		Errors errors = validate(modelFromDatabase);
		
		if (errors!=null && errors.hasErrors()) {
			return ResponseEntity.status(500).body(errors.getAllErrors());
		}

		model =  encuestaRepository.save(modelFromDatabase);

		return new ResponseEntity<>(model, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Optional<Encuesta> workgruoup = encuestaRepository.findById(id);
		if (workgruoup != null) {
			encuestaRepository.deleteById(id);
		}else {
			return new ResponseEntity<Encuesta>(HttpStatus.NO_CONTENT);
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
	
	private Errors validate(Encuesta model) throws Exception {
		Errors errors = new BeanPropertyBindingResult(model, model.getClass().getSimpleName());
		encuestaValidator.validate(model, errors);
		return errors;		
	}
	
	private ResponseEntity<?> notFound() {
		return new ResponseEntity<>(ErrorResponse.of("No hay ningun registro.", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

}
