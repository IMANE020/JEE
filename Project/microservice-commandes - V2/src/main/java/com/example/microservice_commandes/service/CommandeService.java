package com.example.microservice_commandes.service;

import com.example.microservice_commandes.model.Commande;

import java.util.List;
import java.util.Optional;

public interface CommandeService {

    List<Commande> findAll();

    Optional<Commande> findById(Long id);

    Commande save(Commande commande);

    Commande createCommandeWithProductValidation(Commande commande);

    void deleteById(Long id);

    List<Commande> findCommandesRecent(int jours);

    List<Commande> findByProduitId(Long produitId);

    long countCommandes();
}