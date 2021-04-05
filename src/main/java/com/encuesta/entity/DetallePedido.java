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

import com.encuesta.json.RespuestaJsonDeserializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Table(name = "detalle_pedido")
public class DetallePedido implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DetallePedido() {
		// TODO Auto-generated constructor stub
	}
	
	@Id
	@Column(name = "idpreguntarespuesta", nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;	

	@JoinColumn(name = "idpregunta", nullable = false)
	@ManyToOne
	@JsonProperty("id_pregunta")
	private Pregunta idPregunta;
	
	@JsonDeserialize(using = RespuestaJsonDeserializer.class)
	@JoinColumn(name = "idrespuesta", nullable = true)
	@ManyToOne
	@JsonProperty("id_respuesta")
	private Respuesta idRespuesta;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "fecharesultado", nullable = false)
	@JsonProperty("fecha_resultado")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
	@CreationTimestamp
	private Date fechaResultado;
	
	@Column(name = "lastuser")
	@JsonProperty("last_user")
	private String lastUser;
	
	@JoinColumn(name = "idpedido")
	@ManyToOne
	@JsonProperty("id_pedido")
	private PedidoEncuesta idPedido;
	
	@Column(name = "numericresult")	
	@JsonProperty("numeric_result")
	private Double numericResult;
	
	@Column(name = "textresult")	
	@JsonProperty("text_result")
	private String textResult;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pregunta getIdPregunta() {
		return idPregunta;
	}

	public void setIdPregunta(Pregunta idPregunta) {
		this.idPregunta = idPregunta;
	}

	public Respuesta getIdRespuesta() {
		return idRespuesta;
	}

	public void setIdRespuesta(Respuesta idRespuesta) {
		this.idRespuesta = idRespuesta;
	}

	public Date getFechaResultado() {
		return fechaResultado;
	}

	public void setFechaResultado(Date fechaResultado) {
		this.fechaResultado = fechaResultado;
	}

	public String getLastUser() {
		return lastUser;
	}

	public void setLastUser(String lastUser) {
		this.lastUser = lastUser;
	}

	public PedidoEncuesta getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(PedidoEncuesta idPedido) {
		this.idPedido = idPedido;
	}

	public Double getNumericResult() {
		return numericResult;
	}

	public void setNumericResult(Double numericResult) {
		this.numericResult = numericResult;
	}

	public String getTextResult() {
		return textResult;
	}

	public void setTextResult(String textResult) {
		this.textResult = textResult;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "DetallePedido [id=" + id + ", idPregunta=" + idPregunta + ", idRespuesta=" + idRespuesta
				+ ", fechaResultado=" + fechaResultado + ", lastUser=" + lastUser + ", idPedido=" + idPedido
				+ ", numericResult=" + numericResult + ", textResult=" + textResult + "]";
	}

}
