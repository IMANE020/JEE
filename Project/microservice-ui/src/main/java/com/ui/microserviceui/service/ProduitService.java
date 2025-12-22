package com.ui.microserviceui.service;

import com.ui.microserviceui.model.Produit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
public class ProduitService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${produit.service.url}")
    private String produitServiceUrl;

    public List<Produit> getAllProduits() {
        try {
            log.info("Fetching products from: {}/produits", produitServiceUrl);

            String url = produitServiceUrl + "/produits";
            ResponseEntity<Produit[]> response = restTemplate.getForEntity(url, Produit[].class);

            List<Produit> produits = Arrays.asList(response.getBody());
            log.info("Retrieved {} products", produits.size());

            return produits;
        } catch (Exception e) {
            log.error("Error fetching products: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to fetch products: " + e.getMessage());
        }
    }

    public Produit getProduitById(Integer id) {
        try {
            log.info("Fetching product ID: {} from: {}/produits/{}", id, produitServiceUrl, id);

            String url = produitServiceUrl + "/produits/" + id;
            ResponseEntity<Produit> response = restTemplate.getForEntity(url, Produit.class);

            return response.getBody();
        } catch (Exception e) {
            log.error("Error fetching product {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Product not found with ID: " + id);
        }
    }

    public Produit saveProduit(Produit produit) {
        try {
            log.info("Saving product: {}", produit.getTitre());

            String url = produitServiceUrl + "/produits";

            if (produit.getId() != null) {
                // Update
                url = produitServiceUrl + "/produits/" + produit.getId();
                restTemplate.put(url, produit);
                return produit;
            } else {
                // Create
                return restTemplate.postForObject(url, produit, Produit.class);
            }
        } catch (Exception e) {
            log.error("Error saving product: {}", e.getMessage(), e);
            throw new RuntimeException("Unable to save product: " + e.getMessage());
        }
    }

    public void deleteProduit(Integer id) {
        try {
            log.info("Deleting product ID: {}", id);

            String url = produitServiceUrl + "/produits/" + id;
            restTemplate.delete(url);
        } catch (Exception e) {
            log.error("Error deleting product {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Unable to delete product: " + id);
        }
    }
}