package com.bpc;

import javax.swing.*;
import java.beans.*; //Property change stuff
import java.awt.*;
import java.awt.event.*;

/**
 * Cette classe permet de construire une fenetre de dialogue
 */
public class InputDialog extends JDialog {

    private String inputTexte = null;
    private JOptionPane optionPane;
    GraphicalUserInterface G1;

    /**
     * @return Le texte tapé dans la zone de texte
     */
    public String getValidatedText() {
        return inputTexte;
    }

    /**
     * Constructeur
     *
     * @param aFrame Objet de type Frame
     * @param aWord Un Objet de type String
     * @param parent Objet de type GraphicalUserInterface
     */
    public InputDialog(Frame aFrame, String aWord, GraphicalUserInterface parent) {
        super(aFrame, true);
        //final GraphicalUserInterface G1 = parent;

        setTitle("Dialogue - BPC");

        final String msgString1 = "Taper l'Information";
        final String msgString2 = "Nécessaire à Votre Recherche ";
        final JTextField textField = new JTextField(12);
        Object[] array = {msgString1, msgString2, textField};

        final String btnString1 = "Rechercher";
        final String btnString2 = "Annuler";

        Object[] options = {btnString1, btnString2};

        optionPane = new JOptionPane(array,
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.YES_NO_OPTION,
                null,
                options,
                options[0]);
        setContentPane(optionPane);

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                /*
                 * Instead of directly closing the window,
                 * we're going to change the JOptionPane's
                 * value property.
                 */
                optionPane.setValue(JOptionPane.CLOSED_OPTION);
            }
        });

        textField.addActionListener((ActionEvent e) -> {
            optionPane.setValue(btnString1);
        });

        optionPane.addPropertyChangeListener((PropertyChangeEvent e) -> {
            String prop = e.getPropertyName();
            
            if (isVisible() && (e.getSource() == optionPane) && (prop.equals(JOptionPane.VALUE_PROPERTY) || prop.equals(JOptionPane.INPUT_VALUE_PROPERTY))) {
                Object value = optionPane.getValue();
                
                if (value == JOptionPane.UNINITIALIZED_VALUE) {
                    //ignore reset
                    return;
                }
                
                // Reset the JOptionPane's value.
                // If you don't do this, then if the user
                // presses the same button next time, no
                // property change event will be fired.
                optionPane.setValue(JOptionPane.UNINITIALIZED_VALUE);
                
                if (value.equals(btnString1)) {
                    inputTexte = textField.getText();
                    setVisible(false);
                } else { // user closed dialog or clicked cancel
                    inputTexte = null;
                    setVisible(false);
                }
                
            }
        });
    }
}
