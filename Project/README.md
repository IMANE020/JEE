# 📦 Microservices – Études de cas 1 & 2

## 👥 Équipe de développement
**Imane TAYB** et **ASSIA EL_ATTARY**

## 📖 Description du projet
Ce dépôt contient les codes sources Maven de deux études de cas réalisées dans le cadre de la mise en place d'une architecture microservices avec **Spring Boot** et **Spring Cloud**.

### 🔹 Étude de cas 1
Mise en place d'un microservice commandes avec :
- Configuration centralisée via **Spring Cloud Config Server**
- Supervision avec **Spring Boot Actuator**
- Validation des endpoints CRUD via **Postman**

### 🔹 Étude de cas 2
Architecture distribuée complète avec :
- Deux microservices : **commandes** et **produits**
- **Eureka** pour la découverte des services
- **API Gateway** pour le routage
- **Feign Client** pour la communication inter-services
- **Hystrix** pour la résilience
- **Actuator** pour la supervision
- **Swagger / OpenAPI** pour la documentation
- Une **interface utilisateur (UI)** pour la gestion des produits et des commandes

## ⚙️ Prérequis
Avant d'exécuter les projets, assurez-vous d'avoir installé :
- Java 17+
- Maven 3.8+
- Git
- Un IDE compatible (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Démarrage des projets

### 1️⃣ Cloner le dépôt
```bash
git clone https://github.com/<votre-utilisateur>/<nom-du-repo>.git
```

### 2️⃣ Accéder au projet
```bash
cd microservices-etudes
```

### 3️⃣ Lancer les microservices
```bash
mvn spring-boot:run
```

## 🔗 Endpoints principaux

### 📌 Étude de cas 1 – Microservice Commandes
- `GET /commandes` → Récupérer toutes les commandes
- `GET /commandes/recentes` → Récupérer les commandes des X derniers jours (configurable)
- `GET /commandes/{id}` → Récupérer une commande par ID
- `POST /commandes` → Créer une commande
- `PUT /commandes/{id}` → Mettre à jour une commande
- `DELETE /commandes/{id}` → Supprimer une commande
- `GET /actuator/health` → Vérifier l'état du microservice

### 📌 Étude de cas 2 – Architecture distribuée
- `GET /produits` → Récupérer tous les produits
- `POST /produits` → Créer un produit
- `PUT /produits/{id}` → Mettre à jour un produit
- `DELETE /produits/{id}` → Supprimer un produit
- `GET /commandes/recentes` → Récupérer les commandes des X derniers jours
- `GET /api-gateway/commandes/**` → Routage via API Gateway
- `GET /api-gateway/produits/**` → Routage via API Gateway

## 🖥️ Interface Utilisateur (UI)
Une interface web est incluse pour gérer les produits et les commandes :

### Dashboard
Vue d'ensemble avec indicateurs clés (produits totaux, commandes actives, chiffre d'affaires, services actifs)

### Gestion des Produits
CRUD complet, affichage du prix moyen et des produits premium

### Gestion des Commandes
CRUD complet, statistiques globales (nombre de commandes, montant total, quantité totale)

### Fiche Produit
Détails d'un produit avec actions possibles (modifier, supprimer, créer une commande)

### Formulaires
Interfaces pour créer ou modifier un produit ou une commande

## 🧪 Tests Postman

### 🔹 Création d'une commande avec validation produit
```http
POST http://localhost:8083/api/commandes
Content-Type: application/json

{
  "description": "Commande Aspirateur robot",
  "quantite": 1,
  "date": "2025-12-22",
  "montant": 299.99,
  "idProduit": 1
}
```

**Résultat attendu :**
- HTTP 201 Created si le produit existe
- Réponse JSON avec l'ID généré automatiquement
- HTTP 503 Service Unavailable si le microservice-produits est indisponible (fallback Hystrix)

### 🔹 Création simple sans validation produit
```http
POST http://localhost:8083/api/commandes/simple
Content-Type: application/json

{
  "description": "Commande test sans validation",
  "quantite": 2,
  "date": "2025-12-20",
  "montant": 150.00,
  "idProduit": 2
}
```

**Résultat attendu :**
- HTTP 201 Created
- Commande enregistrée sans vérification du produit

## ✅ Conclusion
Cette étude de cas met en œuvre une architecture microservices complète et résiliente basée sur Spring Cloud. Elle intègre la découverte des services avec Eureka, un point d'entrée unique via API Gateway, la communication inter-services avec Feign Client, et la tolérance aux pannes grâce à Hystrix.

Les microservices commandes et produits, chacun relié à sa propre base H2, sont configurés via un serveur de configuration centralisé et supervisés à l'aide de Spring Boot Actuator et Swagger/OpenAPI. Les tests Postman ainsi que l'interface utilisateur confirment la cohérence, la robustesse et le bon fonctionnement global de l'architecture.
```
