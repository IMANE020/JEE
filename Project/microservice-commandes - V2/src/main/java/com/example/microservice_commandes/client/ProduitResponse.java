package com.example.microservice_commandes.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProduitResponse {
    private int id;
    private String titre;
    private String description;
    private String image;
    private Double prix;
}
