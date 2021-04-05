package com.encuesta.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.encuesta.json.EncuestaJsonDeserializer;
import com.encuesta.json.EncuestaJsonSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "categoria_pregunta")
public class CategoriaPregunta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CategoriaPregunta() {
		// TODO Auto-generated constructor stub
	}
	
	public CategoriaPregunta(int id) {
		super();
		this.id = id;
	}
	
	public CategoriaPregunta(int id, Encuesta idEncuesta, String nombre, String lastUser, int order) {
		super();
		this.id = id;
		this.idEncuesta = idEncuesta;
		this.nombre = nombre;
		this.lastUser = lastUser;
		this.order = order;
	}


	@Id
	@Column(name = "idcategoria", nullable = false)
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
	@JsonProperty("id_categoria")
	private int id;	
	
	@JsonSerialize(using = EncuestaJsonSerializer.class)
	@JsonDeserialize(using = EncuestaJsonDeserializer.class)
	@JoinColumn(name = "id_encuesta")
	@ManyToOne
	@JsonProperty("id_encuesta")
	private Encuesta idEncuesta;

	@Column(name = "Nombre", nullable = false)
	@JsonProperty("nombre")
	private String nombre;
	
	@Column(name = "lastuser")
	@JsonProperty("last_user")
	private String lastUser;
	
	@Column(name = "Orden")
	@JsonProperty("order")
	private int order;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Encuesta getIdEncuesta() {
		return idEncuesta;
	}

	public void setIdEncuesta(Encuesta idEncuesta) {
		this.idEncuesta = idEncuesta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	@Override
	public String toString() {
		return "CategoriaPregunta [id=" + id + ", idEncuesta=" + idEncuesta + ", nombre=" + nombre + ", lastUser="
				+ lastUser + ", order=" + order + "]";
	}
	
	

}
