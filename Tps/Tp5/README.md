# TP 5.1 : Spring Cloud Zuul - API Gateway

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


# TP 5.2 : Spring Cloud Gateway - API Gateway Moderne

## Objectifs
Mettre en place une API Gateway moderne avec Spring Cloud Gateway en utilisant une approche réactive et programmatique.

## Architecture
- **Spring Cloud Gateway** : Port 9999
- **Eureka Server** : Port 9102
- **Config Server** : Port 9101

## Configuration Principale

### 1. Fichier Properties
```properties
server.port=9999
eureka.client.serviceUrl.defaultZone=http://localhost:9102/eureka/
