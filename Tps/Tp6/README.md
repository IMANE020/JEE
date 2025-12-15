# TP 6 : Spring Cloud Hystrix - Résilience des Microservices

## Objectifs
Implémenter des mécanismes de résilience avec Hystrix pour gérer les pannes et les timeouts dans les microservices.

## Architecture
- **Application** : `Api_5bHystrix` sur le port 9000
- **Base de données** : H2 en mémoire
- **Monitoring** : Dashboard Hystrix et flux Actuator
- **Circuit Breaker** : Timeout configuré à 1 seconde

## Points Clés

### 1. Configuration Hystrix
```properties
# Fichier application.properties
spring.application.name=Api_5bHystrix
server.port=9000
management.endpoints.web.exposure.include=hystrix.stream
