package com.mproduits.microserviceproduits.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Microservice Produits API",
                version = "1.0",
                description = "API pour la gestion des produits"
        )
)
public class OpenApiConfig {
}