package com.encuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.encuesta.entity.PedidoEncuesta;
import com.fasterxml.jackson.annotation.JsonProperty;


public interface PedidoEncuestaRepository extends JpaRepository<PedidoEncuesta, Integer>{
	
	@Query(value = "select tt.`Nombre` as categoria, \n" + 
			"t.`IdPregunta` as id_pregunta,\n" + 
			"t.`Estado` as estado,\n" + 
			"t.`IdCategoria` as id_categoria,\n" + 
			"t.`LastUser` as last_user,\n" + 
			"t.`Nombre` as nombre_pregunta,\n" + 
			"t.`Orden` as order_pregunta,\n"
			+" dp2.IdRespuesta as id_respuesta_selected " + 
			" from `pregunta` t\n" + 
			"inner join `categoria_pregunta` tt on(tt.`IdCategoria`=t.`IdCategoria`)\n"
			+ "inner join detalle_pedido dp2 on(dp2.IdPregunta=t.IdPregunta)\n"
			+ "where  dp2.IdPedido=?1 and  t.Estado=1\n"
			+ " order by tt.`Orden`, t.`Orden` ASC", nativeQuery = true)
	List<NameOnly> findByNativeQuery(Integer idPedido);
	
	@Query(value="select \n"
			+ "dp2.IdPreguntaRespuesta as id, r2.IdRespuesta as id_respuesta,\n"
			+ "r2.Nombre as respuesta,\n"
			+ "r2.Estado as estado_respuesta,\n"
			+ "r2.TipoRespuesta as tipo_respuesta,\n"
			+ "r2.`Orden` as order_respuesta,\n"
			+ "t.`IdPregunta` as id_pregunta,\n"
			+ "t.`IdCategoria` as id_categoria,\n"
			+ "t.`Nombre` as nombre_pregunta\n"
			+ "from `pregunta` t\n"
			+ "inner join respuesta r2 on(r2.IdPregunta=t.IdPregunta)\n"
			+ "inner join `categoria_pregunta` tt on(tt.`IdCategoria`=t.`IdCategoria`)\n"
			+ "inner join detalle_pedido dp2 on(dp2.IdPregunta=t.IdPregunta)\n"
			+ "where dp2.IdPedido=?1 and t.Estado=1"
			+ " order by r2.`Orden`, t.`Orden` ASC", nativeQuery = true)
	List<NameOnlyAnswers> findAnswuerByNativeQuery(Integer idPedido);
	
	public static interface NameOnlyAnswers {
		 @JsonProperty("id")
		 Integer getid();
		 @JsonProperty("id_respuesta")
		 Integer getid_respuesta();		 
		 @JsonProperty("respuesta")
		 String getRespuesta();		 
		 @JsonProperty("estado_respuesta")
		 Integer getestado_respuesta();
		 @JsonProperty("tipo_respuesta")
		 Integer gettipo_respuesta();
		 @JsonProperty("order_respuesta")
		 Integer getorder_respuesta();
		 @JsonProperty("id_pregunta")
		 Integer getid_pregunta();
		 @JsonProperty("nombre_pregunta")
		 String getnombre_pregunta();
		 @JsonProperty("order_pregunta")
		 Integer getorder_pregunta(); 	 
		 
	  }
	
	public static interface NameOnly {

		 @JsonProperty("categoria")
		 String getCategoria();		 
		 @JsonProperty("id_pregunta")
		 Integer getid_pregunta();		 
		 @JsonProperty("estado")
		 Integer getEstado();
		 @JsonProperty("last_user")
		 String getlast_user();
		 @JsonProperty("nombre_pregunta")
		 String getnombre_pregunta();
		 @JsonProperty("order_pregunta")
		 Integer getorder_pregunta(); 
		 @JsonProperty("id_respuesta_selected")
		 Integer getid_respuesta_selected();
		 
	  }

}
