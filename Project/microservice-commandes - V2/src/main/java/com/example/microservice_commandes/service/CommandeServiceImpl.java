package com.example.microservice_commandes.service;

import com.example.microservice_commandes.client.ProduitResponse;
import com.example.microservice_commandes.client.ProduitServiceClient;
import com.example.microservice_commandes.model.Commande;
import com.example.microservice_commandes.repository.CommandeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class CommandeServiceImpl implements CommandeService {

    private final CommandeRepository commandeRepository;
    private final ProduitServiceClient produitServiceClient;

    @Override
    public List<Commande> findAll() {
        log.info("Récupération de toutes les commandes");
        return commandeRepository.findAll();
    }

    @Override
    public Optional<Commande> findById(Long id) {
        log.info("Recherche de la commande avec ID: {}", id);
        return commandeRepository.findById(id);
    }

    @Override
    public Commande save(Commande commande) {
        log.info("Sauvegarde d'une nouvelle commande pour le produit ID: {}", commande.getIdProduit());

        // Si le montant n'est pas fourni, le calculer automatiquement
        if (commande.getMontant() == null && commande.getIdProduit() != null) {
            calculerMontantCommande(commande);
        }

        // Si la date n'est pas fournie, utiliser la date actuelle
        if (commande.getDate() == null) {
            commande.setDate(LocalDate.now());
        }

        return commandeRepository.save(commande);
    }

    @Override
    public Commande createCommandeWithProductValidation(Commande commande) {
        log.info("Création de commande avec validation produit pour ID: {}", commande.getIdProduit());

        // 1. Validation du produit via OpenFeign
        validateProduitExists(commande.getIdProduit());

        // 2. Calcul automatique du montant
        calculerMontantCommande(commande);

        // 3. Définir la date si non fournie
        if (commande.getDate() == null) {
            commande.setDate(LocalDate.now());
        }

        // 4. Sauvegarder
        Commande savedCommande = commandeRepository.save(commande);
        log.info("Commande créée avec ID: {} - Montant: {}", savedCommande.getId(), savedCommande.getMontant());

        return savedCommande;
    }

    @Override
    public void deleteById(Long id) {
        log.info("Suppression de la commande avec ID: {}", id);
        commandeRepository.deleteById(id);
    }

    @Override
    public List<Commande> findCommandesRecent(int jours) {
        log.info("Recherche des commandes des {} derniers jours", jours);
        LocalDate dateLimite = LocalDate.now().minusDays(jours);
        return commandeRepository.findByDateAfter(dateLimite);
    }

    @Override
    public List<Commande> findByProduitId(Long produitId) {
        log.info("Recherche des commandes pour le produit ID: {}", produitId);
        return commandeRepository.findAll().stream()
                .filter(commande -> produitId.equals(commande.getIdProduit()))
                .toList();
    }

    @Override
    public long countCommandes() {
        return commandeRepository.count();
    }

    // Méthodes privées d'aide
    private void validateProduitExists(Long produitId) {
        if (produitId == null) {
            throw new IllegalArgumentException("L'ID du produit ne peut pas être null");
        }

        try {
            Boolean produitExists = produitServiceClient.produitExists(produitId.intValue());

            if (produitExists == null || !produitExists) {
                throw new RuntimeException("Produit non trouvé avec ID: " + produitId);
            }

            log.info("Produit validé avec succès: ID {}", produitId);

        } catch (Exception e) {
            log.error("Erreur lors de la validation du produit ID {}: {}", produitId, e.getMessage());
            throw new RuntimeException("Service produit indisponible. Impossible de valider le produit.");
        }
    }

    private void calculerMontantCommande(Commande commande) {
        if (commande.getIdProduit() == null) {
            throw new IllegalArgumentException("L'ID du produit est requis pour calculer le montant");
        }

        if (commande.getQuantite() == null || commande.getQuantite() <= 0) {
            throw new IllegalArgumentException("La quantité doit être supérieure à 0");
        }

        try {
            // Récupérer le produit via OpenFeign
            ProduitResponse produit = produitServiceClient.getProduitById(commande.getIdProduit().intValue());

            if (produit == null || produit.getPrix() == null) {
                throw new RuntimeException("Impossible de récupérer le prix du produit");
            }

            // Calculer le montant
            Double montant = commande.getQuantite() * produit.getPrix();
            commande.setMontant(montant);

            log.info("Montant calculé: {} x {}€ = {}€",
                    commande.getQuantite(), produit.getPrix(), montant);

        } catch (Exception e) {
            log.error("Erreur lors du calcul du montant: {}", e.getMessage());
            throw new RuntimeException("Impossible de calculer le montant. Service produit indisponible.");
        }
    }
}
