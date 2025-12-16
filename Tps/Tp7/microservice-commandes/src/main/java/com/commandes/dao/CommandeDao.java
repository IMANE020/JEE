package com.commandes.dao;

import com.commandes.model.Commande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface CommandeDao extends JpaRepository<Commande, Integer> {

    List<Commande> findByProductId(Integer productId);

    List<Commande> findByCommandePayee(Boolean payee);

    List<Commande> findByDateCommandeAfter(Date date);

    List<Commande> findByDateCommandeBefore(Date date);

    List<Commande> findByDateCommandeBetween(Date startDate, Date endDate);

    Long countByProductId(Integer productId);

    Long countByCommandePayee(Boolean payee);

    @Query("SELECT c FROM Commande c WHERE c.productId = :productId AND c.commandePayee = true")
    List<Commande> findPaidOrdersByProductId(@Param("productId") Integer productId);

    @Query("SELECT COUNT(c) FROM Commande c WHERE c.productId = :productId")
    Long countOrdersByProduct(@Param("productId") Integer productId);

    @Query("SELECT c FROM Commande c ORDER BY c.dateCommande DESC")
    List<Commande> findAllOrderByDateDesc();
}