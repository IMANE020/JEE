# TP 5 – Spring Cloud API Gateway  
## Zuul API Gateway & Spring Cloud Gateway

Ce repository regroupe les travaux pratiques **TP 5.1** et **TP 5.2** portant sur la mise en œuvre d’un **API Gateway** dans une architecture microservices basée sur **Spring Cloud**.

L’API Gateway joue un rôle central en servant de point d’entrée unique pour les clients et en assurant le routage, la découverte des services et le load balancing.

---

## Objectifs

- Mettre en place un **Eureka Server**
- Enregistrer les microservices et la Gateway dans Eureka
- Implémenter la **Service Discovery**
- Mettre en œuvre le **Load Balancing**
- Tester les appels directs et via la Gateway
- Comprendre la différence entre **Zuul API Gateway** et **Spring Cloud Gateway**

---

## Prérequis

- Java 8 (Zuul) / Java 17 (Spring Cloud Gateway)
- Maven
- Spring Boot
- Spring Cloud Config Server (optionnel)
- Eureka Server
- Postman (ou équivalent)

---

## TP 5.1 – Spring Cloud Zuul API Gateway

Zuul est une solution de Gateway fournie par Netflix et intégrée à Spring Cloud (ancienne génération).

### Fonctionnalités principales

- Routage dynamique des requêtes
- Découverte des services via Eureka
- Load balancing automatique (Round Robin)
- Gestion des filtres :
  - **Pre Filter** : interception des requêtes
  - **Post Filter** : modification des réponses

### Test des services

- Accès direct au microservice Produits :
http://localhost:9001/Produits

- Accès via Zuul Gateway :
http://localhost:9004/microservice-produits/Produits

À chaque requête, une instance différente du microservice est appelée grâce au load balancing.

---

## TP 5.2 – Spring Cloud Gateway

Spring Cloud Gateway est la solution moderne et recommandée par Spring pour remplacer Zuul.

### Fonctionnalités principales

- Gateway réactive basée sur **Spring WebFlux**
- Découverte automatique des routes via Eureka
- Load balancing intégré
- Routage dynamique sans configuration manuelle des routes

### Test des services

- Accès au microservice via la Gateway :
http://localhost:9999/MICROSERVICE-PRODUITS/Produits

La Gateway interroge Eureka pour localiser automatiquement l’instance du microservice.

---

## Comparaison Zuul vs Spring Cloud Gateway

| Critère | Zuul | Spring Cloud Gateway |
|-------|------|----------------------|
| Modèle | Bloquant | Réactif |
| Performance | Moyenne | Élevée |
| Maintenance | Obsolète | Recommandé |
| WebFlux | Non | Oui |
| Versions Spring | Anciennes | Récentes |

---

## Ordre de démarrage recommandé

1. Spring Cloud Config Server (port 9101)
2. Eureka Server (port 9102)
3. API Gateway (Zuul ou Spring Cloud Gateway)
4. Microservices (plusieurs instances)

---

## Conclusion

Ces travaux pratiques montrent l’importance de l’API Gateway dans une architecture microservices.  
**Zuul** permet de comprendre les concepts de base, tandis que **Spring Cloud Gateway** représente la solution moderne, performante et évolutive recommandée par Spring.

---

## Références

- TP 5.1 – Spring Cloud Zuul API Gateway  
- TP 5.2 – Spring Cloud Gateway
