package com.example.microservice_commandes.repository;

import com.example.microservice_commandes.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    List<Commande> findByDateAfter(LocalDate date);

    //Compte le nombre de commandes
    long count();
}
