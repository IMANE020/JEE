package com.ui.microserviceui.controller;

import com.ui.microserviceui.model.Produit;
import com.ui.microserviceui.service.ProduitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/produits")
@Slf4j
public class ProduitController {

    @Autowired
    private ProduitService produitService;

    @GetMapping
    public String listProduits(Model model) {
        try {
            log.info("Loading products list");
            List<Produit> produits = produitService.getAllProduits();

            // Calcul des statistiques - ASSUREZ-VOUS que c'est un Double
            double moyennePrix = 0.0;
            long produitsPrixSuperieur = 0;

            if (!produits.isEmpty()) {
                // Calcul de la moyenne
                moyennePrix = produits.stream()
                        .filter(p -> p.getPrice() != null)
                        .mapToDouble(Produit::getPrice)
                        .average()
                        .orElse(0.0);

                // Compte les produits > 50€
                produitsPrixSuperieur = produits.stream()
                        .filter(p -> p.getPrice() != null && p.getPrice() > 50)
                        .count();
            }

            // Ajoutez les attributs comme des NUMBERS, pas des Strings
            model.addAttribute("produits", produits);
            model.addAttribute("moyennePrix", moyennePrix);  // ← Double
            model.addAttribute("produitsPrixSuperieur", produitsPrixSuperieur);  // ← Long
            model.addAttribute("totalProduits", produits.size());  // ← Integer

            return "produits/list";

        } catch (Exception e) {
            log.error("Error loading products: {}", e.getMessage());
            model.addAttribute("error", "Erreur lors du chargement des produits");
            return "produits/list";
        }
    }

    @GetMapping("/{id}")
    public String detailProduit(@PathVariable("id") Integer id, Model model) {
        try {
            log.info("Accès aux détails du produit ID: {}", id);

            if (id == null) {
                log.error("ID du produit est null");
                return "redirect:/produits";
            }

            Produit produit = produitService.getProduitById(id);

            if (produit == null) {
                log.warn("Produit non trouvé avec ID: {}", id);
                model.addAttribute("error", "Produit non trouvé");
                return "produits/detail";
            }

            log.info("Produit trouvé: {}", produit.getTitre());
            model.addAttribute("produit", produit);
            return "produits/detail";

        } catch (Exception e) {
            log.error("Erreur lors de la récupération du produit {}: {}", id, e.getMessage(), e);
            model.addAttribute("error", "Erreur lors du chargement du produit");
            return "produits/detail";
        }
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        Produit produit = new Produit();
        model.addAttribute("produit", produit);
        model.addAttribute("isEdit", false);
        return "produits/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        try {
            Produit produit = produitService.getProduitById(id);
            model.addAttribute("produit", produit);
            model.addAttribute("isEdit", true);
            return "produits/form";
        } catch (Exception e) {
            log.error("Error loading product for edit {}: {}", id, e.getMessage());
            return "redirect:/produits";
        }
    }

    @PostMapping("/save")
    public String saveProduit(@ModelAttribute Produit produit,
                              RedirectAttributes redirectAttributes) {
        try {
            Produit savedProduit = produitService.saveProduit(produit);

            if (produit.getId() == null) {
                redirectAttributes.addFlashAttribute("success", "Produit créé avec succès!");
            } else {
                redirectAttributes.addFlashAttribute("success", "Produit mis à jour avec succès!");
            }

        } catch (Exception e) {
            log.error("Error saving product: {}", e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de l'enregistrement du produit: " + e.getMessage());
        }
        return "redirect:/produits";
    }

    @GetMapping("/delete/{id}")
    public String deleteProduit(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            produitService.deleteProduit(id);
            redirectAttributes.addFlashAttribute("success", "Produit supprimé avec succès!");
        } catch (Exception e) {
            log.error("Error deleting product {}: {}", id, e.getMessage(), e);
            redirectAttributes.addFlashAttribute("error",
                    "Erreur lors de la suppression du produit: " + e.getMessage());
        }
        return "redirect:/produits";
    }
}