package com.atoudeft.serveur;

import com.atoudeft.banque.Banque;
import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.CompteClient;
import com.atoudeft.banque.CompteEpargne;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

/**
 * Cette classe représente un gestionnaire d'événement d'un serveur. Lorsqu'un serveur reçoit un texte d'un client,
 * il crée un événement à partir du texte reçu et alerte ce gestionnaire qui réagit en gérant l'événement.
 *
 * @author Abdelmoumène Toudeft (Abdelmoumene.Toudeft@etsmtl.ca)
 * @version 1.0
 * @since 2023-09-01
 */
public class GestionnaireEvenementServeur implements GestionnaireEvenement {
    private Serveur serveur;

    /**
     * Construit un gestionnaire d'événements pour un serveur.
     *
     * @param serveur Serveur Le serveur pour lequel ce gestionnaire gère des événements
     */
    public GestionnaireEvenementServeur(Serveur serveur) {
        this.serveur = serveur;
    }

    /**
     * Méthode de gestion d'événements. Cette méthode contiendra le code qui gère les réponses obtenues d'un client.
     *
     * @param evenement L'événement à gérer.
     */
    @Override
    public void traiter(Evenement evenement) {
        Object source = evenement.getSource();
        ServeurBanque serveurBanque = (ServeurBanque)serveur;
        Banque banque = serveurBanque.getBanque();
        ConnexionBanque cnx;
        String msg, typeEvenement, argument, numCompteClient, nip;
        String[] t;

        if (source instanceof Connexion) {
            String cnx = (ConnexionBanque) source;
            System.out.println("SERVEUR: Recu : " + evenement.getType() + " " + evenement.getArgument());
            String typeEvenement = evenement.getType();
            cnx.setTempsDerniereOperation(System.currentTimeMillis());
            switch (typeEvenement) {
                /******************* COMMANDES GÉNÉRALES *******************/
                case "EXIT": //Ferme la connexion avec le client qui a envoyé "EXIT":
                    cnx.envoyer("END");
                    serveurBanque.enlever(cnx);
                    cnx.close();
                    break;
                case "LIST": //Envoie la liste des numéros de comptes-clients connectés :
                    cnx.envoyer("LIST " + serveurBanque.list());
                    break;

                case "CONNECT":
                    String argument = evenement.getArgument();
                    String[] t = argument.split(":");
                    if(t.length<2){
                        cnx.envoyer("CONNECT NO");
                    }else{
                        String numeroCompteClient = t[0];
                        String nip = t[1];

                        String connectesString = serveurBanque.list(); //récupère la liste des connexions
                        String[] connectes = connectesString.split(":");

                        for(ConnexionBanque connexion : connectes){
                            if(connexion.getNumeroCompteClient().equals(numeroCompteClient)){
                                cnx.envoyer("CONNECT NO");
                                break;
                            }
                        }

                        CompteClient compteClient = banque.getCompteClient(numeroCompteClient);
                        if(compteClient != null && compteClient.getNip().equals(nip)){

                            cnx.setNumeroCompteClient(numeroCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numeroCompteClient));
                            cnx.envoyer("CONNCET OK");
                        }else{
                            cnx.envoyer("CONNECT NO");
                            break;
                        }

                    }

                    break;
                    /******************* COMMANDES DE GESTION DE COMPTES *******************/
                case "NOUVEAU": //Crée un nouveau compte-client :
                    if (cnx.getNumeroCompteClient()!=null) {
                        cnx.envoyer("NOUVEAU NO deja connecte");
                        break;
                    }
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("NOUVEAU NO");
                    }
                    else {
                        numCompteClient = t[0];
                        nip = t[1];
                        banque = serveurBanque.getBanque();
                        if (banque.ajouter(numCompteClient,nip)) {
                            cnx.setNumeroCompteClient(numCompteClient);
                            cnx.setNumeroCompteActuel(banque.getNumeroCompteParDefaut(numCompteClient));
                            cnx.envoyer("NOUVEAU OK " + t[0] + " cree");
                        }
                        else
                            cnx.envoyer("NOUVEAU NO "+t[0]+" existe");
                    }
                    break;

                case "EPARGNE" :
                    if (cnx.getNumeroCompteClient()==null) {
                        cnx.envoyer("EPARGNE NO pas connecter");
                        break;
                    }

                    banque = serveurBanque.getBanque();
                    if(banque.getNumeroCompteEpargne(cnx.getNumeroCompteClient()) != null){
                        cnx.envoyer("EPARGNE NO compte epargne deja existant");
                        break;
                    }

                    String numeroTemp = CompteBancaire.genereNouveauNumero();
                    while(banque.getCompteClient(numeroTemp) != null) {
                        numeroTemp = CompteBancaire.genereNouveauNumero();
                    }

                    CompteEpargne compteEpargne = new CompteEpargne(numeroTemp, 5);
                    banque.getCompteClient(cnx.getNumeroCompteClient()).ajouter(compteEpargne);
                    break;

                case "DEPOT" :
                    argument = evenement.getArgument();
                    double depot = 0;

                    try {
                        depot = Double.parseDouble(argument);
                    }catch (Exception ex){
                        cnx.envoyer("DEPOT NO");
                        break;
                    }

                    if(depot <= 0) {
                        cnx.envoyer("DEPOT NO");
                        break;
                    }else{
                        banque.deposer(depot, cnx.getNumeroCompteActuel());
                        cnx.envoyer("DEPOT OK");
                    }
                    break;

                case "RETRAIT" :
                    argument = evenement.getArgument();
                    double retrait = 0;

                    try {
                        retrait = Double.parseDouble(argument);
                    }catch (Exception ex){
                        cnx.envoyer("RETRAIT NO");
                        break;
                    }

                    if(retrait <= 0) {
                        cnx.envoyer("RETRAIT NO");
                        break;
                    }else{
                        banque.retirer(retrait, cnx.getNumeroCompteActuel());
                        cnx.envoyer("RETRAIT OK");
                    }
                    break;

                case "FACTURE" :
                    argument = evenement.getArgument();
                    double montantFacture = 0;

                    t = argument.split(" ");

                    if (t.length != 3) {
                        cnx.envoyer("FACTURE NO");
                    }else {
                        String numeroFacture = t[1];
                        String description = t[2];
                        try {
                            montantFacture = Double.parseDouble(t[0]);
                        }catch (Exception ex){
                            cnx.envoyer("FACTURE NO");
                            break;
                        }

                        if(montantFacture <= 0) {
                            cnx.envoyer("FACTURE NO");
                            break;
                        }else{
                            banque.payerFacture(montantFacture, cnx.getNumeroCompteActuel(), numeroFacture, description);
                            cnx.envoyer("FACTURE OK");
                        }

                    }
                    break;

                case "TRANSFER" :
                    argument = evenement.getArgument();
                    double transfer = 0;

                    t = argument.split(" ");
                    if (t.length != 2) {
                        cnx.envoyer("TRANSFER NO");
                    }else {
                        String compteTransfer = t[1];
                        try {
                            transfer = Double.parseDouble(t[0]);
                        }catch (Exception ex){
                            cnx.envoyer("TRANSFER NO");
                            break;
                        }

                        if(transfer <= 0) {
                            cnx.envoyer("TRANSFER NO");
                            break;
                        }else{
                            banque.transferer(transfer, cnx.getNumeroCompteActuel(), compteTransfer);
                            cnx.envoyer("TRANSFER OK");
                        }

                    }
                    break;
                case "SELECT":
                    String numCompte = null;
                    if(cnx.getNumeroCompteClient() == null){
                        cnx.envoyer("SELECT NO");
                        break;
                    }
                    argument = evenement.getArgument();
                    if(argument.toLowerCase().equals("cheque")){
                        numCompte = banque.getNumeroCompteParDefaut(cnx.getNumeroCompteClient());
                    }else if(argument.toLowerCase().equals("epargne")){
                        numCompte = banque.getNumeroCompteEpargne(cnx.getNumeroCompteClient());
                    }else{
                        cnx.envoyer("SELECT NO");
                        break;
                    }

                    if(numCompte != null){
                        cnx.setNumeroCompteActuel(numCompte);
                        cnx.envoyer("SELECT OK");
                    }else {
                        cnx.envoyer("SELECT NO");
                    }

                    break;


                /******************* TRAITEMENT PAR DÉFAUT *******************/
                default: //Renvoyer le texte recu convertit en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
        }
    }
}