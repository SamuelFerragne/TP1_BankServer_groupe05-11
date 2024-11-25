package com.atoudeft.serveur;

import com.atoudeft.banque.Banque;
import com.atoudeft.banque.CompteBancaire;
import com.atoudeft.banque.CompteClient;
import com.atoudeft.banque.TypeCompte;
import com.atoudeft.banque.serveur.ConnexionBanque;
import com.atoudeft.banque.serveur.ServeurBanque;
import com.atoudeft.commun.evenement.Evenement;
import com.atoudeft.commun.evenement.GestionnaireEvenement;
import com.atoudeft.commun.net.Connexion;

import java.util.List;
import java.util.ListIterator;

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
        Banque banque;
        ConnexionBanque cnx;
        String msg, typeEvenement, argument, numCompteClient, nip, numCompte, str;
        String[] t;
        CompteClient compteClient;
        CompteBancaire compteBancaire;
        boolean dejaConnecte;
        List<CompteBancaire> comptes;
        double  montant, solde;

        if (source instanceof Connexion) {
            cnx = (ConnexionBanque) source;
            System.out.println("SERVEUR: Recu : " + evenement.getType() + " " + evenement.getArgument());
            typeEvenement = evenement.getType();
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
                            numCompte = banque.getNumeroCompteParDefaut(numCompteClient);
                            cnx.setNumeroCompteActuel(numCompte);
                            compteBancaire = banque.getCompteBancaireParNumero(numCompte);
                            cnx.envoyer("NOUVEAU OK " + compteBancaire.getNumero()+"[CHEQUE] "+compteBancaire.getSolde());
                        }
                        else
                            cnx.envoyer("NOUVEAU NO ");
                    }
                    break;
                case "CONNECT": //Question 3.1 : Connexion au compte-client
                    //On empeche le client de se connecter à plusieurs comptes-client en
                    //même temps (NON demandé dans le TP) :
                    if (cnx.getNumeroCompteClient()!=null) {
                        cnx.envoyer("NOUVEAU NO");
                        break;
                    }
                    //On récupère le numéro et le nip :
                    argument = evenement.getArgument();
                    t = argument.split(":");
                    if (t.length<2) {
                        cnx.envoyer("CONNECT NO");
                    }
                    else {
                        numCompteClient = t[0];
                        nip = t[1];
                        //On vérifie si le client n'est pas déjà connecté à ce compte :
                        t = serveurBanque.list().split(":");
                        dejaConnecte = false;
                        for (String s : t) {
                            if (s.trim().equals(numCompteClient)) {
                                dejaConnecte = true;
                            }
                        }
                        if (dejaConnecte) {
                            cnx.envoyer("CONNECT NO");
                            break;
                        }
                        //On vérifie si le compte-client existe :
                        banque = serveurBanque.getBanque();
                        compteClient = banque.getCompteClient(numCompteClient);
                        if (compteClient==null) {
                            cnx.envoyer("CONNECT NO");
                            break;
                        }
                        //On vérifie si le nip est correct (définir la méthode getNip() dans CompteClient) :
                        if (!nip.equals(compteClient.getNip())) {
                            cnx.envoyer("CONNECT NO");
                            break;
                        }
                        //Tout est OK. On enregiste le numéro de compte-client et son numéro de compte-chèque
                        // dans la connexion:
                        cnx.setNumeroCompteClient(numCompteClient);
                        comptes = compteClient.getComptes();
                        for (CompteBancaire cpt:comptes) {
                            if (cpt.getType()== TypeCompte.CHEQUE) {
                                numCompte = cpt.getNumero();
                                cnx.setNumeroCompteActuel(numCompte);
                                str = "";
                                cpt = banque.getCompteClient(numCompteClient).getCompteCheque();
                                if (cpt!=null)
                                    str += cpt.getNumero()+"[CHEQUE] "+cpt.getSolde();
                                cpt = banque.getCompteClient(numCompteClient).getCompteEpargne();
                                if (cpt!=null)
                                    str += ":"+cpt.getNumero()+"[EPARGNE] "+cpt.getSolde();
                                cnx.envoyer("CONNECT OK "+str);
                                break;
                            }
                        }
                    }
                    break;
                case "EPARGNE" : //Question 4.2 :
                    //On vérifie si le client n'est pas encore connecté à son compte-client :
                    numCompteClient = cnx.getNumeroCompteClient();
                    if (numCompteClient==null) {
                        cnx.envoyer("EPARGNE NO");
                        break;
                    }
                    //
                    banque = serveurBanque.getBanque();
                    if (banque.ajouterCompteEpargne(numCompteClient)) {
                        cnx.envoyer("EPARGNE OK "+banque.getCompteClient(numCompteClient).getCompteEpargne().getNumero());
                    }
                    else {
                        cnx.envoyer("EPARGNE NO");
                    }
                    break;
                case "SELECT" : //Question 5.1 :
                    //On vérifie si le client n'est pas encore connecté à son compte-client :
                    numCompteClient = cnx.getNumeroCompteClient();
                    if (numCompteClient==null) {
                        cnx.envoyer("SELECT NO");
                        break;
                    }
                    argument = evenement.getArgument(); //type de compte à sélectionner
                    numCompte = serveurBanque.getBanque().getNumeroCompteBancaire(numCompteClient,argument);
                    if (numCompte==null) {
                        cnx.envoyer("SELECT NO");
                    }
                    else  {
                        compteBancaire = serveurBanque.getBanque().getCompteBancaireParNumero(numCompte);
                        if (compteBancaire!=null)
                            solde = compteBancaire.getSolde();
                        else
                            solde = 0;
                        cnx.setNumeroCompteActuel(numCompte);
                        cnx.envoyer("SELECT OK "+numCompte+" "+solde);
                    }
                    break;
                case "DEPOT": //Question 6.1 :
                    //On vérifie si le client n'est pas encore connecté à son compte-client :
                    numCompteClient = cnx.getNumeroCompteClient();
                    if (numCompteClient==null) {
                        cnx.envoyer("DEPOT NO");
                        break;
                    }
                    argument = evenement.getArgument(); //montant du dépot en String
                    try {
                        montant = Double.parseDouble(argument);
                        if (serveurBanque.getBanque().deposer(montant,cnx.getNumeroCompteActuel())) {
                            solde = serveurBanque.getBanque().getCompteBancaireParNumero(cnx.getNumeroCompteActuel()).getSolde();
                            cnx.envoyer("DEPOT OK "+solde);
                        }
                        else {
                            cnx.envoyer("DEPOT NO");
                        }
                    }
                    catch (NumberFormatException exp) {
                        cnx.envoyer("DEPOT NO");
                    }
                    break;
                case "RETRAIT": //Question 6.2 :
                    //On vérifie si le client n'est pas encore connecté à son compte-client :
                    numCompteClient = cnx.getNumeroCompteClient();
                    if (numCompteClient==null) {
                        cnx.envoyer("RETRAIT NO");
                        break;
                    }
                    argument = evenement.getArgument(); //montant du dépot en String
                    try {
                        montant = Double.parseDouble(argument);
                        if (serveurBanque.getBanque().retirer(montant,cnx.getNumeroCompteActuel())) {
                            solde = serveurBanque.getBanque().getCompteBancaireParNumero(cnx.getNumeroCompteActuel()).getSolde();
                            cnx.envoyer("RETRAIT OK "+solde);
                        }
                        else {
                            cnx.envoyer("RETRAIT NO");
                        }
                    }
                    catch (NumberFormatException exp) {
                        cnx.envoyer("RETRAIT NO");
                    }
                    break;
                case "FACTURE": //Question 6.3 :
                    //On vérifie si le client n'est pas encore connecté à son compte-client :
                    numCompteClient = cnx.getNumeroCompteClient();
                    if (numCompteClient==null) {
                        cnx.envoyer("FACTURE NO");
                        break;
                    }
                    argument = evenement.getArgument();
                    t = argument.split(" ");
                    if (t.length<3) { //Pas assez d'arguments dans la commande
                        cnx.envoyer("FACTURE NO");
                        break;
                    }
                    try {
                        montant = Double.parseDouble(t[0]);
                        if (serveurBanque.getBanque().payerFacture(montant,cnx.getNumeroCompteActuel(),t[1],
                                argument.substring(argument.indexOf(t[1])+t[1].length()).trim())) {
                            solde = serveurBanque.getBanque().getCompteBancaireParNumero(cnx.getNumeroCompteActuel()).getSolde();
                            cnx.envoyer("FACTURE OK "+solde);
                        }
                        else {
                            cnx.envoyer("FACTURE NO");
                        }
                    }
                    catch (NumberFormatException exp) {
                        cnx.envoyer("FACTURE NO");
                    }
                    break;
                case "TRANSFER": //Question 6.4 :
                    //On vérifie si le client n'est pas encore connecté à son compte-client :
                    numCompteClient = cnx.getNumeroCompteClient();
                    if (numCompteClient==null) {
                        cnx.envoyer("TRANSFER NO");
                        break;
                    }
                    argument = evenement.getArgument();
                    t = argument.split(" ");
                    if (t.length<2) { //Pas assez d'arguments dans la commande
                        cnx.envoyer("TRANSFER NO");
                        break;
                    }
                    try {
                        montant = Double.parseDouble(t[0]);
                        if (serveurBanque.getBanque().transferer(montant,cnx.getNumeroCompteActuel(),t[1])) {
                            solde = serveurBanque.getBanque().getCompteBancaireParNumero(cnx.getNumeroCompteActuel()).getSolde();
                            cnx.envoyer("TRANSFER OK "+solde);
                        }
                        else {
                            cnx.envoyer("TRANSFER NO");
                        }
                    }
                    catch (NumberFormatException exp) {
                        cnx.envoyer("TRANSFER NO");
                    }
                    break;
                case "HIST":
                    compteBancaire = serveurBanque.getBanque().getCompteBancaireParNumero(cnx.getNumeroCompteActuel());
                    str = compteBancaire.getHistorique();
                    cnx.envoyer("HIST "+str);
                    break;
                /******************* TRAITEMENT PAR DÉFAUT *******************/
                default: //Renvoyer le texte recu converti en majuscules :
                    msg = (evenement.getType() + " " + evenement.getArgument()).toUpperCase();
                    cnx.envoyer(msg);
            }
            serveurBanque.getBanque().print();
        }
    }
}