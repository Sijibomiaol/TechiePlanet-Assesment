package com.techieplanet.assessment.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Student Score Management API")
                        .version("1.0")
                        .description("API for managing student scores in 5 subjects with statistical analysis")
                        .contact(new Contact()
                                .name("TechiePlanet")
                                .email("info@techieplanet.com")));
    }
}

