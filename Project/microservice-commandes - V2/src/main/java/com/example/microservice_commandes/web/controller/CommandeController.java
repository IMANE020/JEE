package com.example.microservice_commandes.web.controller;

import com.example.microservice_commandes.configuration.ApplicationPropertiesConfiguration;
import com.example.microservice_commandes.model.Commande;
import com.example.microservice_commandes.service.CommandeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/commandes")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Commandes", description = "API de gestion des commandes")
public class CommandeController {

    private final CommandeService commandeService;
    private final ApplicationPropertiesConfiguration config;

    @GetMapping
    @Operation(summary = "Récupérer toutes les commandes")
    @ApiResponse(responseCode = "200", description = "Liste des commandes récupérée avec succès")
    public ResponseEntity<List<Commande>> getAllCommandes() {
        log.info("GET /api/commandes - Récupération de toutes les commandes");
        List<Commande> commandes = commandeService.findAll();
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/recentes")
    @Operation(summary = "Récupérer les commandes récentes")
    @ApiResponse(responseCode = "200", description = "Commandes récentes récupérées")
    public ResponseEntity<List<Commande>> getCommandesRecent() {
        int jours = config.getCommandesLast();
        log.info("GET /api/commandes/recentes - Derniers {} jours", jours);
        List<Commande> commandes = commandeService.findCommandesRecent(jours);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/produit/{produitId}")
    @Operation(summary = "Récupérer les commandes par produit")
    @ApiResponse(responseCode = "200", description = "Commandes du produit récupérées")
    public ResponseEntity<List<Commande>> getCommandesByProduit(
            @Parameter(description = "ID du produit", required = true)
            @PathVariable Long produitId) {
        log.info("GET /api/commandes/produit/{}", produitId);
        List<Commande> commandes = commandeService.findByProduitId(produitId);
        return ResponseEntity.ok(commandes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer une commande par ID")
    @ApiResponse(responseCode = "200", description = "Commande trouvée")
    @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    public ResponseEntity<Commande> getCommandeById(
            @Parameter(description = "ID de la commande", required = true)
            @PathVariable Long id) {
        log.info("GET /api/commandes/{}", id);
        return commandeService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Créer une nouvelle commande")
    @ApiResponse(responseCode = "201", description = "Commande créée avec succès")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<Commande> createCommande(
            @Parameter(description = "Commande à créer", required = true)
            @RequestBody Commande commande) {
        log.info("POST /api/commandes - Création d'une commande");

        try {
            // Utiliser la méthode avec validation produit
            Commande savedCommande = commandeService.createCommandeWithProductValidation(commande);

            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(savedCommande.getId())
                    .toUri();

            return ResponseEntity.created(location).body(savedCommande);

        } catch (IllegalArgumentException e) {
            log.error("Erreur de validation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            log.error("Erreur lors de la création: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(null);
        }
    }

    @PostMapping("/simple")
    @Operation(summary = "Créer une commande sans validation produit")
    @ApiResponse(responseCode = "201", description = "Commande créée")
    public ResponseEntity<Commande> createCommandeSimple(@RequestBody Commande commande) {
        log.info("POST /api/commandes/simple - Création simple");
        Commande savedCommande = commandeService.save(commande);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .replacePath("/api/commandes/{id}")
                .buildAndExpand(savedCommande.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedCommande);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour une commande")
    @ApiResponse(responseCode = "200", description = "Commande mise à jour")
    @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    public ResponseEntity<Commande> updateCommande(
            @Parameter(description = "ID de la commande", required = true)
            @PathVariable Long id,
            @Parameter(description = "Nouvelles données de la commande", required = true)
            @RequestBody Commande commandeDetails) {
        log.info("PUT /api/commandes/{}", id);

        return commandeService.findById(id)
                .map(existingCommande -> {
                    existingCommande.setDescription(commandeDetails.getDescription());
                    existingCommande.setQuantite(commandeDetails.getQuantite());
                    existingCommande.setDate(commandeDetails.getDate());
                    existingCommande.setIdProduit(commandeDetails.getIdProduit());

                    // Si le montant est fourni, l'utiliser, sinon le recalculer
                    if (commandeDetails.getMontant() != null) {
                        existingCommande.setMontant(commandeDetails.getMontant());
                    } else {
                        existingCommande.setMontant(null); // Sera recalculé à la sauvegarde
                    }

                    Commande updated = commandeService.save(existingCommande);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer une commande")
    @ApiResponse(responseCode = "204", description = "Commande supprimée")
    @ApiResponse(responseCode = "404", description = "Commande non trouvée")
    public ResponseEntity<Void> deleteCommande(
            @Parameter(description = "ID de la commande à supprimer", required = true)
            @PathVariable Long id) {
        log.info("DELETE /api/commandes/{}", id);

        if (commandeService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        commandeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Compter le nombre total de commandes")
    @ApiResponse(responseCode = "200", description = "Nombre de commandes")
    public ResponseEntity<Long> countCommandes() {
        log.info("GET /api/commandes/count");
        long count = commandeService.countCommandes();
        return ResponseEntity.ok(count);
    }
}