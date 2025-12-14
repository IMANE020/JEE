# TP 4 : Spring Cloud Eureka - Service Registry & Discovery

## Objectifs
Mettre en place un serveur Eureka pour la découverte automatique des microservices et permettre l'enregistrement de plusieurs instances d'un même service.

## Architecture
- **Eureka Server** : Port 9102 - Registre central des services
- **Config Server** : Port 9101 - Configuration centralisée (TP précédent)
- **Microservice Produits** : Instances multiples sur ports 9001 et 9011
- **GitHub** : Dépôt de configuration centralisé

## Points Clés

### 1. Eureka Server
- **Annotation** : `@EnableEurekaServer` pour activer le serveur
- **Configuration** : Utilisation de `eureka-server.properties` dans GitHub
- **Port** : 9102 (convention 91xx pour les services d'infrastructure)
- **Mode standalone** : `eureka.client.registerWithEureka=false` (ne s'enregistre pas lui-même)

### 2. Configuration Eureka Server
```properties
# Fichier eureka-server.properties dans GitHub
server.port=9102
spring.application.name=eureka-server
eureka.client.registerWithEureka=false
eureka.client.fetchRegistry=false
spring.cloud.config.import-check.enabled=false
eureka.server.maxThreadsForPeerReplication=0
