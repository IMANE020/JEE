CREATE TABLE IF NOT EXISTS product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titre VARCHAR(255),
    description VARCHAR(255),
    image VARCHAR(255),
    price DOUBLE
);