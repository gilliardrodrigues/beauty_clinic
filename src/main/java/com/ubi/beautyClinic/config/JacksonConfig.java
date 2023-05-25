package com.ubi.beautyClinic.config;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ubi.beautyClinic.application.core.domain.Gender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.io.IOException;

@Configuration
public class JacksonConfig {

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        SimpleModule module = new SimpleModule();
        module.addDeserializer(Gender.class, new GenderDeserializer());
        objectMapper.registerModule(module);

        return objectMapper;
    }

    private static class GenderDeserializer extends JsonDeserializer<Gender> {
        @Override
        public Gender deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
            String value = jsonParser.getValueAsString();
            if (value == null || value.isEmpty()) {
                return null;
            }
            return Gender.valueOf(value);
        }
    }
}

