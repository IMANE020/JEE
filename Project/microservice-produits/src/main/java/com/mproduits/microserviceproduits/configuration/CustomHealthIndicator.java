package com.mproduits.microserviceproduits.configuration;

import com.mproduits.microserviceproduits.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomHealthIndicator implements HealthIndicator {

    private final ProductService productService;

    @Override
    public Health health() {
        long count = productService.countProducts();
        String status = count > 0 ? "UP" : "DOWN";

        return Health.status(status)
                .withDetail("produitsCount", count)
                .withDetail("message", count > 0 ?
                        "Service produits opérationnel" :
                        "Aucun produit disponible")
                .build();
    }
}