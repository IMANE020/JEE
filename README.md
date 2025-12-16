# 🧠 Microservices avec Spring Cloud – Série de TPs

Ce dépôt regroupe une série complète de TPs réalisés dans le cadre d’un apprentissage approfondi des microservices avec Spring Boot et Spring Cloud. Chaque TP explore une composante essentielle d’une architecture distribuée moderne.

---

## 🚀 Technologies utilisées

- **Java 8 / 17**
- **Spring Boot**
- **Spring Cloud Config**
- **Spring Cloud Eureka**
- **Spring Cloud Zuul / Gateway**
- **Spring Cloud OpenFeign**
- **Spring Cloud Hystrix**
- **Spring Data JPA**
- **H2 Database**
- **Postman** (tests API)
- **GitHub** (centralisation des fichiers `.properties`)
- **Hystrix Dashboard**

---

## 📚 Liste des TPs

### ✅ TP1 : Microservice Produits
- Création d’un microservice RESTful avec Spring Boot.
- Base H2 + Spring Data JPA.
- Tests CRUD via Postman.

### ✅ TP2 : Communication entre microservices
- Appels REST entre services (sans OpenFeign).
- Simulation d’un front-end WebApp.

### ✅ TP3 : Spring Cloud Config Server
- Centralisation des configurations via GitHub.
- Utilisation de `bootstrap.properties` et `application.properties`.
- Test du rechargement dynamique avec `/actuator/refresh`.

### ✅ TP4 : Spring Cloud Eureka Server
- Mise en place d’un registre de services.
- Enregistrement automatique des microservices.
- Test du Service Discovery via la console Eureka.

### ✅ TP5.1 : Spring Cloud Zuul API Gateway
- Mise en place d’un point d’entrée unique.
- Routage dynamique via Eureka.
- Test du load balancing et des filtres personnalisés.

### ✅ TP5.2 : Spring Cloud Gateway (version moderne)
- Utilisation de Spring Cloud Gateway (remplaçant Zuul).
- Configuration automatique des routes via DiscoveryClient.
- Test du routage via `http://localhost:9999/SERVICE-NAME/...`.

### ✅ TP6 : Résilience avec Spring Cloud Hystrix
- Implémentation du Circuit Breaker.
- Détection de timeout et fallback automatique.
- Visualisation en temps réel via Hystrix Dashboard.

### ✅ TP7 : Communication simplifiée avec OpenFeign
- Appels REST déclaratifs entre microservices.
- Enrichissement des réponses (ex : commande → produit → paiement).
- Architecture locale sans Eureka ni Config Server.

---

## 🧪 Tests réalisés

- ✅ **Postman** : vérification des endpoints CRUD, appels inter-services, tests de fallback.
- ✅ **Console H2** : inspection des tables `PRODUCT`, `COMMANDE`, `PAIEMENT`.
- ✅ **Hystrix Dashboard** : suivi des circuits, latence, erreurs.
- ✅ **Interface Mcommerce** : simulation d’achat, paiement, confirmation.
- ✅ **Diagramme de séquence** : visualisation du flux entre Client → Produits → Commandes → Paiement.

---

## 📌 Objectifs pédagogiques

- Comprendre les principes de **modularité**, **scalabilité** et **résilience** dans les architectures microservices.
- Maîtriser les outils Spring Cloud pour la **configuration**, le **routage**, la **découverte de services** et la **tolérance aux pannes**.
- Mettre en œuvre une communication **déclarative et robuste** entre services.
- Tester et documenter chaque étape pour garantir la reproductibilité.

---

## 👩‍💻 Auteur

**TAYB Imane**  


