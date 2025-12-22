package com.mproduits.microserviceproduits.web.controller;

import com.mproduits.microserviceproduits.model.Product;
import com.mproduits.microserviceproduits.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/produits")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Produits", description = "API de gestion des produits")
public class ProductController implements HealthIndicator {

    private final ProductService productService;

    @GetMapping
    @Operation(summary = "Récupérer tous les produits")
    @ApiResponse(responseCode = "200", description = "Liste des produits récupérée")
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("GET /api/produits - Récupération de tous les produits");
        List<Product> products = productService.findAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Récupérer un produit par ID")
    @ApiResponse(responseCode = "200", description = "Produit trouvé")
    @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    public ResponseEntity<Product> getProductById(
            @Parameter(description = "ID du produit", required = true)
            @PathVariable Integer id) {
        log.info("GET /api/produits/{}", id);
        return productService.findProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/exists/{id}")
    @Operation(summary = "Vérifier si un produit existe")
    @ApiResponse(responseCode = "200", description = "Existence vérifiée")
    public ResponseEntity<Boolean> productExists(
            @Parameter(description = "ID du produit à vérifier", required = true)
            @PathVariable Integer id) {
        log.info("GET /api/produits/exists/{}", id);
        boolean exists = productService.productExists(id);
        return ResponseEntity.ok(exists);
    }

    @PostMapping
    @Operation(summary = "Créer un nouveau produit")
    @ApiResponse(responseCode = "201", description = "Produit créé")
    @ApiResponse(responseCode = "400", description = "Données invalides")
    public ResponseEntity<Product> createProduct(
            @Parameter(description = "Produit à créer", required = true)
            @RequestBody Product product) {
        log.info("POST /api/produits - Création d'un produit");
        Product savedProduct = productService.saveProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Mettre à jour un produit")
    @ApiResponse(responseCode = "200", description = "Produit mis à jour")
    @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    public ResponseEntity<Product> updateProduct(
            @Parameter(description = "ID du produit", required = true)
            @PathVariable Integer id,
            @Parameter(description = "Nouvelles données du produit", required = true)
            @RequestBody Product productDetails) {
        log.info("PUT /api/produits/{}", id);
        try {
            Product updatedProduct = productService.updateProduct(id, productDetails);
            return ResponseEntity.ok(updatedProduct);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Supprimer un produit")
    @ApiResponse(responseCode = "204", description = "Produit supprimé")
    @ApiResponse(responseCode = "404", description = "Produit non trouvé")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID du produit à supprimer", required = true)
            @PathVariable Integer id) {
        log.info("DELETE /api/produits/{}", id);
        if (!productService.productExists(id)) {
            return ResponseEntity.notFound().build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    @Operation(summary = "Compter le nombre total de produits")
    @ApiResponse(responseCode = "200", description = "Nombre de produits")
    public ResponseEntity<Long> countProducts() {
        log.info("GET /api/produits/count");
        long count = productService.countProducts();
        return ResponseEntity.ok(count);
    }

    @Override
    public Health health() {
        log.info("Vérification de santé du service Produits");
        long productCount = productService.countProducts();

        if (productCount > 0) {
            return Health.up()
                    .withDetail("message", "Service Produits opérationnel")
                    .withDetail("nombreProduits", productCount)
                    .build();
        } else {
            return Health.down()
                    .withDetail("message", "Aucun produit dans la base de données")
                    .withDetail("nombreProduits", 0)
                    .build();
        }
    }
}