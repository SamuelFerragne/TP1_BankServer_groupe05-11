package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauHistorique extends JPanel {
    private JTextArea txtHistorique;

    public PanneauHistorique() {
        txtHistorique = new JTextArea();

        txtHistorique.setEditable(false);
        txtHistorique.setBorder(BorderFactory.createTitledBorder("Historique"));

        JPanel pTout = new JPanel();
        this.setLayout(new BorderLayout());
        pTout.add(txtHistorique);

        this.add(pTout, BorderLayout.NORTH);
        this.setBorder(BorderFactory.createLineBorder(new Color(0x00000000,true),200));
    }
    public PanneauHistorique(String historique) {
        txtHistorique = new JTextArea();

        txtHistorique.setEditable(false);
        txtHistorique.setBorder(BorderFactory.createTitledBorder("Historique"));
        txtHistorique.setText(historique);

        JPanel pTout = new JPanel();
        this.setLayout(new BorderLayout());
        pTout.add(txtHistorique);

        this.add(pTout, BorderLayout.NORTH);
        this.setBorder(BorderFactory.createLineBorder(new Color(0x00000000,true),200));
    }

}
