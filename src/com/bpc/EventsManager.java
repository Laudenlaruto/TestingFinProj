package com.bpc;

import java.awt.event.*;

/**
 * La classe EventsManager fournit des champs et des méthodes nécessaires pour
 * tester le modéle de gestion d'événement du JDK 1.1 ou JDK 1.2
 *
 * Les évenements testés ici sont de type action.
 *
 * Une classe écouteur interne EventsManager est implémentée pour écouter et
 * traiter les évéments "ActionEvent".
 */
public class EventsManager implements ActionListener {

    public static int codeEv;
    //Ce code est attribué é chaque événnement
    //lié au clic sur VALIDER ou RECHERCHER

    ActionServices tr = new ActionServices();
    //Création d'un objet Traitements pour accéder aux méthodes de cette classe

    public GraphicalUserInterface G1;

    /**
     * Cette méthode permet l'enregistrement des écouteurs via AddActionListener
     * auquel on passe comme argument l'objet courant "this"
     *
     * @param G2 Un objet de type GraphicalUserInterface
     */
    public void enregistrerListener(GraphicalUserInterface G2) {
        G1 = G2;

        G1.employe1.addActionListener(this);
        G1.employe2.addActionListener(this);
        G1.employe3.addActionListener(this);
        G1.employe4.addActionListener(this);
        G1.enfant1.addActionListener(this);
        G1.enfant2.addActionListener(this);
        G1.enfant3.addActionListener(this);
        G1.enfant4.addActionListener(this);
        G1.tous.addActionListener(this);
        G1.insee.addActionListener(this);
        G1.memeAnnee.addActionListener(this);
        G1.viaInseePere.addActionListener(this);
        G1.viaInseePropre.addActionListener(this);
        G1.quitter.addActionListener(this);
        G1.rechercher.addActionListener(this);
        G1.effacer.addActionListener(this);
        G1.valider.addActionListener(this);
        G1.annuler.addActionListener(this);

    }

    /**
     * Cette méthode effectue le dispatching des actions en fonction du résultat
     * de la méthode getSource() attaché à l'objet de type ActionEvent
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == G1.employe1) //insérer employé
        {
            tr.afficherEmployeAjouter(G1);
            codeEv = 1;
        }

        if (e.getSource() == G1.enfant1) //insérer enfant
        {
            tr.afficherEnfantAjouter(G1);
            codeEv = 2;
        }

        if (e.getSource() == G1.employe2) //supprimer employé
        {
            tr.afficherEmployeSupprimer(G1);
            codeEv = 3;
        }

        if (e.getSource() == G1.enfant2) //supprimer enfant
        {
            tr.afficherEnfantSupprimer(G1);
            codeEv = 4;
        }

        if (e.getSource() == G1.employe3) //modifier employé
        {
            tr.afficherEmployeModifier(G1);
            codeEv = 5;
        }

        if (e.getSource() == G1.enfant3) //modifier enfant
        {
            tr.afficherEnfantModifier(G1);
            codeEv = 6;
        }

        if (e.getSource() == G1.tous) //afficher tous les employés
        {
            tr.lancerAfficherEmployes(G1);
        }

        if (e.getSource() == G1.insee) //invite pour taper le #insee de l'employé
        {
            tr.lancerDialogInseeEmp(G1);
        }

        if (e.getSource() == G1.memeAnnee) {
            tr.lancerDialogEmpAnnee(G1);
        }

        if (e.getSource() == G1.viaInseePere) {
            tr.lancerDialogInseePere(G1);
        }

        if (e.getSource() == G1.viaInseePropre) {
            tr.lancerDialogInseeEnf(G1);
        }

        if (e.getSource() == G1.rechercher) {
            tr.lancerRechercher(codeEv, G1);
        }

        if (e.getSource() == G1.valider) {
            tr.lancerValider(codeEv, G1);
        }

        if (e.getSource() == G1.effacer) {
            tr.resetChamps(G1);
        }

        // les évennements ci-dessous ne font pas appel 
        // à l'"objet de la classe de traitements" tr
        // =============================================================
        if (e.getSource() == G1.annuler) {
            G1.cl.show(G1.panelCard0, "1");
        }

        if (e.getSource() == G1.quitter) {
            System.exit(0);
        }

        // =============================================================
    }

}
