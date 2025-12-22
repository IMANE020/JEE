package com.ui.microserviceui.controller;

import com.ui.microserviceui.model.Commande;
import com.ui.microserviceui.model.Produit;
import com.ui.microserviceui.service.CommandeService;
import com.ui.microserviceui.service.ProduitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/commandes")
@Slf4j
public class CommandeController {

    @Autowired
    private CommandeService commandeService;

    @Autowired
    private ProduitService produitService;

    @GetMapping
    public String listCommandes(Model model) {
        try {
            log.info("Loading commandes list");
            List<Commande> commandes = commandeService.getAllCommandes();

            // Calcul des statistiques - ASSUREZ-VOUS que ce sont des Numbers
            int totalCommandes = 0;
            double totalMontant = 0.0;
            int totalQuantite = 0;

            if (commandes != null && !commandes.isEmpty()) {
                totalCommandes = commandes.size();

                totalMontant = commandes.stream()
                        .filter(c -> c.getMontant() != null)
                        .mapToDouble(Commande::getMontant)
                        .sum();

                totalQuantite = commandes.stream()
                        .filter(c -> c.getQuantite() != null)
                        .mapToInt(Commande::getQuantite)
                        .sum();
            }

            // Ajoutez les attributs comme des NUMBERS
            model.addAttribute("commandes", commandes);
            model.addAttribute("totalCommandes", totalCommandes);  // ← Integer
            model.addAttribute("totalMontant", totalMontant);      // ← Double
            model.addAttribute("totalQuantite", totalQuantite);    // ← Integer

            return "commandes/list";

        } catch (Exception e) {
            log.error("Error loading commandes: {}", e.getMessage(), e);
            model.addAttribute("error", "Erreur lors du chargement des commandes");
            return "commandes/list";
        }
    }

    @GetMapping("/edit")
    public String redirectEdit() {
        return "redirect:/commandes";
    }

    @GetMapping("/{id}")
    public String detailCommande(@PathVariable Long id, Model model) {
        try {
            Commande commande = commandeService.getCommandeById(id);
            model.addAttribute("commande", commande);
            return "commandes/detail";
        } catch (Exception e) {
            log.error("Error loading commande {}: {}", id, e.getMessage());
            return "redirect:/commandes";
        }
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        try {
            log.info("Showing add form for commande");

            Commande commande = new Commande();
            commande.setDate(LocalDate.now());
            commande.setQuantite(1);
            commande.setMontant(0.0);

            List<Produit> produits = produitService.getAllProduits();
            log.info("Found {} products", produits.size());

            model.addAttribute("commande", commande);
            model.addAttribute("produits", produits);
            model.addAttribute("isEdit", false);

            return "commandes/form";

        } catch (Exception e) {
            log.error("Error showing add form: {}", e.getMessage(), e);
            return "redirect:/commandes";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        try {
            log.info("Showing edit form for commande ID: {}", id);

            Commande commande = commandeService.getCommandeById(id);

            if (commande.getMontant() == null) {
                commande.setMontant(0.0);
            }

            List<Produit> produits = produitService.getAllProduits();
            log.info("Found {} products for edit", produits.size());

            model.addAttribute("commande", commande);
            model.addAttribute("produits", produits);
            model.addAttribute("isEdit", true);

            return "commandes/form";

        } catch (Exception e) {
            log.error("Error showing edit form for commande {}: {}", id, e.getMessage(), e);
            return "redirect:/commandes";
        }
    }

    @PostMapping("/save")
    public String saveCommande(@ModelAttribute Commande commande, Model model) {
        try {
            log.info("Saving commande with product ID: {}", commande.getIdProduit());

            // Calcul du montant si le produit est sélectionné
            if (commande.getIdProduit() != null) {
                try {
                    Produit produit = produitService.getProduitById(commande.getIdProduit().intValue());
                    if (produit != null && produit.getPrice() != null) {
                        commande.setMontant(commande.getQuantite() * produit.getPrice());
                    }
                } catch (Exception e) {
                    log.warn("Could not fetch product for price calculation: {}", e.getMessage());
                }
            }

            if (commande.getId() == null) {
                commandeService.saveCommande(commande);
                model.addAttribute("success", "Commande créée avec succès!");
            } else {
                commandeService.updateCommande(commande.getId(), commande);
                model.addAttribute("success", "Commande mise à jour avec succès!");
            }

            return "redirect:/commandes";

        } catch (Exception e) {
            log.error("Error saving commande: {}", e.getMessage(), e);

            // Recharger les produits pour le formulaire
            List<Produit> produits = produitService.getAllProduits();
            model.addAttribute("produits", produits);
            model.addAttribute("error", "Erreur lors de l'enregistrement: " + e.getMessage());
            model.addAttribute("isEdit", commande.getId() != null);

            return "commandes/form";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteCommande(@PathVariable Long id, Model model) {
        try {
            commandeService.deleteCommande(id);
            model.addAttribute("success", "Commande supprimée avec succès!");
        } catch (Exception e) {
            log.error("Error deleting commande {}: {}", id, e.getMessage(), e);
            model.addAttribute("error", "Erreur lors de la suppression: " + e.getMessage());
        }
        return "redirect:/commandes";
    }
}