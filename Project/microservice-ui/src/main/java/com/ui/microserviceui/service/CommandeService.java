package com.ui.microserviceui.service;

import com.ui.microserviceui.model.Commande;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class CommandeService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${commande.service.url}")
    private String commandeServiceUrl;

    public List<Commande> getAllCommandes() {
        try {
            log.info("Fetching commandes from: {}/commandes", commandeServiceUrl);

            String url = commandeServiceUrl + "/commandes";
            ResponseEntity<Commande[]> response = restTemplate.getForEntity(url, Commande[].class);

            List<Commande> commandes = Arrays.asList(response.getBody());
            log.info("Retrieved {} commandes", commandes.size());

            return commandes;
        } catch (Exception e) {
            log.error("Error fetching commandes: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to fetch commandes: " + e.getMessage());
        }
    }

    public Commande getCommandeById(Long id) {
        try {
            log.info("Fetching commande ID: {} from: {}/commandes/{}", id, commandeServiceUrl, id);

            String url = commandeServiceUrl + "/commandes/" + id;
            ResponseEntity<Commande> response = restTemplate.getForEntity(url, Commande.class);

            return response.getBody();
        } catch (Exception e) {
            log.error("Error fetching commande {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Commande not found with ID: " + id);
        }
    }

    public Commande saveCommande(Commande commande) {
        try {
            log.info("Saving commande: {}", commande.getDescription());

            String url = commandeServiceUrl + "/commandes";

            if (commande.getId() != null) {
                // Update
                url = commandeServiceUrl + "/commandes/" + commande.getId();
                restTemplate.put(url, commande);
                return commande;
            } else {
                // Create - utilise /simple si disponible, sinon normal
                try {
                    url = commandeServiceUrl + "/commandes/simple";
                    return restTemplate.postForObject(url, commande, Commande.class);
                } catch (Exception e) {
                    // Fallback to regular endpoint
                    url = commandeServiceUrl + "/commandes";
                    return restTemplate.postForObject(url, commande, Commande.class);
                }
            }
        } catch (Exception e) {
            log.error("Error saving commande: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to save commande: " + e.getMessage());
        }
    }

    public void updateCommande(Long id, Commande commande) {
        try {
            log.info("Updating commande ID: {}", id);

            String url = commandeServiceUrl + "/commandes/" + id;
            restTemplate.put(url, commande);
        } catch (Exception e) {
            log.error("Error updating commande {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to update commande: " + id);
        }
    }

    public void deleteCommande(Long id) {
        try {
            log.info("Deleting commande ID: {}", id);

            String url = commandeServiceUrl + "/commandes/" + id;
            restTemplate.delete(url);
        } catch (Exception e) {
            log.error("Error deleting commande {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to delete commande: " + id);
        }
    }

    public List<Commande> getCommandesByProduit(Long produitId) {
        try {
            log.info("Fetching commandes for product ID: {}", produitId);

            String url = commandeServiceUrl + "/commandes/produit/" + produitId;
            ResponseEntity<Commande[]> response = restTemplate.getForEntity(url, Commande[].class);

            return Arrays.asList(response.getBody());
        } catch (Exception e) {
            log.error("Error fetching commandes for product {}: {}", produitId, e.getMessage(), e);
            throw new RuntimeException("Unable to fetch commandes for product: " + produitId);
        }
    }
}