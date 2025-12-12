# TP 2 : Spring Boot – Communication WebApp + Microservice REST API

Ce TP consiste à développer une application Web (WebApp) qui communique avec un microservice REST API, en utilisant Spring Boot, Thymeleaf et H2 Database.

## Objectifs
- Développer un microservice REST API avec Spring Boot et H2 Database
- Développer une WebApp de gestion des employés avec Spring Boot et Thymeleaf
- Communiquer entre la WebApp et le microservice via `RestTemplate`
- Implémenter un CRUD complet pour l'entité `Employee`
- Utiliser Spring Data JPA pour la persistance des données
- Appliquer une architecture MVC dans la WebApp
- Utiliser des fichiers de configuration personnalisés

## Prérequis
- Eclipse Mars (ou IDE équivalent)
- JDK 1.8
- Maven
- Postman (pour tester les méthodes POST, PUT et DELETE)
- Connexion Internet pour télécharger les dépendances Maven

## Étapes principales
1. **Partie 1 - Microservice REST API** :
   - Création du projet avec Spring Initializr (Spring Web, Lombok, H2 Database, Spring Data JPA)
   - Configuration de la base de données H2
   - Développement de l'entité `Employee`
   - Création du repository avec `CrudRepository`
   - Implémentation du service métier `EmployeeService`
   - Développement du contrôleur REST `EmployeeController`

2. **Partie 2 - WebApp** :
   - Création du projet avec Spring Initializr (Spring Web, Thymeleaf)
   - Configuration personnalisée avec `CustomProperties`
   - Développement du proxy `EmployeeProxy` avec `RestTemplate`
   - Création du contrôleur MVC `EmployeeController`
   - Développement des vues Thymeleaf (`home.html`, `formUpdateEmployee.html`)

3. **Communication entre les composants** :
   - Utilisation de `RestTemplate` pour les appels HTTP
   - Application de règles métier dans la WebApp
   - Intégration des formulaires HTML avec le backend

## Structure des projets

### Microservice (api_sb)
### WebApp (webapp)

<img width="150" height="1500" alt="image" src="https://github.com/user-attachments/assets/2e318b1a-bbc1-42ad-b1dc-55434363946e" />


## Tests
### Microservice (port 9000) :
- GET : `http://localhost:9000/employees` (liste tous les employés)
- Console H2 : `http://localhost:9000/h2-console`

### WebApp (port 9001) :
- Accueil : `http://localhost:9001/`
- Formulaire de création/modification d'employés
- Suppression via liens

## Configuration

### Microservice (`application.properties`) :
```properties
spring.application.name=api_sb
server.port=9000
spring.h2.console.enabled=true

