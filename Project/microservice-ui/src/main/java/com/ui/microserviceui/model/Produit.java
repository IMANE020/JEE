package com.ui.microserviceui.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Produit {
    private Integer id;
    private String titre;
    private String description;
    private String image;
    private Double price;
}