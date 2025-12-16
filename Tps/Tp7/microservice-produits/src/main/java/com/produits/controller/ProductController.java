package com.produits.controller;

import com.produits.dao.ProductDao;
import com.produits.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class ProductController {

    @Autowired
    private ProductDao productDao;

    /**
     * Récupérer la liste de tous les produits
     * URL: GET http://localhost:9001/Produits
     */
    @GetMapping(value = "/Produits")
    public List<Product> listeDesProduits() {
        return productDao.findAll();
    }

    /**
     * Récupérer un produit par son ID
     * URL: GET http://localhost:9001/Produits/{id}
     */
    @GetMapping(value = "/Produits/{id}")
    public Optional<Product> recupererUnProduit(@PathVariable int id) {
        return productDao.findById(id);
    }

    /**
     * Endpoint de santé (health check)
     * URL: GET http://localhost:9001/health
     */
    @GetMapping(value = "/health")
    public String healthCheck() {
        return "Microservice Produits is running on port 9001";
    }

    /**
     * Endpoint pour obtenir des informations sur le service
     * URL: GET http://localhost:9001/info
     */
    @GetMapping(value = "/info")
    public String getServiceInfo() {
        long count = productDao.count();
        return "Microservice Produits - Total produits: " + count;
    }
}