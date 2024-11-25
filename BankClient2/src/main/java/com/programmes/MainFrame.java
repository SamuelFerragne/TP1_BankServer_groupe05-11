package com.programmes;

import com.atoudeft.client.Client;
import com.atoudeft.client.GestionnaireEvenementClient2;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.controleur.EcouteurMenuPrincipal;
import com.atoudeft.observer.Observable;
import com.atoudeft.observer.Observateur;
import com.atoudeft.vue.PanneauPrincipal;


import javax.swing.*;
import java.awt.*;
import java.util.Vector;

/**
 *
 * Cette classe représente la fenêtre principale de l'application cliente.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-11-01
 */
public class MainFrame extends JFrame implements Runnable, Observateur {

    public static final String TITRE = "BankEts - Client";
    private Client client;
    private PanneauPrincipal panneauPrincipal;
    private JMenu mDemarrer;
    private JMenuItem miConnecter, miDeconnecter, miQuitter, miConfigurer;
    private EcouteurMenuPrincipal ecouteurMenuPrincipal;
    private GestionnaireEvenement gestionnaireEvenement;

    public void run() {
        initialiserComposants();
        configurerFenetrePrincipale();
        setVisible(true);
    }

    private void configurerFenetrePrincipale() {
        //Configuration de la fenêtre
        this.setSize(1000,600);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void initialiserComposants() {
        //Création et initialisation des composants :
        client = new Client();
        client.ajouterObservateur(this);
        panneauPrincipal = new PanneauPrincipal(client);
        ecouteurMenuPrincipal = new EcouteurMenuPrincipal(client, this);
        gestionnaireEvenement = new GestionnaireEvenementClient2(client,panneauPrincipal);

        //Menus :
        mDemarrer = new JMenu("Démarrer");
        miConnecter = new JMenuItem("Se connecter");
        miDeconnecter = new JMenuItem("Se déconnecter");
        miQuitter = new JMenuItem("Quitter");
        miConfigurer = new JMenuItem("Configurer serveur");

        miConnecter.setActionCommand("CONNECTER");
        miDeconnecter.setActionCommand("DECONNECTER");
        miQuitter.setActionCommand("QUITTER");
        miConfigurer.setActionCommand("CONFIGURER");

        miConnecter.addActionListener(ecouteurMenuPrincipal);
        miDeconnecter.addActionListener(ecouteurMenuPrincipal);
        miQuitter.addActionListener(ecouteurMenuPrincipal);
        miConfigurer.addActionListener(ecouteurMenuPrincipal);

        mDemarrer.add(miConnecter);
        mDemarrer.add(miDeconnecter);
        mDemarrer.addSeparator();
        mDemarrer.add(miConfigurer);
        mDemarrer.addSeparator();
        mDemarrer.add(miQuitter);

        miDeconnecter.setEnabled(false);

        JMenuBar mb = new JMenuBar();
        mb.add(mDemarrer);

        this.setContentPane(panneauPrincipal);
        panneauPrincipal.setVisible(false);
        this.setJMenuBar(mb);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater( new MainFrame() );
    }

    @Override
    public void seMettreAJour(Observable observable) {
        if (observable instanceof Client) {
            Client client = (Client)observable;
            if (!client.isConnecte()) {
                this.setTitle(TITRE);
                this.panneauPrincipal.setVisible(false);
                panneauPrincipal.cacherPanneauCompteClient();
                panneauPrincipal.montrerPanneauConnexion();
            }
        }
    }
}