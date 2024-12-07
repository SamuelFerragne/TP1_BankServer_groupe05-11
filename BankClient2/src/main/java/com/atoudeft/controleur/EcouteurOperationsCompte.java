package com.atoudeft.controleur;

import com.atoudeft.client.Client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurOperationsCompte implements ActionListener {
    private Client client;

    public EcouteurOperationsCompte(Client client) {
        this.client = client;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command){
            case "EPARGNE":
                client.envoyer(command);
                break;
        }

    }
}
