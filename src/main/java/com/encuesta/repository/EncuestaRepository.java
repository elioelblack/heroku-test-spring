package com.encuesta.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.encuesta.entity.Encuesta;
import com.fasterxml.jackson.annotation.JsonProperty;


public interface EncuestaRepository extends JpaRepository<Encuesta, Integer>{
	
	@Query(value = "SELECT "
			+ "  (SELECT COUNT(e.`IdPedido`) "
			+ "   FROM `pedido_encuesta` e "
			+ "   WHERE `e`.`FechaPedido` >= DATE_SUB(NOW(),INTERVAL 30 DAY)) AS total_encuestas_last_month, "
			+ "  (SELECT COUNT(*) "
			+ "   FROM `usuario`) AS total_usuarios, "
			+ "  (SELECT COUNT(*) "
			+ "   FROM `usuario` u "
			+ "   WHERE u.`FechaRegistro` >= DATE_SUB(NOW(),INTERVAL 30 DAY)) AS total_usuarios_last_month, "
			+ "  (SELECT SUM(CASE "
			+ "  WHEN dp.`IdRespuesta` IS NOT NULL THEN 1 "
			+ "     ELSE 0 "
			+ "   END) "
			+ "   FROM `detalle_pedido` dp) AS preguntas_respondidas", nativeQuery = true)
	NameOnly loadInfoDashboard();
	
	public static interface NameOnly {

		 @JsonProperty("total_encuestas_last_month")
		 Integer gettotal_encuestas_last_month();		 
		 @JsonProperty("total_usuarios")
		 Integer gettotal_usuarios();
		 @JsonProperty("total_usuarios_last_month")
		 Integer gettotal_usuarios_last_month();		 
		 @JsonProperty("preguntas_respondidas")
		 Integer getpreguntas_respondidas();
		 
	  }
	
	@Query(value = "SELECT MONTHNAME(p.`FechaPedido`) AS mes, "
			+ "       count(p.`IdPedido`) AS total, "
			+ "       MONTHNAME(p2.`FechaPedido`) AS mes2, "
			+ "       count(p2.`IdPedido`) AS total2 "
			+ "FROM `pedido_encuesta` p "
			+ "LEFT JOIN `pedido_encuesta` p2 ON(MonthName(p2.`FechaPedido`) = MonthName(p.`FechaPedido`) "
			+ "AND year(p2.`FechaPedido`) = year(makedate(year(curdate()) - 1, 1))) "
			+ "WHERE year(p.`FechaPedido`) = year(curdate()) "
			+ "GROUP BY MonthName(p.`FechaPedido`), "
			+ "         MonthName(p2.`FechaPedido`)"
			+ " order by p.`FechaPedido` ", nativeQuery = true)
	List<NameOnly2> loadInfoDashboard2();
	
	public static interface NameOnly2 {

		 @JsonProperty("mes")
		 String getmes();
		 @JsonProperty("total")
		 Integer gettotal();
		 @JsonProperty("total2")
		 Integer gettotal2();		 
		 
	  }

}
