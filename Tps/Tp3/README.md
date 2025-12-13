# TP 3 - Spring Cloud Config & Actuator

## Partie 3.1 : Spring Cloud Config - Externalisation et Centralisation de la Configuration

### Objectifs
Mettre en place un serveur de configuration centralisé pour externaliser les configurations des microservices depuis un dépôt Git.

### Architecture
- **Config Server** : Port 9101, récupère les configurations depuis GitHub
- **Microservice Produits** : Port 9001, consomme sa configuration depuis le Config Server
- **Dépôt Git** : `https://github.com/IMANE020/mcommerce-config-repo.git`

### Points Clés
1. **Serveur de Configuration** (`@EnableConfigServer`) : Centralise toutes les configurations dans un dépôt Git
2. **Bootstrap Properties** : Les microservices utilisent `bootstrap.properties` pour se connecter au Config Server
3. **Nommage** : Le fichier `microservice-produits.properties` dans le dépôt correspond au `spring.application.name`
4. **Configuration Dynamique** : Modification de `mes-configs.limitDeProduits=3` dans le dépôt Git
5. **Communication** : Le microservice produits récupère sa configuration à son démarrage depuis `http://localhost:9101`

### Résultat
Le microservice produits affiche seulement 3 produits au lieu des 8 présents en base de données, démontrant l'application de la configuration externalisée.

---

## Partie 3.2 : Spring Cloud Actuator - Monitoring des Microservices

### Objectifs
Développer un microservice "monitorable" avec Spring Actuator et permettre le rechargement dynamique de la configuration.

### Fonctionnalités
1. **Actuator Endpoints** : Exposition des endpoints de monitoring via `management.endpoints.web.exposure.include=*`
2. **Refresh Scope** : Rechargement à chaud avec `@RefreshScope` et endpoint `/actuator/refresh`
3. **Health Check** : Personnalisation de l'état de santé avec `HealthIndicator`
4. **Configuration Dynamique** : Modification de `mes-configs.limitDeProduits` de 4 à 5 sans redémarrer

### Points Techniques
- **Endpoint Refresh** : Méthode POST sur `http://localhost:9001/actuator/refresh` pour appliquer les nouvelles configurations
- **Health Indicator** : Vérification de la présence de produits pour déterminer l'état UP/DOWN
- **Exposition** : ~24 endpoints Actuator disponibles pour le monitoring

### Résultat
Le microservice peut être reconfiguré dynamiquement (de 4 à 5 produits affichés) via une simple requête POST, sans interruption de service.

### Avantages
- Centralisation de la configuration pour tous les microservices
- Rechargement dynamique sans downtime
- Monitoring complet via Actuator
- Synchronisation automatique avec Git
- Isolation des configurations par environnement
