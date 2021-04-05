package com.encuesta.controller;

import java.util.Date;
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
import com.encuesta.entity.PedidoEncuesta;
import com.encuesta.entity.Usuario;
import com.encuesta.exception.ErrorResponse;
import com.encuesta.repository.PedidoEncuestaRepository;
import com.encuesta.repository.PedidoEncuestaRepository.NameOnly;
import com.encuesta.repository.PedidoEncuestaRepository.NameOnlyAnswers;
import com.encuesta.repository.UsuarioRepository;
import com.encuesta.validators.PedidoEncuestaValidator;


@RestController
@RequestMapping("/pedido")
public class PedidoEncuestaController {

	public PedidoEncuestaController() {
		// TODO Auto-generated constructor stub
	}
	
	@Autowired
	private PedidoEncuestaRepository pedidoEncuestaRepository;
	
	private PedidoEncuestaValidator pedidoEncuestaValidator;
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@RequestMapping("")
	public List<PedidoEncuesta> getAll() {		
		return pedidoEncuestaRepository.findAll();
	}
		
	@GetMapping("/{id}")
	public ResponseEntity<?> show(@PathVariable(name = "id") Integer id)
			throws Exception {		
		Optional<PedidoEncuesta> model = pedidoEncuestaRepository.findById(id);		
		if (!model.isPresent()) {            
			return notFound();
        }

		return new ResponseEntity<>(model.get(), HttpStatus.OK);
	}
	
	@GetMapping("/preguntas/{id}")
	public ResponseEntity<?> showListaPreguntas(@PathVariable(name = "id") Integer id)
			throws Exception {		
		List<NameOnly> model = pedidoEncuestaRepository.findByNativeQuery(id);		
		

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@GetMapping("/respuestas/{id}")
	public ResponseEntity<?> showListaRespuestas(@PathVariable(name = "id") Integer id)
			throws Exception {		
		List<NameOnlyAnswers> model = pedidoEncuestaRepository.findAnswuerByNativeQuery(id);		
		

		return new ResponseEntity<>(model, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<?> create(
			@RequestBody PedidoEncuesta model) throws Exception {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			System.out.println(auth.getName());
			Usuario user = usuarioRepository.findByUsername(auth.getName());
			model.setElaboradoPor(user);
			model.setFechaPedido(new Date());
			
			model = pedidoEncuestaRepository.save(model);

			return new ResponseEntity<>(model, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(500).body(e.getMessage());
		}
		
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> update(			
			@PathVariable Integer id, 
			@RequestBody PedidoEncuesta model)
			throws Exception {
		
		Optional<PedidoEncuesta> optionalFromDataBase =  
				pedidoEncuestaRepository.findById(id);
		
		if (!optionalFromDataBase.isPresent()) {            
           return notFound();
        }
		
		PedidoEncuesta modelFromDatabase = optionalFromDataBase.get();
		
		//Todo lo que viene en el json lo copio al extraido de la base, de esa manera
		//lo que venga null en el json no se sobreescribira.
		BeanUtils.copyProperties(model, modelFromDatabase, getNullPropertyNames(model));
		
		Errors errors = validate(modelFromDatabase);
		
		if (errors!=null && errors.hasErrors()) {
			return ResponseEntity.status(500).body(errors.getAllErrors());
		}

		model =  pedidoEncuestaRepository.save(modelFromDatabase);

		return new ResponseEntity<>(model, HttpStatus.OK);
		
	}
	
	@GetMapping(value = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Integer id) {
		Optional<PedidoEncuesta> workgruoup = pedidoEncuestaRepository.findById(id);
		if (workgruoup != null) {
			pedidoEncuestaRepository.deleteById(id);
		}else {
			return new ResponseEntity<PedidoEncuesta>(HttpStatus.NO_CONTENT);
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
	
	private Errors validate(PedidoEncuesta model) throws Exception {
		Errors errors = new BeanPropertyBindingResult(model, model.getClass().getSimpleName());
		//pedidoEncuestaValidator.validate(model, errors);
		return errors;		
	}
	
	private ResponseEntity<?> notFound() {
		return new ResponseEntity<>(ErrorResponse.of("No hay ningun registro.", HttpStatus.NOT_FOUND),
				HttpStatus.NOT_FOUND);
	}

}
