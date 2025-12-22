package com.example.microservice_commandes.web.controller;

import com.example.microservice_commandes.configuration.ApplicationPropertiesConfiguration;
import com.example.microservice_commandes.model.Commande;
import com.example.microservice_commandes.service.CommandeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ApplicationPropertiesConfiguration config;

    // GET all commandes
    @GetMapping
    public List<Commande> getAllCommandes() {
        return commandeService.findAll();
    }

    // GET commandes récentes (utilise la propriété configurée)
    @GetMapping("/recentes")
    public List<Commande> getCommandesRecent() {
        int jours = config.getCommandesLast(); // Récupère la valeur 10 de la config
        return commandeService.findCommandesRecent(jours);
    }

    // GET by ID
    @GetMapping("/{id}")
    public ResponseEntity<Commande> getCommandeById(@PathVariable Long id) {
        Optional<Commande> commande = commandeService.findById(id);
        return commande.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST new commande
    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        return commandeService.save(commande);
    }

    // PUT update commande
    @PutMapping("/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable Long id, @RequestBody Commande commandeDetails) {
        Optional<Commande> commande = commandeService.findById(id);
        if (commande.isPresent()) {
            Commande existingCommande = commande.get();
            existingCommande.setDescription(commandeDetails.getDescription());
            existingCommande.setQuantite(commandeDetails.getQuantite());
            existingCommande.setDate(commandeDetails.getDate());
            existingCommande.setMontent(commandeDetails.getMontent());
            return ResponseEntity.ok(commandeService.save(existingCommande));
        }
        return ResponseEntity.notFound().build();
    }

    // DELETE commande
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCommande(@PathVariable Long id) {
        if (commandeService.findById(id).isPresent()) {
            commandeService.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
