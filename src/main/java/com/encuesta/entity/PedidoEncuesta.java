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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import com.encuesta.json.EncuestaJsonDeserializer;
import com.encuesta.json.EncuestaJsonSerializer;
import com.encuesta.json.UsuarioJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.sun.istack.NotNull;

@Entity
@Table(name = "pedidoEncuesta")
public class PedidoEncuesta implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PedidoEncuesta() {
		// TODO Auto-generated constructor stub
	}

	public PedidoEncuesta(int id, Encuesta idEncuesta, Date fechaPedido, String nombreUsuario, String nombrePuesto,
			String direccionPuesto, Usuario elaboradoPor) {
		super();
		this.id = id;
		this.idEncuesta = idEncuesta;
		this.fechaPedido = fechaPedido;
		this.nombreUsuario = nombreUsuario;
		this.nombrePuesto = nombrePuesto;
		this.direccionPuesto = direccionPuesto;
		this.elaboradoPor = elaboradoPor;
	}

	public PedidoEncuesta(Integer valueOf) {
		this.id=valueOf;
	}

	@Id
	@Column(name = "idpedido", nullable = false)
	@GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY )
	private int id;	

	@NotNull
	@JsonSerialize(using = EncuestaJsonSerializer.class)
	@JsonDeserialize(using = EncuestaJsonDeserializer.class)
	@JoinColumn(name = "idencuesta", nullable = false)
	@ManyToOne
	@JsonProperty("id_encuesta")
	private Encuesta idEncuesta;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fechapedido", nullable = false)
	@JsonProperty("fecha_pedido")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private Date fechaPedido;
	
	@NotNull
	@Column(name = "nombreusuario")
	@JsonProperty("nombre_usuario")
	private String nombreUsuario;
	
	@Column(name = "nombrepuesto")
	@JsonProperty("nombre_puesto")
	private String nombrePuesto;
	
	@Column(name = "direccionpuesto")
	@JsonProperty("direccion_puesto")
	private String direccionPuesto;
	
	@JsonDeserialize(using = UsuarioJsonDeserializer.class)
	@JoinColumn(name = "elaboradopor")
	@ManyToOne
	@JsonProperty("elaborado_por")
	@NotNull
	private Usuario elaboradoPor;
	
	/*Nuevos campos*/
	
	@Column(name = "no_referencia")
	@JsonProperty("referencia")
	private String noReferencia;
	
	@Column(name = "censo")
	@JsonProperty("censo")
	private int censo;
	
	@Column(name = "dui")
	@JsonProperty("dui")
	private String dui;
	
	@Column(name = "nit")
	@JsonProperty("nit")
	private String nit;
	
	@Column(name = "telefono")
	@JsonProperty("telefono")
	private String telefono;
	
	@Column(name = "id_asociacion", updatable = false)
	@JsonProperty("asociacion")
	private int idAsociacion;
	
	@Column(name = "direccion_exacta")
	@JsonProperty("direccion_exacta")
	private String direccionExacta;
	
	@Column(name = "id_actividad_comercial", updatable = false)
	@JsonProperty("actividad_comercial")
	private int idActividadComercial;
	
	@Column(name = "largo")
	@JsonProperty("largo")
	private double largo;
	
	@Column(name = "ancho")
	@JsonProperty("ancho")
	private double ancho;
	
	@Column(name = "is_permiso_comercializacion")
	@JsonProperty("permiso")
	private boolean isPermisoComercial;
	
	@Column(name = "cep")
	@JsonProperty("cep")
	private int cep;
	
	@Column(name = "id_distrito")
	@JsonProperty("id_distrito")
	private int idDistrito;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_inicio")
	@JsonProperty("fecha_inicio")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fechaInicio;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecha_fin")
	@JsonProperty("fecha_fin")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date fechaFin;
	
	@Column(name = "b_nombre")
	@JsonProperty("nombre_beneficiario")
	private String bNombre;
	
	@Column(name = "b_direccion")
	@JsonProperty("direccion_beneficiario")
	private String bDireccion;
	
	@Column(name = "b_dui")
	@JsonProperty("dui_beneficiario")
	private String bDui;
	
	@Column(name = "b_nit")
	@JsonProperty("nit_beneficiario")
	private String bNit;
	
	@Column(name = "b_telefono")
	@JsonProperty("telefono_beneficiario")
	private String bTelefono;

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

	public Date getFechaPedido() {
		return fechaPedido;
	}

	public void setFechaPedido(Date fechaPedido) {
		this.fechaPedido = fechaPedido;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public String getNombrePuesto() {
		return nombrePuesto;
	}

	public void setNombrePuesto(String nombrePuesto) {
		this.nombrePuesto = nombrePuesto;
	}

	public String getDireccionPuesto() {
		return direccionPuesto;
	}

	public void setDireccionPuesto(String direccionPuesto) {
		this.direccionPuesto = direccionPuesto;
	}

	public Usuario getElaboradoPor() {
		return elaboradoPor;
	}

	public void setElaboradoPor(Usuario elaboradoPor) {
		this.elaboradoPor = elaboradoPor;
	}
	

	public String getNoReferencia() {
		return noReferencia;
	}

	public void setNoReferencia(String noReferencia) {
		this.noReferencia = noReferencia;
	}

	public int getCenso() {
		return censo;
	}

	public void setCenso(int censo) {
		this.censo = censo;
	}

	public String getDui() {
		return dui;
	}

	public void setDui(String dui) {
		this.dui = dui;
	}

	public String getNit() {
		return nit;
	}

	public void setNit(String nit) {
		this.nit = nit;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public int getIdAsociacion() {
		return idAsociacion;
	}

	public void setIdAsociacion(int idAsociacion) {
		this.idAsociacion = idAsociacion;
	}

	public String getDireccionExacta() {
		return direccionExacta;
	}

	public void setDireccionExacta(String direccionExacta) {
		this.direccionExacta = direccionExacta;
	}

	public int getIdActividadComercial() {
		return idActividadComercial;
	}

	public void setIdActividadComercial(int idActividadComercial) {
		this.idActividadComercial = idActividadComercial;
	}

	public double getLargo() {
		return largo;
	}

	public void setLargo(double largo) {
		this.largo = largo;
	}

	public double getAncho() {
		return ancho;
	}

	public void setAncho(double ancho) {
		this.ancho = ancho;
	}

	public boolean isPermisoComercial() {
		return isPermisoComercial;
	}

	public void setPermisoComercial(boolean isPermisoComercial) {
		this.isPermisoComercial = isPermisoComercial;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public int getIdDistrito() {
		return idDistrito;
	}

	public void setIdDistrito(int idDistrito) {
		this.idDistrito = idDistrito;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	
	public String getbNombre() {
		return bNombre;
	}

	public void setbNombre(String bNombre) {
		this.bNombre = bNombre;
	}

	public String getbDireccion() {
		return bDireccion;
	}

	public void setbDireccion(String bDireccion) {
		this.bDireccion = bDireccion;
	}

	public String getB_direccion() {
		return bDireccion;
	}

	public void setB_direccion(String bDireccion) {
		this.bDireccion = bDireccion;
	}

	public String getbDui() {
		return bDui;
	}

	public void setbDui(String bDui) {
		this.bDui = bDui;
	}

	public String getbNit() {
		return bNit;
	}

	public void setbNit(String bNit) {
		this.bNit = bNit;
	}

	public String getbTelefono() {
		return bTelefono;
	}

	public void setbTelefono(String bTelefono) {
		this.bTelefono = bTelefono;
	}

	@Override
	public String toString() {
		return "PedidoEncuesta [id=" + id + ", idEncuesta=" + idEncuesta + ", fechaPedido=" + fechaPedido
				+ ", nombreUsuario=" + nombreUsuario + ", nombrePuesto=" + nombrePuesto + ", direccionPuesto="
				+ direccionPuesto + ", elaboradoPor=" + elaboradoPor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((nit == null) ? 0 : nit.hashCode());
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
		PedidoEncuesta other = (PedidoEncuesta) obj;
		if (id != other.id)
			return false;
		if (nit == null) {
			if (other.nit != null)
				return false;
		} else if (!nit.equals(other.nit))
			return false;
		return true;
	}
	
	

}
