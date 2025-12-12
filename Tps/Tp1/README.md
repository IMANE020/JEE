# TP 1 : Spring Boot + REST + JAR

Ce TP consiste à développer une application Java (JAR) avec Spring Boot et REST, incluant un exemple CRUD complet.

## 📋 Objectifs
- Créer un projet Maven avec Spring Initializr
- Développer un contrôleur REST avec Spring Boot
- Implémenter un CRUD complet pour une entité `Product`
- Packager l’application en JAR exécutable
- Tester les services REST avec Postman
- Gérer les configurations via `application.properties`
- Produire des réponses en JSON et XML
- Utiliser les profils Spring (`spring.profiles.active`)

## 🛠️ Prérequis
- Eclipse JEE-2020 (ou IDE équivalent)
- JDK 1.8 ou 17
- Maven
- Postman (pour tester les requêtes HTTP)

## 🚀 Étapes principales
1. Création du projet avec [Spring Initializr](https://start.spring.io/)
2. Développement de la classe principale avec `@SpringBootApplication`
3. Création du contrôleur `HelloController`
4. Build et exécution de l’application
5. Développement du modèle `Product` et du contrôleur `ProductController`
6. Test des endpoints CRUD avec Postman
7. Ajout du support XML avec Jackson
8. Gestion des profils de configuration (`dev`, `prod`, `integration`)
9. Utilisation de fichiers de configuration externes

## 📁 Structure du projet



## 🧪 Tests
- GET : `http://localhost:8080/products`
- POST : Envoyer un JSON vers `http://localhost:8080/products`
- PUT : Mettre à jour un produit existant
- DELETE : Supprimer un produit par ID

## 📦 Build et exécution
```bash
mvn clean install
java -jar target/rest-0.0.1-SNAPSHOT.jar
