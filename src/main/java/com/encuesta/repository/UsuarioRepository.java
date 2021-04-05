package com.encuesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.encuesta.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
	Usuario findByUsername(String username);
}
