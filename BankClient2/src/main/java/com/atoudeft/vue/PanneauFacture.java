package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;

public class PanneauFacture extends JPanel {
    private JTextField txtMontantPayer,txtNumeroFacture, txtDescription;

    public PanneauFacture() {

        txtMontantPayer = new JTextField(30);
        txtNumeroFacture = new JTextField(30);
        txtDescription = new JTextField(30);

        txtMontantPayer.setBorder(BorderFactory.createTitledBorder("Montant a payer :"));
        txtNumeroFacture.setBorder(BorderFactory.createTitledBorder("Numero de facture :"));
        txtDescription.setBorder(BorderFactory.createTitledBorder("Description :"));

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();
        JPanel pTout = new JPanel(new GridLayout(3,1));

        p1.add(txtMontantPayer);
        p2.add(txtNumeroFacture);
        p3.add(txtDescription);

        this.setLayout(new BorderLayout());
        pTout.add(p1);
        pTout.add(p2);
        pTout.add(p3);
        this.add(pTout, BorderLayout.NORTH);
        this.setBorder(BorderFactory.createLineBorder(new Color(0x00000000,true),200));
    }
    public String getMontantFacture() {
        return this.txtMontantPayer.getText();
    }
    public String getNumeroFacture() {
        return this.txtNumeroFacture.getText();
    }
    public String getDescription() {
        return this.txtDescription.getText();
    }

}
