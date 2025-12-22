package com.example.microservice_commandes.service;

import com.example.microservice_commandes.model.Commande;
import com.example.microservice_commandes.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;
    public List<Commande> findAll() {
        return commandeRepository.findAll();
    }

    public Optional<Commande> findById(Long id) {
        return commandeRepository.findById(id);
    }

    public Commande save(Commande commande) {
        return commandeRepository.save(commande);
    }

    public void deleteById(Long id) {
        commandeRepository.deleteById(id);
    }

    public List<Commande> findCommandesRecent(int jours){
        LocalDate dateLimite = LocalDate.now().minusDays(jours);
        return commandeRepository.findByDateAfter(dateLimite);
    }
}
