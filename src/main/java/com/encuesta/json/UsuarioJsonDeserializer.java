package com.encuesta.json;

import java.io.IOException;

import com.encuesta.entity.Usuario;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

public class UsuarioJsonDeserializer extends JsonDeserializer<Usuario>{
	@Override
	public Usuario deserialize(JsonParser jp, DeserializationContext arg1)
			throws IOException, JsonProcessingException {
		JsonNode node = jp.getCodec().readTree(jp);
		if(node.asText()==null || node.asText().equalsIgnoreCase("")) {
			return null;
		}
		return new Usuario(Integer.valueOf((node.asText())));
	}
}
