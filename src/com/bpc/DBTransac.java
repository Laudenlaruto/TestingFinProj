package com.bpc;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

/**
 * Cette classe s'occupe de tout ce qui a rapport é la communication avec la
 * base de données via JDBC
 *
 * @see GespersonnelEvennements
 * @see ActionServices
 * @see GraphicalUserInterface
 * @see InputDialog
 * @see ResultsModel
 */
public class DBTransac {

    String nom, prenom, adresse, grade, responsable, hobby, moisNaiss, moisEmb;
    int insee, inseePere, jNaiss, jEmb, aNaiss, aEmb;
    ActionServices tr = new ActionServices();
    GraphicalUserInterface G1;
    InputDialog resDialog;
    Connection con;
    Statement stmnt;
    PreparedStatement pstmnt;

    /**
     * La construction d'un objet GespersonnelJdbc s'accompagne du chargement du
     * driver et de l'ouverture de la connexion
     */
    public DBTransac() {

        try {
            con = DriverManager.getConnection("jdbc:derby://localhost:1527/bpc");
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }

    }

//**** DEBUT  MODULE  EMPLOYE ******************
//*****Vérification de l'existence du #insee*****
    /**
     * Teste l'existence de ce #INSEE (pére) dans la base
     *
     * @param G2 Objet de type GraphicalUserInterface
     * @return test Une variable de type booléenne
     */
    public boolean rechInseePere(GraphicalUserInterface G2) {
        G1 = G2;
        boolean test = false;
        int inseePere = 0;
        try {
            inseePere = Integer.valueOf(G1.inseePereTexte.getText());
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery("select insee from employe where insee=" + inseePere);
            if (rs.next()) {
                return true;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return test;
    }

    /**
     * Teste l'existence de ce #INSEE (employé) dans la base
     *
     * @param G2 Objet de type GraphicalUserInterface
     * @return test Une variable de type booléenne
     */
    public boolean rechInseeEmp(GraphicalUserInterface G2) {
        G1 = G2;
        boolean test = false;
        int insee = 0;
        try {
            insee = Integer.valueOf(G1.inseeTexte.getText());
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery("select insee from employe where insee=" + insee);
            if (rs.next()) {
                test = true;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return test;
    }

    /**
     * Teste l'existence de ce #INSEE (enfant) dans la base
     *
     * @param G2 Objet de type GraphicalUserInterface
     * @return test Une variable de type booléenne
     */
    public boolean rechInseeEnf(GraphicalUserInterface G2) {
        G1 = G2;
        boolean test = false;
        try {
            int insee = Integer.valueOf(G1.inseeTexte.getText());
            stmnt = con.createStatement();
            ResultSet rs = stmnt.executeQuery("select insee from enfant where insee=" + insee);
            if (rs.next()) {
                test = true;
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        return test;
    }

//*****fin de vérification insee*****
    /**
     * Cette méthode permet l'affichage de tous les employés de la base quand
     * l'utilisateur choisit Afficher - Tous dans le menu principal
     *
     * @param G2 Objet de type GraphicalUserInterface
     * @see ResultsModel
     */
    public void afficherEmployes(GraphicalUserInterface G2) {
        G1 = G2;
        String requete = "select * from employe";
        JTextArea status = new JTextArea(1, 10);
        JLabel titreShowAll = new JLabel("D O S S I E R  --  E M P L O Y E S  --  BPC");

        try {

            stmnt = con.createStatement();

            ResultsModel rmodel = new ResultsModel(); // Creation du modéle
            rmodel.setResultSet(stmnt.executeQuery(requete));
            JTable table = new JTable(rmodel);            // Creation de la table é partir du modéle

            G1.panelShowAll.add(titreShowAll, BorderLayout.NORTH);

            JScrollPane sPane = new JScrollPane(table);        // Creation du scrollpane pour la table
            G1.panelShowAll.add(sPane, BorderLayout.CENTER);

            status.setLineWrap(true);
            status.setWrapStyleWord(true);
            G1.panelShowAll.add(status, BorderLayout.SOUTH);

            status.setText("BPC compte  " + rmodel.getRowCount() + " employés.");
        } catch (SQLException sqle) {
            System.err.println("SQLState: " + sqle.getSQLState());
            System.err.println("Message : " + sqle.getMessage());
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(G1.panelCentre, "Probléme Grave \n Relancer le programme", "Erreur Interne", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Cette méthode permet l'affichage de tous les employés de la base engagés
     * la meme année
     *
     * @param G2 Objet de type GraphicalUserInterface
     * @param resDialog
     * @see ResultsModel
     */
    public void afficherEmployesAnneeIdem(GraphicalUserInterface G2, InputDialog resDialog) {
        G1 = G2;

        String requete = "select * from employe where to_char(date_embauche,'YYYY')=" + resDialog.getValidatedText();
        JTextArea status = new JTextArea(1, 10);
        JLabel titreMemeAnnee = new JLabel("E M P L O Y E (S)  engagé(s) cette meme année --  BPC");

        try {
            G1.cl.show(G1.panelCard0, "4");

            stmnt = con.createStatement();

            ResultsModel rmodel = new ResultsModel(); // Creation du modéle
            rmodel.setResultSet(stmnt.executeQuery(requete));
            JTable tableMemeAnnee = new JTable(rmodel);            // Creation de la table é partir du modéle

            G1.panelShowMemeAnnee.add(titreMemeAnnee, BorderLayout.NORTH);

            JScrollPane memeAnneePane = new JScrollPane(tableMemeAnnee);        // Creation du scrollpane pour la table
            G1.panelShowMemeAnnee.add(memeAnneePane, BorderLayout.CENTER);

            status.setLineWrap(true);
            status.setWrapStyleWord(true);
            G1.panelShowMemeAnnee.add(status, BorderLayout.SOUTH);

            status.setText("Il y " + rmodel.getRowCount() + " employé(s) engagé(s) cette année-lé.");
        } catch (SQLException sqle) {
            System.err.println("SQLState: " + sqle.getSQLState());
            System.err.println("Message : " + sqle.getMessage());
        } catch (NullPointerException npe) {
            JOptionPane.showMessageDialog(G1.panelCentre, "Probléme Grave \n Relancer le programme", "Erreur Interne", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Cette méthode se charge de l'insertion d'un nouvel employé
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void ajouterEmploye(GraphicalUserInterface G2) {
        G1 = G2;
        insee = Integer.valueOf(G1.inseeTexte.getText());
        nom = G1.nom.getText();
        prenom = G1.prenom.getText();
        adresse = G1.adresse.getText();
        grade = G1.grade.getText();
        responsable = G1.responsable.getText();
        moisNaiss = G1.moisNaiss.getSelectedItem().toString();
        moisEmb = G1.moisEmb.getSelectedItem().toString();
        jNaiss = Integer.valueOf(G1.jNaiss.getText());
        jEmb = Integer.valueOf(G1.jEmb.getText());
        aNaiss = Integer.valueOf(G1.aNaiss.getText());
        aEmb = Integer.parseInt(G1.aEmb.getText());
        String ajoutEmp = "insert into employe values(?,?,?,?,?,?,?,?)";

        try {
            pstmnt = con.prepareStatement(ajoutEmp);

            pstmnt.setInt(1, insee);
            pstmnt.setString(2, nom);
            pstmnt.setString(3, prenom);
            pstmnt.setString(4, adresse);
            pstmnt.setString(5, grade);
            pstmnt.setString(6, responsable);
            pstmnt.setString(7, jNaiss + "-" + moisNaiss + "-" + aNaiss);
            pstmnt.setString(8, jEmb + "-" + moisEmb + "-" + aEmb);
            if (insee != 0) {
                int n = pstmnt.executeUpdate();
                //Affichage d'un message si enregistrement effectué avec succés
                if (n != 0) {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération Réussie!", "BPC - SUCCES", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération NON Réussie", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(G1.panelCentre, "Tapez un # insee\n non nul", "Echec", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(G1.panelCentre, "Opération NON Réussie!", "Echec", JOptionPane.ERROR_MESSAGE);
            System.err.println("SQLState: " + sqle.getSQLState());
            System.err.println("Message : " + sqle.getMessage());
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(G1.panelCentre, "Probléme de format \n de nombre", "Echec", JOptionPane.ERROR_MESSAGE);
        }

    }

    /**
     * Cette méthode se charge de la suppression d'un employé
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void supprimerEmploye(GraphicalUserInterface G2) {
        G1 = G2;
        try {
            if (rechInseeEmp(G1)) //***  vérification de l'existence du #insee
            {
                insee = Integer.valueOf(G1.inseeTexte.getText());

                String effaceEmp = "delete from employe where insee=?";

                pstmnt = con.prepareStatement(effaceEmp);

                pstmnt.setInt(1, insee);

                int n = pstmnt.executeUpdate();

                if (n != 0) {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération Réussie!", "BPC - SUCCES", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération NON Réussie", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(G1.panelCentre, "Insee Inexistant", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println("SQLState: " + sqle.getSQLState());
            System.err.println("Message : " + sqle.getMessage());
        }
    }

    /**
     * Suite é la demande de modification d'un employé,cette méthode affiche les
     * informations concernant l'enregistrement é modifier
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void afficherAvantModifEmploye(GraphicalUserInterface G2) {
        G1 = G2;
        try {
            if (rechInseeEmp(G1)) //si #insee Emp existe
            {
                insee = Integer.valueOf(G1.inseeTexte.getText());
                String requete = "select nom,prenom,adresse,grade,responsable,"
                        + "to_char(date_naissance,'dd'),to_char(date_embauche,'dd'),"
                        + "to_char(date_naissance,'YYYY'),to_char(date_embauche,'YYYY'),"
                        + "to_number(to_char(date_naissance,'mm')),to_number(to_char(date_embauche,'mm'))"
                        + "from employe where insee=" + insee;
                stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(requete);

                while (rs.next()) {
                    G1.nom.setText(rs.getString(1));
                    G1.prenom.setText(rs.getString(2));
                    G1.adresse.setText(rs.getString(3));
                    G1.grade.setText(rs.getString(4));
                    G1.responsable.setText(rs.getString(5));
                    G1.jNaiss.setText(rs.getString(6));
                    G1.jEmb.setText(rs.getString(7));
                    G1.aNaiss.setText(rs.getString(8));
                    G1.aEmb.setText(rs.getString(9));
                    G1.moisNaiss.setSelectedIndex(rs.getInt(10) - 1);
                    G1.moisEmb.setSelectedIndex(rs.getInt(11) - 1);
                }
            } else {
                JOptionPane.showMessageDialog(G1.panelCentre, "Insee Inexistant", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        G1.valider.setEnabled(true);
    }

    /**
     * Cette méthode se charge de la modification de l'entregistrement
     * correspondant é un employé
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void modifierEmploye(GraphicalUserInterface G2) {
        G1 = G2;

        insee = Integer.valueOf(G1.inseeTexte.getText());
        nom = G1.nom.getText();
        prenom = G1.prenom.getText();
        adresse = G1.adresse.getText();
        grade = G1.grade.getText();
        responsable = G1.responsable.getText();
        moisNaiss = G1.moisNaiss.getSelectedItem().toString();
        moisEmb = G1.moisEmb.getSelectedItem().toString();
        jNaiss = Integer.valueOf(G1.jNaiss.getText());
        jEmb = Integer.parseInt(G1.jEmb.getText());
        aNaiss = Integer.valueOf(G1.aNaiss.getText());
        aEmb = Integer.valueOf(G1.aEmb.getText());

        String modifEmploye = "update employe set nom=?,prenom=?,adresse=?,"
                + "grade=?,responsable=?,date_naissance=?,date_embauche=?"
                + "where insee=?";
        try {
            pstmnt = con.prepareStatement(modifEmploye);

            pstmnt.setString(1, nom);
            pstmnt.setString(2, prenom);
            pstmnt.setString(3, adresse);
            pstmnt.setString(4, grade);
            pstmnt.setString(5, responsable);
            pstmnt.setString(6, jNaiss + "-" + moisNaiss + "-" + aNaiss);
            pstmnt.setString(7, jEmb + "-" + moisEmb + "-" + aEmb);
            pstmnt.setInt(8, insee);

            int n = pstmnt.executeUpdate();
            if (n != 0) {
                JOptionPane.showMessageDialog(G1.panelCentre, "Opération Réussie!", "BPC - SUCCES", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(G1.panelCentre, "Opération NON Réussie", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println("SQLState: " + sqle.getSQLState());
            System.err.println("Message : " + sqle.getMessage());
        }
    }

    /**
     * Cette méthode récupére le #INSEE tapé dans la fenetre de dialogue et la
     * place dans la zone de texte correspondante
     *
     * @param G2 un objet de type GraphicalUserInterface
     * @param resDialog un objet de type InputDialog
     * @see InputDialog
     */
    public void afficherEmployeInsee(GraphicalUserInterface G2, InputDialog resDialog) {
        G1 = G2;
        G1.inseeTexte.setText(resDialog.getValidatedText());

    }

//********FIN  MODULE   EMPLOYE***********
//******** DEBUT MODULE ENFANT *********** 
    /**
     * Cette méthode permet d'insérer les informations concernant un enfant dans
     * la base
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void ajouterEnfant(GraphicalUserInterface G2) {
        G1 = G2;
        try {
            insee = Integer.valueOf(G1.inseeTexte.getText());
            inseePere = Integer.parseInt(G1.inseePereTexte.getText());
            nom = G1.nom.getText();
            prenom = G1.prenom.getText();
            moisNaiss = G1.moisNaiss.getSelectedItem().toString();
            jNaiss = Integer.valueOf(G1.jNaiss.getText());
            aNaiss = Integer.valueOf(G1.aNaiss.getText());
            hobby = G1.hobby.getText();

            String ajoutEnf = "insert into enfant values(?,?,?,?,?,?)";

            pstmnt = con.prepareStatement(ajoutEnf);

            pstmnt.setInt(1, insee);
            pstmnt.setInt(2, inseePere);
            pstmnt.setString(3, nom);
            pstmnt.setString(4, prenom);
            pstmnt.setString(5, jNaiss + "-" + moisNaiss + "-" + aNaiss); //artifice pour etre "compliant" au format "date" d'Oracle
            pstmnt.setString(6, hobby);

            //Vérification de l'existence du # insee du pére dans la base avant toute opération
            if (rechInseePere(G1)) //si #insee Pere existe
            {
                int n = pstmnt.executeUpdate();
                if (n != 0) {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération Réussie!", "BPC - SUCCES", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération NON Réussie \n Vérifiez #insee", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
                }
            } else // si #insee Pere n'existe pas
            {
                JOptionPane.showMessageDialog(G1.panelCentre, "Insee Père Inexistant", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println("SQLState: " + sqle.getSQLState());
            System.err.println("Message : " + sqle.getMessage());
        }
    }

    /**
     * Cette méthode permet de supprimer un enregistrement relatif é un enfant
     * dans la base
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void supprimerEnfant(GraphicalUserInterface G2) {
        G1 = G2;
        try {
            insee = Integer.valueOf(G1.inseeTexte.getText());

            String effaceEnf = "delete from enfant where insee=?";

            pstmnt = con.prepareStatement(effaceEnf);

            pstmnt.setInt(1, insee);
            int n = pstmnt.executeUpdate();

            if (rechInseePere(G1)) //si #insee Pere existe
            {
                if (n != 0) {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération Réussie!", "BPC - SUCCES", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération NON Réussie \n Vérifiez #insee", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
                }
            } else // si #insee Pere n'existe pas
            {
                JOptionPane.showMessageDialog(G1.panelCentre, "Insee Père Inexistant", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println("SQLState: " + sqle.getSQLState());
            System.err.println("Message : " + sqle.getMessage());
        }
    }

    /**
     * Suite é la demande de modification d'un enfant,cette méthode vérifie
     * d'abord que le #INSEE du pére est dans la base puis affiche les
     * informations concernant l'enregistrement é modifier
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void afficherAvantModifEnfant(GraphicalUserInterface G2) {
        G1 = G2;
        try {
            if (rechInseePere(G1)) //si #insee Père existe
            {
                G1.inseePereTexte.setEnabled(false);
                insee = Integer.valueOf(G1.inseeTexte.getText());
                String requete = "select nom,prenom,hobby,"
                        + "to_char(date_naissance,'dd'),"
                        + "to_char(date_naissance,'YYYY'),"
                        + "to_number(to_char(date_naissance,'mm'))"
                        + "from enfant where insee=" + insee;
                stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(requete);

                while (rs.next()) {
                    G1.nom.setText(rs.getString(1));
                    G1.prenom.setText(rs.getString(2));
                    G1.hobby.setText(rs.getString(3));
                    G1.jNaiss.setText(rs.getString(4));
                    G1.aNaiss.setText(rs.getString(5));
                    G1.moisNaiss.setSelectedIndex(rs.getInt(6) - 1);
                }
            } else {
                JOptionPane.showMessageDialog(G1.panelCentre, "Vérifiez les #insee \n Parent et/ou Enfant  svp!", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        G1.valider.setEnabled(true);
    }

    /**
     * Cette méthode permet de rechercher si le #INSEE de l'enfant existe
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    void rechercherEnfantInseePropre(GraphicalUserInterface G2) {
        G1 = G2;
        try {
            if (rechInseeEnf(G1)) {
                insee = Integer.valueOf(G1.inseeTexte.getText());
                String requete = "select inseepere,nom,prenom,hobby,"
                        + "to_char(date_naissance,'dd'),"
                        + "to_char(date_naissance,'YYYY'),"
                        + "to_number(to_char(date_naissance,'mm'))"
                        + "from enfant where insee=" + insee;

                stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(requete);

                while (rs.next()) {
                    G1.inseePereTexte.setText(String.valueOf(rs.getInt(1)));
                    G1.nom.setText(rs.getString(2));
                    G1.prenom.setText(rs.getString(3));
                    G1.hobby.setText(rs.getString(4));
                    G1.jNaiss.setText(rs.getString(5));
                    G1.aNaiss.setText(rs.getString(6));
                    G1.moisNaiss.setSelectedIndex(rs.getInt(7) - 1);
                }
            } else {
                JOptionPane.showMessageDialog(G1.panelCentre, "Insee Inexistant", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        G1.valider.setEnabled(false);

    }

    /**
     * Cette méthode permet de rechercher si le #INSEE du pére de l'enfant
     * existe
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void rechercherEnfantInseePere(GraphicalUserInterface G2) {
        G1 = G2;
        try {
            if (rechInseePere(G1)) //si #insee Pére existe
            {
                inseePere = Integer.valueOf(G1.inseePereTexte.getText());
                String requete = "select insee,nom,prenom,hobby,"
                        + "to_char(date_naissance,'dd'),"
                        + "to_char(date_naissance,'YYYY'),"
                        + "to_number(to_char(date_naissance,'mm'))"
                        + "from enfant where inseepere=" + inseePere;

                stmnt = con.createStatement();
                ResultSet rs = stmnt.executeQuery(requete);

                while (rs.next()) {
                    G1.inseeTexte.setText(String.valueOf(rs.getInt(1)));
                    G1.nom.setText(rs.getString(2));
                    G1.prenom.setText(rs.getString(3));
                    G1.hobby.setText(rs.getString(4));
                    G1.jNaiss.setText(rs.getString(5));
                    G1.aNaiss.setText(rs.getString(6));
                    G1.moisNaiss.setSelectedIndex(rs.getInt(7) - 1);
                }
            } else {
                JOptionPane.showMessageDialog(G1.panelCentre, "Vérifiez les #insee \n père et/ou enfant  svp!", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println(sqle.getMessage());
        }
        G1.valider.setEnabled(false);

    }

    /**
     * Cette méthode permet de modifier les informations au sujet d'un enfant
     * contenues dans la base
     *
     * @param G2 Objet de type GraphicalUserInterface
     */
    public void modifierEnfant(GraphicalUserInterface G2) {
        G1 = G2;
        try {
            insee = Integer.valueOf(G1.inseeTexte.getText());
            inseePere = Integer.valueOf(G1.inseePereTexte.getText());
            nom = G1.nom.getText();
            prenom = G1.prenom.getText();
            moisNaiss = G1.moisNaiss.getSelectedItem().toString();
            jNaiss = Integer.valueOf(G1.jNaiss.getText());
            aNaiss = Integer.valueOf(G1.aNaiss.getText());
            hobby = G1.hobby.getText();

            String modifEnfant = "update enfant set nom=?,prenom=?,date_naissance=?,hobby=?"
                    + "where insee=?";

            pstmnt = con.prepareStatement(modifEnfant);

            pstmnt.setString(1, nom);
            pstmnt.setString(2, prenom);
            pstmnt.setString(3, adresse);
            pstmnt.setString(4, jNaiss + "-" + moisNaiss + "-" + aNaiss);
            pstmnt.setInt(5, insee);

            if (rechInseePere(G1)) //si #insee Pere existe
            {
                int n = pstmnt.executeUpdate();
                if (n != 0) {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération Réussie!", "BPC - SUCCES", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(G1.panelCentre, "Opération NON Réussie", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(G1.panelCentre, "Insee Père Inexistant", "BPC - ECHEC", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException sqle) {
            System.err.println("SQLState: " + sqle.getSQLState());
            System.err.println("Message : " + sqle.getMessage());
        }
    }

}
