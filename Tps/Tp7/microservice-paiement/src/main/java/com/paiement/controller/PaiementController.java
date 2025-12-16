package com.paiement.controller;

import com.paiement.dao.PaiementDao;
import com.paiement.model.Paiement;
import com.paiement.proxies.CommandeProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
public class PaiementController {

    @Autowired
    private PaiementDao paiementDao;

    @Autowired
    private CommandeProxy commandeProxy;

    @PostMapping(value = "/paiement")
    public ResponseEntity<Paiement> payerUneCommande(@RequestBody Paiement paiement) {
        if (paiement.getDatePaiement() == null) {
            paiement.setDatePaiement(new Date());
        }

        // Simulation du traitement de paiement
        boolean paiementAccepte = simulerTraitementPaiement();
        paiement.setPaiementAccepte(paiementAccepte);

        // Sauvegarde du paiement
        Paiement nouveauPaiement = paiementDao.save(paiement);

        // Si le paiement est accepté, mettre à jour la commande
        if (paiementAccepte && paiement.getIdCommande() != null) {
            try {
                commandeProxy.marquerCommandePayee(paiement.getIdCommande());
                System.out.println("INFO - Commande " + paiement.getIdCommande() + " marquée comme payée avec succès");
            } catch (Exception e) {
                System.err.println("ERREUR - Impossible de mettre à jour la commande " + paiement.getIdCommande() + ": " + e.getMessage());
                // Log plus détaillé
                e.printStackTrace();
            }
        } else if (!paiementAccepte) {
            System.out.println("INFO - Paiement refusé pour la commande " + paiement.getIdCommande());
        }

        // Retourner la réponse appropriée
        if (paiementAccepte) {
            return new ResponseEntity<>(nouveauPaiement, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(nouveauPaiement, HttpStatus.PAYMENT_REQUIRED);
        }
    }

    private boolean simulerTraitementPaiement() {
        Random random = new Random();
        // 80% de chance de succès, 20% d'échec
        return random.nextDouble() > 0.2;
    }

    @GetMapping(value = "/paiements")
    public List<Paiement> listeDesPaiements() {
        return paiementDao.findAll();
    }

    @GetMapping(value = "/paiements/{id}")
    public ResponseEntity<Paiement> recupererUnPaiement(@PathVariable int id) {
        Optional<Paiement> paiement = paiementDao.findById(id);
        return paiement.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/paiements/commande/{idCommande}")
    public List<Paiement> getPaiementsParCommande(@PathVariable Integer idCommande) {
        return paiementDao.findByIdCommande(idCommande);
    }

    @GetMapping(value = "/paiements/statut/{accepte}")
    public List<Paiement> getPaiementsParStatut(@PathVariable Boolean accepte) {
        return paiementDao.findByPaiementAccepte(accepte);
    }

    @GetMapping(value = "/paiements/montant/sup/{montant}")
    public List<Paiement> getPaiementsMontantSuperieur(@PathVariable Double montant) {
        return paiementDao.findByMontantGreaterThan(montant);
    }

    @GetMapping(value = "/paiements/montant/inf/{montant}")
    public List<Paiement> getPaiementsMontantInferieur(@PathVariable Double montant) {
        return paiementDao.findByMontantLessThan(montant);
    }

    @PutMapping(value = "/paiements/{id}")
    public ResponseEntity<Paiement> updatePaiement(@PathVariable int id, @RequestBody Paiement paiementDetails) {
        Optional<Paiement> paiementOptional = paiementDao.findById(id);

        if (paiementOptional.isPresent()) {
            Paiement paiement = paiementOptional.get();
            paiement.setIdCommande(paiementDetails.getIdCommande());
            paiement.setMontant(paiementDetails.getMontant());
            paiement.setNumeroCarte(paiementDetails.getNumeroCarte());
            paiement.setPaiementAccepte(paiementDetails.getPaiementAccepte());

            // Si on change le statut d'un paiement en accepté, mettre à jour la commande
            if (paiementDetails.getPaiementAccepte() && !paiement.getPaiementAccepte()
                    && paiementDetails.getIdCommande() != null) {
                try {
                    commandeProxy.marquerCommandePayee(paiementDetails.getIdCommande());
                    System.out.println("INFO - Commande " + paiementDetails.getIdCommande() + " marquée comme payée après mise à jour");
                } catch (Exception e) {
                    System.err.println("ERREUR - Impossible de mettre à jour la commande: " + e.getMessage());
                }
            }

            Paiement updatedPaiement = paiementDao.save(paiement);
            return new ResponseEntity<>(updatedPaiement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(value = "/paiements/{id}")
    public ResponseEntity<Void> supprimerPaiement(@PathVariable int id) {
        if (paiementDao.existsById(id)) {
            paiementDao.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/statistiques")
    public ResponseEntity<String> getStatistiques() {
        long totalPaiements = paiementDao.count();
        long paiementsAcceptes = paiementDao.countByPaiementAccepte(true);
        Double totalMontant = paiementDao.getTotalMontantAccepte();
        Double moyenneMontant = paiementDao.getMoyenneMontant();

        String stats = String.format(
                "Statistiques Paiements:\n" +
                        "Total paiements: %d\n" +
                        "Paiements acceptés: %d (%.1f%%)\n" +
                        "Paiements refusés: %d (%.1f%%)\n" +
                        "Montant total accepté: %.2f €\n" +
                        "Moyenne montant: %.2f €",
                totalPaiements,
                paiementsAcceptes,
                totalPaiements > 0 ? (paiementsAcceptes * 100.0 / totalPaiements) : 0,
                totalPaiements - paiementsAcceptes,
                totalPaiements > 0 ? ((totalPaiements - paiementsAcceptes) * 100.0 / totalPaiements) : 0,
                totalMontant != null ? totalMontant : 0.0,
                moyenneMontant != null ? moyenneMontant : 0.0
        );

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @GetMapping(value = "/health")
    public String healthCheck() {
        return "Microservice Paiement is running on port 9003";
    }

    @GetMapping(value = "/info")
    public String getServiceInfo() {
        long totalPaiements = paiementDao.count();
        long paiementsAcceptes = paiementDao.countByPaiementAccepte(true);
        return String.format("Microservice Paiement - Total: %d, Acceptés: %d, Taux succès: %.1f%%",
                totalPaiements, paiementsAcceptes,
                totalPaiements > 0 ? (paiementsAcceptes * 100.0 / totalPaiements) : 0);
    }

    // NOUVELLE ENDPOINT - Pour réparer manuellement une commande
    @PatchMapping(value = "/reparer/commande/{idCommande}")
    public ResponseEntity<String> reparerCommandePayee(@PathVariable int idCommande) {
        try {
            // Vérifier s'il y a un paiement accepté pour cette commande
            List<Paiement> paiements = paiementDao.findByIdCommande(idCommande);
            boolean paiementAccepteExiste = paiements.stream()
                    .anyMatch(Paiement::getPaiementAccepte);

            if (paiementAccepteExiste) {
                commandeProxy.marquerCommandePayee(idCommande);
                return ResponseEntity.ok("Commande " + idCommande + " réparée avec succès (marquée comme payée)");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Aucun paiement accepté trouvé pour la commande " + idCommande);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erreur lors de la réparation: " + e.getMessage());
        }
    }
}