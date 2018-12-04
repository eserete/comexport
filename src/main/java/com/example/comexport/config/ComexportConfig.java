package com.example.comexport.config;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ComexportConfig {

    @Bean
    public ObjectIdGenerators.UUIDGenerator uuidGenerator() {
        return new ObjectIdGenerators.UUIDGenerator();
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        return mapper;
    }

}
