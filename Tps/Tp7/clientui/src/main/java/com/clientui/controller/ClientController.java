package com.clientui.controller;

import com.clientui.beans.CommandeBean;
import com.clientui.beans.PaiementBean;
import com.clientui.beans.ProductBean;
import com.clientui.proxies.MicroserviceCommandeProxy;
import com.clientui.proxies.MicroservicePaiementProxy;
import com.clientui.proxies.MicroserviceProduitsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class ClientController {

    @Autowired
    private MicroserviceProduitsProxy produitsProxy;

    @Autowired
    private MicroserviceCommandeProxy commandesProxy;

    @Autowired
    private MicroservicePaiementProxy paiementProxy;

    // Page d'accueil - Liste des produits
    @RequestMapping("/")
    public String accueil(Model model) {
        List<ProductBean> produits = produitsProxy.listeDesProduits();
        model.addAttribute("produits", produits);
        return "Accueil";
    }

    // Page détail d'un produit
    @RequestMapping("/details-produit/{id}")
    public String ficheProduit(@PathVariable int id, Model model) {
        ProductBean produit = produitsProxy.recupererUnProduit(id);
        model.addAttribute("produit", produit);
        return "FicheProduit";
    }

    // Commander un produit
    @RequestMapping(value = "/commander-produit/{idProduit}/{montant}")
    public String passerCommande(@PathVariable int idProduit,
                                 @PathVariable Double montant,
                                 Model model) {

        CommandeBean commande = new CommandeBean();
        commande.setProductId(idProduit);
        commande.setQuantite(1);
        commande.setDateCommande(new Date());

        CommandeBean commandeAjoutee = commandesProxy.ajouterCommande(commande);

        model.addAttribute("commande", commandeAjoutee);
        model.addAttribute("montant", montant);

        return "Paiement";
    }

    // Payer une commande
    @RequestMapping(value = "/payer-commande/{idCommande}/{montantCommande}")
    public String payerCommande(@PathVariable int idCommande,
                                @PathVariable Double montantCommande,
                                Model model) {

        PaiementBean paiementAExecuter = new PaiementBean();
        paiementAExecuter.setIdCommande(idCommande);
        paiementAExecuter.setMontant(montantCommande);
        paiementAExecuter.setNumeroCarte(numcarte()); // Simulation CB

        ResponseEntity<PaiementBean> paiement =
                paiementProxy.payerUneCommande(paiementAExecuter);

        boolean paiementAccepte = paiement.getStatusCode() == HttpStatus.CREATED;
        model.addAttribute("paiementOk", paiementAccepte);

        return "confirmation";
    }

    // Générer un numéro de carte fictif
    private Long numcarte() {
        return ThreadLocalRandom.current().nextLong(1000000000000000L, 9000000000000000L);
    }
}