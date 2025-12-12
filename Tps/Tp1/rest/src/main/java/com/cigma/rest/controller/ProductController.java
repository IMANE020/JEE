package com.cigma.rest.controller;

import com.cigma.rest.model.Product;
import com.cigma.rest.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private IProductService productService;

    @GetMapping(value = "/products")
    public List<Product> getProducts() {
        return productService.getAll();
    }

    @GetMapping(value = "/products/{id}")
    public Product getProductById(@PathVariable(value = "id") Long productid) {
        return productService.getById(productid);
    }

    @PostMapping(value = "/products")
    public ResponseEntity<Object> createProduct(@RequestBody Product product) {
        productService.create(product);
        return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable(name = "id") Long id, @RequestBody Product product) {
        Product productFound = productService.getById(id);
        if (productFound == null) return ResponseEntity.notFound().build();
        productService.update(id, product);
        return new ResponseEntity<>("Prduct is updated successfully", HttpStatus.OK);
    }

    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable(name = "id") Long id) {
        Product productFound = productService.getById(id);
        if (productFound == null) return ResponseEntity.notFound().build();
        productService.delete(id);
        return new ResponseEntity<>("Product is deleted successfully", HttpStatus.OK);
    }

    public IProductService getProductService() {
        return productService;
    }
    public void setProductService(IProductService productService) {
        this.productService = productService;
    }
}
