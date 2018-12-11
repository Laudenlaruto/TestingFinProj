package com.bpc;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DBTransacTest {

    DBTransac gp11 = new DBTransac();


    @Test
    void rechInseePere() {
    }

    @Test
    void rechInseeEmp() {
    }

    @Test
    void rechInseeEnf() {
    }

    @Test
    void afficherEmployes() {

       // gp11.afficherEmployes();
    }

    @Test
    void afficherEmployesAnneeIdem() {
    }

    @Test
    void AjouterEmploye () {

    }

    @Test
    void supprimerEmploye() {
    }

    @Test
    void afficherAvantModifEmploye() {
    }

    @Test
    void modifierEmploye() {
    }

    @Test
    void afficherEmployeInsee() {
    }

    @Test
    void ajouterEnfant() {
        GraphicalUserInterface G2 = new GraphicalUserInterface();
        G2.nom.setText("test");
        G2.prenom.setText("test");
        G2.adresse.setText("11 test 11 road");
        G2.grade.setText("test master");
        G2.responsable.setText("mr test");
        G2.moisNaiss.setSelectedItem("Jan");
        G2.moisEmb.setSelectedItem("");
        gp11.ajouterEnfant(G2);
    }

    @Test
    void supprimerEnfant() {
    }

    @Test
    void afficherAvantModifEnfant() {
    }

    @Test
    void rechercherEnfantInseePropre() {
    }

    @Test
    void rechercherEnfantInseePere() {
    }

    @Test
    void modifierEnfant() {
    }

    @Test
    void fermerConnexion() {
    }
}