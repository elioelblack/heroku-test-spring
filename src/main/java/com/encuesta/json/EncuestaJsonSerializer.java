package com.encuesta.json;

import java.io.IOException;

import com.encuesta.entity.Encuesta;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class EncuestaJsonSerializer extends JsonSerializer<Encuesta>{
	@Override
	public void serialize(Encuesta model, JsonGenerator jsonGenerator, SerializerProvider serializers)
			throws IOException {
		jsonGenerator.writeObject(model.getId());
	}
}
