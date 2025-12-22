package com.example.microservice_commandes.repository;

import com.example.microservice_commandes.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByDateAfter(LocalDate date);

    // Recherche par produit ID (nécessite une requête personnalisée)
    @Query("SELECT c FROM Commande c WHERE c.idProduit = :produitId")
    List<Commande> findByProduitId(@Param("produitId") Long produitId);

    // Recherche par période
    List<Commande> findByDateBetween(LocalDate startDate, LocalDate endDate);

    // Recherche par quantité minimale
    List<Commande> findByQuantiteGreaterThan(Integer quantite);

    // Compte le nombre de commandes
    long count();
}