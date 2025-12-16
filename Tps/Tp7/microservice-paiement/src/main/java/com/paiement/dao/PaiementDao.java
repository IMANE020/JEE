package com.paiement.dao;

import com.paiement.model.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PaiementDao extends JpaRepository<Paiement, Integer> {

    List<Paiement> findByIdCommande(Integer idCommande);

    List<Paiement> findByPaiementAccepte(Boolean accepte);

    List<Paiement> findByDatePaiementAfter(Date date);

    List<Paiement> findByDatePaiementBefore(Date date);

    List<Paiement> findByDatePaiementBetween(Date startDate, Date endDate);

    List<Paiement> findByMontantGreaterThan(Double montant);

    List<Paiement> findByMontantLessThan(Double montant);

    List<Paiement> findByMontantBetween(Double min, Double max);

    Long countByIdCommande(Integer idCommande);

    Long countByPaiementAccepte(Boolean accepte);

    @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.paiementAccepte = true")
    Double getTotalMontantAccepte();

    @Query("SELECT AVG(p.montant) FROM Paiement p WHERE p.paiementAccepte = true")
    Double getMoyenneMontant();

    @Query("SELECT p FROM Paiement p ORDER BY p.datePaiement DESC")
    List<Paiement> findAllOrderByDateDesc();

    @Query("SELECT COUNT(p) FROM Paiement p WHERE p.idCommande = :idCommande AND p.paiementAccepte = true")
    Long countPaiementsAcceptesParCommande(@Param("idCommande") Integer idCommande);
}