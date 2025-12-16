package com.paiement.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "microservice-commandes", url = "localhost:9002")
public interface CommandeProxy {

    @PostMapping(value = "/commandes/{id}/payer")
    void marquerCommandePayee(@PathVariable("id") int id);
}