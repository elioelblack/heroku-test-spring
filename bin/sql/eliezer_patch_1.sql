CREATE DEFINER = 'root'@'localhost' TRIGGER `pedido_encuesta_AU` AFTER INSERT ON `pedido_encuesta`
  FOR EACH ROW
BEGIN
    DECLARE done INT DEFAULT FALSE;	  
	  DECLARE id_pregunta, id_pedido INT;
	  DECLARE cur1 CURSOR FOR 
	  select p.idPregunta from pregunta p 
		inner join categoria_pregunta cp on(cp.IdCategoria=p.IdCategoria)
		where cp.id_encuesta  = NEW.IdEncuesta and p.Estado = 1;
	  
	  DECLARE CONTINUE HANDLER FOR NOT FOUND SET done = TRUE;
	
	  OPEN cur1;
	  read_loop: LOOP
	    FETCH cur1 INTO id_pregunta;
	    
	    IF done THEN
	      LEAVE read_loop;
	    END IF;
	    INSERT INTO encuesta.detalle_pedido
		(IdPregunta, IdRespuesta, FechaResultado, LastUser, IdPedido, NumericResult, TextResult)
		VALUES(id_pregunta, NULL, current_timestamp(), 'Triger', NEW.IdPedido, NULL, NULL);

	  END LOOP;
	
	  CLOSE cur1;
END;
