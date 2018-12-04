package com.example.comexport.config;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        return new ObjectMapper();
    }

}
