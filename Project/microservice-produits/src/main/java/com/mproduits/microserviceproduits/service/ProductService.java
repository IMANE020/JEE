package com.mproduits.microserviceproduits.service;

import com.mproduits.microserviceproduits.dao.ProductDao;
import com.mproduits.microserviceproduits.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public List<Product> findAllProducts() {
        log.info("Récupération de tous les produits");
        return productDao.findAll();
    }

    public Optional<Product> findProductById(Integer id) {
        log.info("Recherche du produit ID: {}", id);
        return productDao.findById(id);
    }

    public Product saveProduct(Product product) {
        log.info("Sauvegarde d'un nouveau produit: {}", product.getTitre());
        return productDao.save(product);
    }

    public Product updateProduct(Integer id, Product productDetails) {
        log.info("Mise à jour du produit ID: {}", id);
        return productDao.findById(id)
                .map(product -> {
                    product.setTitre(productDetails.getTitre());
                    product.setDescription(productDetails.getDescription());
                    product.setImage(productDetails.getImage());
                    product.setPrice(productDetails.getPrice());
                    return productDao.save(product);
                })
                .orElseThrow(() -> new RuntimeException("Produit non trouvé avec ID: " + id));
    }

    public void deleteProduct(Integer id) {
        log.info("Suppression du produit ID: {}", id);
        productDao.deleteById(id);
    }

    public boolean productExists(Integer id) {
        boolean exists = productDao.existsById(id);
        log.info("Vérification existence produit ID {}: {}", id, exists);
        return exists;
    }

    public long countProducts() {
        return productDao.count();
    }
}