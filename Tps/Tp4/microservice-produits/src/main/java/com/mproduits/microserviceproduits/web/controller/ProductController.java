package com.mproduits.microserviceproduits.web.controller;

import com.mproduits.microserviceproduits.configuration.ApplicationPropertiesConfiguration;
import com.mproduits.microserviceproduits.dao.ProductDao;
import com.mproduits.microserviceproduits.model.Product;
import com.mproduits.microserviceproduits.web.exceptions.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController implements HealthIndicator {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ApplicationPropertiesConfiguration appProperties;

    // Affiche la liste de tous les produits disponibles
    @GetMapping(value = "/Produits")
    public List<Product> listeDesProduits() {
        System.out.println("******** ProductController listeDesProduits() ");
        List<Product> products = productDao.findAll();

        if (products.isEmpty()) {
            throw new ProductNotFoundException("Aucun produit n'est disponible à la vente");
        }

        // Limiter la liste selon la configuration
        int limit = appProperties.getLimitDeProduits();
        List<Product> listelimitee = products.subList(0, Math.min(limit, products.size()));

        return listelimitee;
    }

    // Récupérer un produit par son id
    @GetMapping(value = "/Produits/{id}")
    public Optional<Product> recupererUnProduit(@PathVariable int id) {
        System.out.println("******** ProductController recupererUnProduit(@PathVariable int id) ");
        Optional<Product> product = productDao.findById(id);

        if (!product.isPresent()) {
            throw new ProductNotFoundException("Le produit correspondant à l'id " + id + " n'existe pas");
        }

        return product;
    }

    // Health check pour Actuator
    @Override
    public Health health() {
        System.out.println("****** Actuator : ProductController health() ");
        List<Product> products = productDao.findAll();
        if (products.isEmpty()) {
            return Health.down().build();
        }
        return Health.up().build();
    }
}
