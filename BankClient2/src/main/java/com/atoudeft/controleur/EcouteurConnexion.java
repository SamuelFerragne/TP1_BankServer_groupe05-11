package com.atoudeft.controleur;

import com.atoudeft.client.Client;
import com.atoudeft.vue.PanneauConnexion;

import javax.swing.*;
import javax.xml.transform.Source;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EcouteurConnexion implements ActionListener {
    private Client client;
    private PanneauConnexion panneauConnexion;

    public EcouteurConnexion(Client client, PanneauConnexion panneauConnexion) {
        this.client = client;
        this.panneauConnexion = panneauConnexion;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
        String nomAction;
        if (source instanceof JButton) {
            nomAction = ((JButton)source).getActionCommand();
            switch (nomAction) {
                case "NOUVEAU":
                    client.envoyer("NOUVEAU "+panneauConnexion.getNumeroCompteClient()+":"+panneauConnexion.getNip());
                    break;
                case "CONNECT":
                    client.envoyer("CONNECT "+panneauConnexion.getNumeroCompteClient()+":"+panneauConnexion.getNip());
                    break;
            }
            panneauConnexion.effacer();
        }
    }
}