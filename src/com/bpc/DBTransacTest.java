package com.bpc;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class DBTransacTest {

    DBTransac gp11 = new DBTransac();


    @Test
    void AjouterEmploye () {
        GraphicalUserInterface G2 = new GraphicalUserInterface();
        G2.lancer();
        G2.inseeTexte.setText("100");
        G2.nom.setText("test");
        G2.prenom.setText("test");
        G2.adresse.setText("11 test 11 road");
        G2.grade.setText("test master");
        G2.responsable.setText("mr test");
        G2.moisNaiss.setSelectedItem("Jan");
        G2.moisEmb.setSelectedItem("Jan");
        G2.jNaiss.setText("10");
        G2.jEmb.setText("9");
        G2.aNaiss.setText("1999");
        G2.aEmb.setText("2007");
        gp11.ajouterEmploye(G2);
        G2.inseeTexte.setText("100");
        assertTrue(gp11.rechInseeEmp(G2));

        // Employe with incomplete data
        GraphicalUserInterface G3 = new GraphicalUserInterface();
        G3.lancer();
        G3.inseeTexte.setText("100");
        G3.nom.setText("test");
        G3.grade.setText("test master");
        G3.responsable.setText("mr test");
        G3.moisNaiss.setSelectedItem("Jan");
        G3.jEmb.setText("9");
        G3.aEmb.setText("2007");
        gp11.ajouterEmploye(G3);
        G3.inseeTexte.setText("100");
        assertFalse(gp11.rechInseeEmp(G2));
    }

    @Test
    void supprimerEmploye() {
        GraphicalUserInterface G2 = new GraphicalUserInterface();
        G2.lancer();
        this.ajouterEnfant();
        G2.inseeTexte.setText("100");
        gp11.supprimerEmploye(G2);
        assertFalse(gp11.rechInseeEmp(G2));
    }

    @Test
    void modifierEmploye() {
        GraphicalUserInterface G2 = new GraphicalUserInterface();
        G2.lancer();
        this.AjouterEmploye();
        G2.inseeTexte.setText("100");
        gp11.afficherAvantModifEmploye(G2);
        GraphicalUserInterface G1 = G2;
        G2.grade.setText("test ultimate master");
        G2.responsable.setText("miss test");
        gp11.modifierEmploye(G2);
        G2.inseeTexte.setText("100");
        InputDialog resDialog = new InputDialog(new Frame(),"test",G2);
        gp11.afficherEmployeInsee(G2,resDialog);
        assertFalse(G1.grade.getText().equals(G2.grade.getText()) && G1.responsable.getText().equals(G2.responsable.getText()));
    }

    @Test
    void ajouterEnfant() {
        GraphicalUserInterface G2 = new GraphicalUserInterface();
        G2.lancer();
        G2.inseeTexte.setText("101");
        G2.inseePereTexte.setText("100");
        G2.nom.setText("test");
        G2.prenom.setText("test");
        G2.moisNaiss.setSelectedItem("Jan");
        G2.jNaiss.setText("10");
        G2.aNaiss.setText("1999");
        G2.hobby.setText("kite");
        gp11.ajouterEnfant(G2);
        G2.inseeTexte.setText("101");
        assertTrue(gp11.rechInseeEnf(G2));
    }

    @Test
    void supprimerEnfant() {
        GraphicalUserInterface G2 = new GraphicalUserInterface();
        G2.lancer();
        this.ajouterEnfant();
        G2.inseeTexte.setText("101");
        gp11.supprimerEnfant(G2);
        assertFalse(gp11.rechInseeEmp(G2));
    }



    @Test
    void modifierEnfant() {
        GraphicalUserInterface G2 = new GraphicalUserInterface();
        G2.lancer();
        this.ajouterEnfant();
        G2.inseeTexte.setText("100");
        gp11.afficherAvantModifEnfant(G2);
        GraphicalUserInterface G1 = G2;
        G2.nom.setText("super uper name of insanity");
        G2.hobby.setText("surf");
        gp11.modifierEnfant(G2);
        G2.inseeTexte.setText("100");


        assertFalse(G1.nom.getText().equals(G2.nom.getText()) && G1.hobby.getText().equals(G2.hobby.getText()));
    }

    @Test
    void fermerConnexion() {
    }
}