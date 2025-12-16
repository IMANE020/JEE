-- Création de la table si elle n'existe pas (JPA le fait automatiquement)
CREATE TABLE IF NOT EXISTS product (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       titre VARCHAR(255) NOT NULL,
    description TEXT,
    image VARCHAR(500),
    prix DECIMAL(10, 2) NOT NULL
    );