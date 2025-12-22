package com.mproduits.microserviceproduits.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PRODUCT")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String titre;
    private String description;
    private String image;
    private Double price;

    // Constructeur corrigé
    public Product(String titre, String description, String image, Double price) {
        this.titre = titre;
        this.description = description;
        this.image = image;
        this.price = price;
    }
}