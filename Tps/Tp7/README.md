# TP 7 – Communication entre Microservices avec Spring Cloud OpenFeign

## 📌 Description
Ce TP a pour objectif de mettre en place une application distribuée basée sur une architecture **microservices**, et d’assurer la communication entre ces microservices à l’aide de **Spring Cloud OpenFeign**.

L’application réalisée, nommée **MCommerce**, simule un système de commerce en ligne permettant :
- la consultation de produits,
- la passation de commandes,
- le paiement des commandes.

Le microservice **ClientUI** joue le rôle de point d’entrée et orchestre les appels aux autres microservices.

---

## 🎯 Objectifs du TP
- Comprendre le principe de communication entre microservices
- Mettre en place une application distribuée composée de plusieurs microservices
- Utiliser **Spring Cloud OpenFeign**
- Utiliser les annotations :
  - `@EnableFeignClients`
  - `@FeignClient`
- Orchestrer les appels entre microservices

---

## 🧱 Architecture de l’application

L’application **MCommerce** est composée de **4 microservices** :

- **ClientUI** : point d’entrée de l’application (orchestration)
- **Microservice-Produits** : gestion des produits
- **Microservice-Commandes** : gestion des commandes
- **Microservice-Paiement** : gestion des paiements

Le microservice ClientUI communique avec les autres microservices via **OpenFeign**.

---

## 🛠️ Prérequis
- Java JDK 17
- Maven
- Connexion Internet
- IDE (IntelliJ IDEA / Eclipse / VS Code)
- Navigateur Web

---

## ⚙️ Technologies utilisées
- Spring Boot
- Spring Cloud OpenFeign
- Spring MVC
- Thymeleaf
- Maven
- H2 Database
- Bootstrap

---

## 🚀 Mise en œuvre

### 1️⃣ Activation de Feign
Le microservice ClientUI est annoté avec :
```java
@EnableFeignClients("com.clientui")

