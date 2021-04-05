package com.encuesta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "pregunta")
public class Pregunta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Pregunta() {
		// TODO Auto-generated constructor stub
	}
	
	public Pregunta(int id) {
		super();
		this.id = id;
	}

	public Pregunta(int id, String nombre, int estado, String lastUser, CategoriaPregunta idCategoria, int order) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.estado = estado;
		this.lastUser = lastUser;
		this.idCategoria = idCategoria;
		this.order = order;
	}

	@Id
	@Column(name = "idpregunta", nullable = false)
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	@JsonProperty("id_pregunta")
	private int id;	

	@Column(name = "Nombre", nullable = false)
	@JsonProperty("nombre_pregunta")
	private String nombre;
	
	@Column(name = "Estado")
	@JsonProperty("estado")
	private int estado;
	
	@Column(name = "lastuser")
	@JsonProperty("last_user")
	private String lastUser;
	
	@JoinColumn(name = "idcategoria")
	@ManyToOne
	@JsonProperty("id_categoria")
	private CategoriaPregunta idCategoria;
	
	@Column(name = "Orden")
	@JsonProperty("order")
	private int order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public CategoriaPregunta getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(CategoriaPregunta idCategoria) {
		this.idCategoria = idCategoria;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "Pregunta [id=" + id + ", nombre=" + nombre + ", estado=" + estado + ", lastUser=" + lastUser
				+ ", idCategoria=" + idCategoria + ", order=" + order + "]";
	}
	
	@PrePersist
	public void onPreSave() {
		this.estado=1;
	}

}
