package com.atoudeft.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PanneauRetrait extends JPanel {
    private JTextField txtMontantRetrait;

    public PanneauRetrait() {

        txtMontantRetrait = new JTextField(30);

        txtMontantRetrait.setBorder(BorderFactory.createTitledBorder("Montant du retrait :"));


        JPanel pTout = new JPanel();
        this.setLayout(new BorderLayout());
        pTout.add(txtMontantRetrait);
        this.add(pTout, BorderLayout.NORTH);
        this.setBorder(BorderFactory.createLineBorder(new Color(0x00000000,true),200));
    }
    public String getMontantRetrait() {
        return this.txtMontantRetrait.getText();
    }

    public void effacer() {
        this.txtMontantRetrait.setText("");
    }
}
