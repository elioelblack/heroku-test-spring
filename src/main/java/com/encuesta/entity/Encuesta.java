package com.encuesta.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "encuesta")
public class Encuesta implements Serializable{
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Encuesta(int id) {
		super();
		this.id = id;
	}

	public Encuesta(int id, String nombre, String descripcion, String lastUser, Date fechaEncuesta, Date fechaCreacion,
			Usuario elaboradoPor) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.lastUser = lastUser;
		this.fechaEncuesta = fechaEncuesta;
		this.fechaCreacion = fechaCreacion;
		this.elaboradoPor = elaboradoPor;
	}

	public Encuesta() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@Column(name = "id_encuesta", nullable = false)
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
	private int id;	

	@Column(name = "nombreencuesta", nullable = false)
	@JsonProperty("nombre_encuesta")
	private String nombre;
	
	@Column(name = "descripcion")
	@JsonProperty("descripcion")
	private String descripcion;
	
	@Column(name = "lastuser")
	@JsonProperty("last_user")
	private String lastUser;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechaencuesta", nullable = false)
	@JsonProperty("fecha_encuesta")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	private Date fechaEncuesta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechacreacion")
	@JsonProperty("fecha_creacion")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private Date fechaCreacion;
	
	@JoinColumn(name = "elaboradopor")
	@ManyToOne
	@JsonProperty("elaborado_por")
	private Usuario elaboradoPor;
	
	@JsonProperty("id_categoria")
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@JoinColumn(name = "id_encuesta")
	private Set<CategoriaPregunta> catPregunta;

	public Set<CategoriaPregunta> getCatPregunta() {
		return catPregunta;
	}

	public void setCatPregunta(Set<CategoriaPregunta> catPregunta) {
		this.catPregunta = catPregunta;
	}

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

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public Date getFechaEncuesta() {
		return fechaEncuesta;
	}

	public void setFechaEncuesta(Date fechaEncuesta) {
		this.fechaEncuesta = fechaEncuesta;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Usuario getElaboradoPor() {
		return elaboradoPor;
	}

	public void setElaboradoPor(Usuario elaboradoPor) {
		this.elaboradoPor = elaboradoPor;
	}

	@Override
	public String toString() {
		return "Encuesta [id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", lastUser=" + lastUser
				+ ", fechaEncuesta=" + fechaEncuesta + ", fechaCreacion=" + fechaCreacion + ", elaboradoPor="
				+ elaboradoPor + "]";
	}

	

}
