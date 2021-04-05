package com.encuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encuesta.entity.CategoriaPregunta;
import com.encuesta.entity.Pregunta;

public interface PreguntaRepository extends JpaRepository<Pregunta, Integer>{
	List<Pregunta> findByidCategoria(CategoriaPregunta categ);

}
