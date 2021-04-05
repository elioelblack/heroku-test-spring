package com.encuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encuesta.entity.Pregunta;
import com.encuesta.entity.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta, Integer>{
	List<Respuesta> findByidPregunta(Pregunta preg);
	
}
