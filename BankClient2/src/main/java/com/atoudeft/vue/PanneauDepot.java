package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanneauDepot extends JPanel {
    private JTextField txtMontantDepot;

    public PanneauDepot() {

        txtMontantDepot = new JTextField(30);

        txtMontantDepot.setBorder(BorderFactory.createTitledBorder("Montant du depot :"));

        JPanel pTout = new JPanel();
        this.setLayout(new BorderLayout());
        pTout.add(txtMontantDepot);

        this.add(pTout, BorderLayout.NORTH);
        this.setBorder(BorderFactory.createLineBorder(new Color(0x00000000,true),200));

    }

    public String getMontantDepot() {
        return this.txtMontantDepot.getText();
    }

    public void effacer() {
        this.txtMontantDepot.setText("");
    }
}
