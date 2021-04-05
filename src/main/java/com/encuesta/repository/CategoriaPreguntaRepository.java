package com.encuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encuesta.entity.CategoriaPregunta;
import com.encuesta.entity.Encuesta;

public interface CategoriaPreguntaRepository extends JpaRepository<CategoriaPregunta, Integer>{

	List<CategoriaPregunta> findByidEncuesta(Encuesta encuesta);
}
