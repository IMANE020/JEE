package com.example.microservice_commandes.client;

import org.springframework.stereotype.Component;

@Component
public class ProduitServiceClientFallback implements ProduitServiceClient {

    @Override
    public ProduitResponse getProduitById(int id) {
        // Retourne un produit "fallback" en cas d'échec
        return new ProduitResponse(
                id,
                "Produit non disponible (Fallback)",
                "Le service produit est temporairement indisponible",
                "default.jpg",
                0.0
        );
    }

    @Override
    public Boolean produitExists(int id) {
        // Par défaut, on considère que le produit n'existe pas
        return false;
    }
}