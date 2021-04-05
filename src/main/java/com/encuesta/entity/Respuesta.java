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

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Respuesta")
public class Respuesta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Respuesta() {
		// TODO Auto-generated constructor stub
	}
	
	public Respuesta(int id) {
		super();
		this.id = id;
	}
	
	public Respuesta(int id, String nombre, int estado, int tipoRespuesta, String lastUser, int order,
			Pregunta idPregunta) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.estado = estado;
		this.tipoRespuesta = tipoRespuesta;
		this.lastUser = lastUser;
		this.order = order;
		this.idPregunta = idPregunta;
	}

	@Id
	@Column(name = "idrespuesta", nullable = false)
	@JsonProperty("id_respuesta")
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
	private int id;	

	@Column(name = "Nombre", nullable = false)
	@JsonProperty("nombre_respuesta")
	private String nombre;
	
	@Column(name = "Estado")
	@JsonProperty("estado")
	private int estado;
	
	@Column(name = "tiporespuesta")
	@JsonProperty("tipo_respuesta")
	private int tipoRespuesta;
	
	@Column(name = "lastuser")
	@JsonProperty("last_user")
	private String lastUser;
	
	@Column(name = "Orden")
	@JsonProperty("order")
	private int order;
	
	@JoinColumn(name = "idpregunta")
	@ManyToOne
	@JsonProperty("id_pregunta")
	private Pregunta idPregunta;

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

	public int getTipoRespuesta() {
		return tipoRespuesta;
	}

	public void setTipoRespuesta(int tipoRespuesta) {
		this.tipoRespuesta = tipoRespuesta;
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

	public Pregunta getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Pregunta idPregunta) {
		this.idPregunta = idPregunta;
	}

	@Override
	public String toString() {
		return "Respuesta [id=" + id + ", nombre=" + nombre + ", estado=" + estado + ", tipoRespuesta=" + tipoRespuesta
				+ ", lastUser=" + lastUser + ", order=" + order + ", idPregunta=" + idPregunta + "]";
	}
	
	@PrePersist
	public void onPreSave(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		this.lastUser = auth.getName();
		this.tipoRespuesta=1;
		this.estado=1;
	}

}
