package com.atoudeft.controleur;

import com.atoudeft.client.Client;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class EcouteurListeComptes extends MouseAdapter {

    private Client client;
    public EcouteurListeComptes(Client client) {
        this.client = client;
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        if(evt.getClickCount() == 2){
            JList source = (JList) evt.getSource();
            String compteSelect = (String) source.getSelectedValue();

            if(compteSelect != null){
                String typeCompte = compteSelect.substring(7,compteSelect.length()-1);
                //System.out.println(typeCompte);
                client.envoyer("SELECT "+typeCompte.toLowerCase());
            }

        }
    }
}
