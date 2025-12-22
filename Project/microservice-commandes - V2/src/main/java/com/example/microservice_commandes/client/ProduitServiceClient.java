package com.example.microservice_commandes.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "microservice-produits",
        fallback = ProduitServiceClientFallback.class  // Pour Hystrix
)
public interface ProduitServiceClient {

    @GetMapping("/produits/{id}")
    ProduitResponse getProduitById(@PathVariable("id") int id);

    @GetMapping("/produits/exists/{id}")
    Boolean produitExists(@PathVariable("id") int id);
}
