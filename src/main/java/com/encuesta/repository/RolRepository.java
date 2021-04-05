package com.encuesta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.encuesta.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Integer>{
	@Query(value="Select * from rol_usuario where IdRol = ?1",nativeQuery = true)
	Rol findByNativeQuery(Integer id);
}
