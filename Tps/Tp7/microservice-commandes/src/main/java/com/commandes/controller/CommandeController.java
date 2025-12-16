package com.commandes.controller;

import com.commandes.dao.CommandeDao;
import com.commandes.model.Commande;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class CommandeController {

    @Autowired
    private CommandeDao commandeDao;

    @PostMapping(value = "/commandes")
    public ResponseEntity<Commande> ajouterCommande(@RequestBody Commande commande) {
        if (commande.getDateCommande() == null) {
            commande.setDateCommande(new Date());
        }
        if (commande.getCommandePayee() == null) {
            commande.setCommandePayee(false);
        }

        Commande nouvelleCommande = commandeDao.save(commande);
        return new ResponseEntity<>(nouvelleCommande, HttpStatus.CREATED);
    }

    @GetMapping(value = "/commandes")
    public List<Commande> listeDesCommandes() {
        return commandeDao.findAll();
    }

    @GetMapping(value = "/commandes/{id}")
    public ResponseEntity<Commande> recupererUneCommande(@PathVariable int id) {
        Optional<Commande> commande = commandeDao.findById(id);
        return commande.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(value = "/commandes/{id}")
    public ResponseEntity<Commande> updateCommande(@PathVariable int id, @RequestBody Commande commandeDetails) {
        Optional<Commande> commandeOptional = commandeDao.findById(id);

        if (commandeOptional.isPresent()) {
            Commande commande = commandeOptional.get();
            commande.setProductId(commandeDetails.getProductId());
            commande.setQuantite(commandeDetails.getQuantite());
            commande.setCommandePayee(commandeDetails.getCommandePayee());

            Commande updatedCommande = commandeDao.save(commande);
            return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/commandes/{id}/payer")
    public ResponseEntity<Commande> marquerCommandePayee(@PathVariable int id) {
        Optional<Commande> commandeOptional = commandeDao.findById(id);

        if (commandeOptional.isPresent()) {
            Commande commande = commandeOptional.get();
            commande.setCommandePayee(true);

            Commande updatedCommande = commandeDao.save(commande);
            return new ResponseEntity<>(updatedCommande, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/commandes/produit/{productId}")
    public List<Commande> getCommandesParProduit(@PathVariable Integer productId) {
        return commandeDao.findByProductId(productId);
    }

    @GetMapping(value = "/commandes/statut/{payee}")
    public List<Commande> getCommandesParStatut(@PathVariable Boolean payee) {
        return commandeDao.findByCommandePayee(payee);
    }

    @DeleteMapping(value = "/commandes/{id}")
    public ResponseEntity<Void> supprimerCommande(@PathVariable int id) {
        if (commandeDao.existsById(id)) {
            commandeDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/health")
    public String healthCheck() {
        return "Microservice Commandes is running on port 9002";
    }

    @GetMapping(value = "/info")
    public String getServiceInfo() {
        long totalCommandes = commandeDao.count();
        long commandesPayees = commandeDao.countByCommandePayee(true);
        return String.format("Microservice Commandes - Total: %d, Payées: %d, En attente: %d",
                totalCommandes, commandesPayees, totalCommandes - commandesPayees);
    }
}