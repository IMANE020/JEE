package com.example.microservice_commandes.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservice Commandes API",
                version = "1.0",
                description = "API pour la gestion des commandes"
        )
)
public class OpenApiConfig {
}