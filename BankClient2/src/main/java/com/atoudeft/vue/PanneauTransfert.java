package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanneauTransfert extends JPanel {
    private JTextField txtMontantTransfert, txtCompteDestinataire, txtCompteDebiter;

    public PanneauTransfert() {

        txtMontantTransfert = new JTextField(30);
        txtCompteDestinataire = new JTextField(30);
        txtCompteDebiter = new JTextField(30);

        txtMontantTransfert.setBorder(BorderFactory.createTitledBorder("Montant du transfert :"));
        txtCompteDestinataire.setBorder(BorderFactory.createTitledBorder("Compte destinataire :"));
        txtCompteDebiter.setBorder(BorderFactory.createTitledBorder("Compte debiter :"));

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel pTout = new JPanel(new GridLayout(2,1));

        p1.add(txtMontantTransfert);
        p2.add(txtCompteDebiter);
        p2.add(txtCompteDestinataire);

        this.setLayout(new BorderLayout());
        pTout.add(p1);
        pTout.add(p2);
        this.add(pTout, BorderLayout.NORTH);
        this.setBorder(BorderFactory.createLineBorder(new Color(0x00000000,true),200));
    }
    public String getMontantTransfert() {
        return this.txtMontantTransfert.getText();
    }
    public String getCompteDestinataire() {
        return this.txtCompteDestinataire.getText();
    }
    public String getCompteDebiter() {
        return this.txtCompteDebiter.getText();
    }

}
