package com.ui.microserviceui.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commande {
    private Long id;
    private String description;
    private Integer quantite;
    private LocalDate date;
    private Double montant;
    private Long idProduit;

    private String produitNom;
    private Double produitPrix;
}