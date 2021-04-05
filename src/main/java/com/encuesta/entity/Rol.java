package com.encuesta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "rol_usuario")
public class Rol implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Rol() {
		// TODO Auto-generated constructor stub
	}
	
	public Rol(int id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name = "idrol", nullable = false)
	@JsonProperty("id_rol")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
	private int id;

	@Column(name = "nombrerol", nullable = false)
	@JsonProperty("nombre_rol")
	private String nombreRol;
	
	@Column(name = "descripcionrol", nullable = true)
	@JsonProperty("descripcion_rol")
	private String descripcionRol;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombreRol() {
		return nombreRol;
	}

	public void setNombreRol(String nombreRol) {
		this.nombreRol = nombreRol;
	}

	public String getDescripcionRol() {
		return descripcionRol;
	}

	public void setDescripcionRol(String descripcionRol) {
		this.descripcionRol = descripcionRol;
	}

	@Override
	public String toString() {
		return "Rol [id=" + id + ", nombreRol=" + nombreRol + ", descripcionRol=" + descripcionRol + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((descripcionRol == null) ? 0 : descripcionRol.hashCode());
		result = prime * result + id;
		result = prime * result + ((nombreRol == null) ? 0 : nombreRol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Rol other = (Rol) obj;
		if (descripcionRol == null) {
			if (other.descripcionRol != null)
				return false;
		} else if (!descripcionRol.equals(other.descripcionRol))
			return false;
		if (id != other.id)
			return false;
		if (nombreRol == null) {
			if (other.nombreRol != null)
				return false;
		} else if (!nombreRol.equals(other.nombreRol))
			return false;
		return true;
	}
	
	

}
