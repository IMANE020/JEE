package com.paiement.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer idCommande;

    private Double montant;

    private Long numeroCarte;

    @Temporal(TemporalType.TIMESTAMP)
    private Date datePaiement;

    private Boolean paiementAccepte;

    public Paiement() {}

    public Paiement(Integer idCommande, Double montant, Long numeroCarte) {
        this.idCommande = idCommande;
        this.montant = montant;
        this.numeroCarte = numeroCarte;
        this.datePaiement = new Date();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Integer idCommande) {
        this.idCommande = idCommande;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Long getNumeroCarte() {
        return numeroCarte;
    }

    public void setNumeroCarte(Long numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

    public Boolean getPaiementAccepte() {
        return paiementAccepte;
    }

    public void setPaiementAccepte(Boolean paiementAccepte) {
        this.paiementAccepte = paiementAccepte;
    }

    @Override
    public String toString() {
        return "Paiement{" +
                "id=" + id +
                ", idCommande=" + idCommande +
                ", montant=" + montant +
                ", numeroCarte=" + numeroCarte +
                ", datePaiement=" + datePaiement +
                ", paiementAccepte=" + paiementAccepte +
                '}';
    }
}