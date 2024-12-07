package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class PanneauConfigServeur extends JPanel {
    private JTextField txtAdrServeur, txtNumPort;

    public PanneauConfigServeur(String adr, int port) {
        txtAdrServeur = new JTextField(30);
        txtNumPort = new JTextField(30);

        txtAdrServeur.setText(adr);
        txtNumPort.setText(String.valueOf(port));

        JLabel lAdrServeur = new JLabel("Adresse du serveur : ");
        JLabel lNumPort = new JLabel("Numéro de port : ");

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel pFenetre = new JPanel(new GridLayout(2,1));

        p1.add(lAdrServeur);
        p1.add(txtAdrServeur);
        p2.add(lNumPort);
        p2.add(txtNumPort);

        pFenetre.add(p1);
        pFenetre.add(p2);

        this.setLayout(new BorderLayout());
        this.add(pFenetre, BorderLayout.NORTH);
    }
    public String getAdresseServeur() {
        return txtAdrServeur.getText();
    }
    public String getPortServeur() {
        return txtNumPort.getText();
    }
}
