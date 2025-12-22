package com.example.microservice_commandes.configuration;

import com.example.microservice_commandes.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Autowired
    private CommandeRepository commandeRepository;

    @Override
    public Health health() {
        try {
            long count = commandeRepository.count();
            if (count > 0) {
                return Health.up()
                        .withDetail("message", "Service en bonne santé - " + count + " commande(s) en base")
                        .withDetail("nombreCommandes", count)
                        .build();
            } else {
                return Health.down()
                        .withDetail("message", "Service DOWN - Aucune commande en base de données")
                        .withDetail("nombreCommandes", 0)
                        .build();
            }
        } catch (Exception e) {
            return Health.down()
                    .withDetail("message", "Erreur d'accès à la base de données: " + e.getMessage())
                    .build();
        }
    }
}


