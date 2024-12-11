package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurOperationsCompte implements ActionListener {
    private Client client;

    public EcouteurOperationsCompte(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        String command = e.getActionCommand();
        int confirmation;

        switch (command){
            case "HIST":
            case "EPARGNE":
                client.envoyer(command);
                break;
            case "DEPOT":
                PanneauDepot panneauDepot = new PanneauDepot();
                confirmation = JOptionPane.showConfirmDialog(null, panneauDepot, "Depot", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if(confirmation == JOptionPane.OK_OPTION) {
                    client.envoyer(command + " " + panneauDepot.getMontantDepot());
                }
                break;
            case "RETRAIT":
                PanneauRetrait panneauRetrait = new PanneauRetrait();
                confirmation = JOptionPane.showConfirmDialog(null, panneauRetrait, "Retrait", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if(confirmation == JOptionPane.OK_OPTION) {
                    client.envoyer(command + " " + panneauRetrait.getMontantRetrait());
                }
                break;
            case "TRANSFER":
                PanneauTransfert panneauTransfert = new PanneauTransfert();
                confirmation = JOptionPane.showConfirmDialog(null, panneauTransfert, "Transfert", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if(confirmation == JOptionPane.OK_OPTION) {
                    client.envoyer(command + " " + panneauTransfert.getMontantTransfert() + " " + panneauTransfert.getCompteDebiter() + " " + panneauTransfert.getCompteDestinataire());
                }
                break;
            case "FACTURE":
                PanneauFacture panneauFacture = new PanneauFacture();
                confirmation = JOptionPane.showConfirmDialog(null, panneauFacture, "Transfert", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if(confirmation == JOptionPane.OK_OPTION) {
                    client.envoyer(command + " " + panneauFacture.getMontantFacture() + " " + panneauFacture.getNumeroFacture() + " " + panneauFacture.getDescription());
                }
                break;


        }

    }
}
