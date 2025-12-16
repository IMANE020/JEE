package com.clientui.beans;

public class PaiementBean {
    private int id;
    private Integer idCommande;
    private Double montant;
    private Long numeroCarte;

    // Constructeurs
    public PaiementBean() {}

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Integer getIdCommande() { return idCommande; }
    public void setIdCommande(Integer idCommande) { this.idCommande = idCommande; }

    public Double getMontant() { return montant; }
    public void setMontant(Double montant) { this.montant = montant; }

    public Long getNumeroCarte() { return numeroCarte; }
    public void setNumeroCarte(Long numeroCarte) { this.numeroCarte = numeroCarte; }
}