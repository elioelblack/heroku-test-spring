package com.encuesta.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "Usuario")
public class Usuario implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(Integer id) {
		super();
		this.id = id;
	}

	@Id
	@Column(name = "idusuario", nullable = false)
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
	@JsonProperty("id_user")
	private int id;	

	@Column(name = "username", nullable = false)
	@JsonProperty("username")
	private String username;
	
	@Column(name = "password", nullable = true)
	@JsonProperty("password")
	private String password;
	
	@Column(name = "Nombre", nullable = true)
	@JsonProperty("nombre")
	private String nombre;
	
	@Column(name = "Apellido", nullable = true)
	@JsonProperty("apellido")
	private String apellido;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharegistro", nullable = false)
	@JsonProperty("fecha_registro")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private Date fechaRegistro;
	
	@Column(name = "Activo")
	@JsonProperty("activo")
	private boolean activo;	
	
	//@JsonDeserialize(using = LabWorkgroupJsonDeserializer.class)
	@JoinColumn(name = "idrol")
	@ManyToOne
	@JsonProperty("id_rol")
	private Rol idRol;
	
	@Column(name = "lastuser")
	@JsonProperty("last_user")
	private String lastUser;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Date getFechaRegistro() {
		return fechaRegistro;
	}

	public void setFechaRegistro(Date fechaRegistro) {
		this.fechaRegistro = fechaRegistro;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}	

	public Rol getIdRol() {
		return idRol;
	}

	public void setIdRol(Rol idRol) {
		this.idRol = idRol;
	}	
	
	@PrePersist
	public void onPreSave() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		this.fechaRegistro = new Date();
		this.lastUser = auth.getName();
	}
	
	@PreUpdate
	public void onPreUpdate() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();		
		this.lastUser = auth.getName();
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", username=" + username + ", password=" + password + ", nombre=" + nombre
				+ ", apellido=" + apellido + ", fechaRegistro=" + fechaRegistro + ", activo=" + activo + ", idRol="
				+ idRol + ", lastUser=" + lastUser + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (activo ? 1231 : 1237);
		result = prime * result + ((apellido == null) ? 0 : apellido.hashCode());
		result = prime * result + ((fechaRegistro == null) ? 0 : fechaRegistro.hashCode());
		result = prime * result + id;
		result = prime * result + ((idRol == null) ? 0 : idRol.hashCode());
		result = prime * result + ((lastUser == null) ? 0 : lastUser.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		Usuario other = (Usuario) obj;
		if (activo != other.activo)
			return false;
		if (apellido == null) {
			if (other.apellido != null)
				return false;
		} else if (!apellido.equals(other.apellido))
			return false;
		if (fechaRegistro == null) {
			if (other.fechaRegistro != null)
				return false;
		} else if (!fechaRegistro.equals(other.fechaRegistro))
			return false;
		if (id != other.id)
			return false;
		if (idRol == null) {
			if (other.idRol != null)
				return false;
		} else if (!idRol.equals(other.idRol))
			return false;
		if (lastUser == null) {
			if (other.lastUser != null)
				return false;
		} else if (!lastUser.equals(other.lastUser))
			return false;
		if (nombre == null) {
			if (other.nombre != null)
				return false;
		} else if (!nombre.equals(other.nombre))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

	
	
	

}
