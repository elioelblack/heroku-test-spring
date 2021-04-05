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

import com.encuesta.entity.Rol;
import com.encuesta.entity.Usuario;
import com.encuesta.exception.ErrorResponse;
import com.encuesta.repository.RolRepository;
import com.encuesta.repository.UsuarioRepository;
import com.encuesta.validators.UsuarioValidator;


@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	public UsuarioController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	private UsuarioValidator usuarioValidator;
	@Autowired
	private RolRepository repoRol;
	
	@RequestMapping("")
	public List<Usuario> getAll() {		
		return usuarioRepository.findAll();
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable(name = "id") Integer id)
			throws Exception {		
		Optional<Usuario> model = usuarioRepository.findById(id);		
		if (!model.isPresent()) {            
			return notFound();
        }

		return new ResponseEntity<>(model.get(), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> create(
			@RequestBody Usuario model) throws Exception {
	
		//Errors errors = validate(model);
		
		//if (errors.hasErrors()) {
		//	return ResponseEntity.status(500).body(errors.getAllErrors());
		//}
		System.out.println(model.getIdRol());
		Rol rol = repoRol.findByNativeQuery(model.getIdRol().getId());
		model.setIdRol(rol);
		model = usuarioRepository.save(model);

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@RequestMapping("/whoami")
	public Object getWhoami() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Usuario user = usuarioRepository.findByUsername(auth.getPrincipal().toString());
		return new ResponseEntity<>(user, HttpStatus.OK);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(			
			@PathVariable Integer id, 
			@RequestBody Usuario model)
			throws Exception {
		
		Optional<Usuario> optionalFromDataBase =  
				usuarioRepository.findById(id);
		
		if (!optionalFromDataBase.isPresent()) {            
           return notFound();
        }
		
		Usuario modelFromDatabase = optionalFromDataBase.get();
		
		//Todo lo que viene en el json lo copio al extraido de la base, de esa manera
		//lo que venga null en el json no se sobreescribira.
		BeanUtils.copyProperties(model, modelFromDatabase, getNullPropertyNames(model));
		
		Errors errors = validate(modelFromDatabase);
		
		if (errors!=null && errors.hasErrors()) {
			return ResponseEntity.status(500).body(errors.getAllErrors());
		}

		model =  usuarioRepository.save(modelFromDatabase);

		return new ResponseEntity<>(model, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Optional<Usuario> workgruoup = usuarioRepository.findById(id);
		if (workgruoup != null) {
			usuarioRepository.deleteById(id);
		}else {
			return new ResponseEntity<Usuario>(HttpStatus.NO_CONTENT);
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
	
	private Errors validate(Usuario model) throws Exception {
		Errors errors = new BeanPropertyBindingResult(model, model.getClass().getSimpleName());
		//usuarioValidator.validate(model, errors);
		return errors;		
	}
	
	private ResponseEntity<?> notFound() {
		return new ResponseEntity<>(ErrorResponse.of("No hay ningun registro.", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

}
