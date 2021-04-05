package com.encuesta.json;

import java.io.IOException;

import com.encuesta.entity.Respuesta;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class RespuestaJsonDeserializer extends JsonDeserializer<Respuesta>{
	@Override
	public Respuesta deserialize(JsonParser jp, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		if(node.asText()==null || node.asText().equalsIgnoreCase("")) {
			return null;
		}
		return new Respuesta(Integer.valueOf((node.asText())));
	}
}
