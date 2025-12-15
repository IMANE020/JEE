# TP 5 : Spring Cloud Zuul - API Gateway

## Objectifs
Mettre en place une API Gateway avec Zuul pour centraliser l'accès aux microservices, gérer le load balancing et implémenter des filtres de requêtes.

## Architecture
- **Zuul Gateway** : Port 9004 - Point d'entrée unique pour tous les microservices
- **Eureka Server** : Port 9102 - Service Registry (TP précédent)
- **Config Server** : Port 9101 - Configuration centralisée
- **Microservice Produits** : 2 instances sur ports 9001 et 9011

## Points Clés

### 1. Configuration Zuul
```properties
# Fichier zuul-server.properties dans GitHub
server.port=9004
eureka.client.serviceUrl.defaultZone=http://localhost:9102/eureka/
