package com.commandes.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Commande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private Integer productId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateCommande;

    private Integer quantite;

    private Boolean commandePayee = false;

    public Commande() {}

    public Commande(Integer productId, Date dateCommande, Integer quantite) {
        this.productId = productId;
        this.dateCommande = dateCommande;
        this.quantite = quantite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
    }

    public Integer getQuantite() {
        return quantite;
    }

    public void setQuantite(Integer quantite) {
        this.quantite = quantite;
    }

    public Boolean getCommandePayee() {
        return commandePayee;
    }

    public void setCommandePayee(Boolean commandePayee) {
        this.commandePayee = commandePayee;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id=" + id +
                ", productId=" + productId +
                ", dateCommande=" + dateCommande +
                ", quantite=" + quantite +
                ", commandePayee=" + commandePayee +
                '}';
    }
}