
---

# `README.md` pour TP 2 - Spring Boot : Communication WebApp + Microservice REST API

```markdown
# TP 2 : Spring Boot – Communication entre une WebApp et un Microservice REST API

Ce TP a pour objectif de développer une application Web (WebApp) qui communique avec un microservice REST API, en utilisant Spring Boot, Thymeleaf et H2 Database.

## 📋 Objectifs
- Développer un microservice REST API avec Spring Boot et H2
- Créer une WebApp avec Spring Boot et Thymeleaf
- Communiquer entre la WebApp et le microservice via `RestTemplate`
- Implémenter un CRUD complet pour l’entité `Employee`
- Utiliser Spring Data JPA pour la persistance
- Appliquer une architecture MVC dans la WebApp
- Tester les deux applications indépendamment et en interaction

## 🛠️ Prérequis
- Eclipse Mars+ avec Maven 3.x
- JDK 1.8
- Postman
- Connexion Internet pour télécharger les dépendances

## 🏗️ Architecture

WebApp (port 9001) ↔ REST API (port 9000) ↔ Base H2 (in-memory)


## 🚀 Étapes principales
### Partie 1 : Microservice REST API (`api_sb`)
1. Création du projet avec Spring Initializr (Spring Web, Lombok, H2, Spring Data JPA)
2. Configuration de H2 dans `application.properties`
3. Développement de l’entité `Employee`
4. Création du repository avec `CrudRepository`
5. Implémentation du service métier `EmployeeService`
6. Développement du contrôleur REST `EmployeeController`
7. Test des endpoints avec Postman

### Partie 2 : WebApp (`webapp`)
1. Création du projet avec Spring Initializr (Spring Web, Thymeleaf)
2. Configuration personnalisée avec `CustomProperties`
3. Développement du proxy `EmployeeProxy` avec `RestTemplate`
4. Création du contrôleur MVC `EmployeeController`
5. Développement des vues Thymeleaf (`home.html`, `formUpdateEmployee.html`)
6. Intégration des actions CRUD via formulaires et liens

## 📁 Structure du microservice


## 🧪 Tests
### Microservice (API)
- GET : `http://localhost:9000/employees`
- Console H2 : `http://localhost:9000/h2-console`

### WebApp
- Accueil : `http://localhost:9001/`
- Formulaire de création/modification
- Suppression via lien

## 🔗 Communication WebApp ↔ API
La WebApp utilise `RestTemplate` pour appeler les endpoints du microservice. L’URL de l’API est configurée dans `application.properties`.

## 📦 Build et exécution
```bash
# Pour le microservice
cd api_sb
mvn spring-boot:run

# Pour la WebApp
cd webapp
mvn spring-boot:run
