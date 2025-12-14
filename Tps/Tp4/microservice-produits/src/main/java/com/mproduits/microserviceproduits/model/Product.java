package com.mproduits.microserviceproduits.model;

import jakarta.persistence.*;

@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titre;
    private String description;
    private String image;
    private Double price;

    public Product() {}

    public Product(String name, String description, String image, Double price) {
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    @Override
    public String toString() {
        return "Product{" + "id=" + id + ", titre='" + titre + '\'' + ", description='" + description + '\'' + ", image='" + image + '\'' + ", prix=" + price + '}';
    }
}
