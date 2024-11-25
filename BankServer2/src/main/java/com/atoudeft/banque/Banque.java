package com.atoudeft.banque;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class Banque implements Serializable {
    private String nom;
    private List<CompteClient> comptes;

    public Banque(String nom) {
        this.nom = nom;
        this.comptes = new ArrayList<>();
    }

    @Override
    public String toString() {
        String str = "-----Banque-----\n";
        for (CompteClient c:comptes)
            str += "Compte-client : "+c + "\n";
        return str + "\n----------------";
    }
    public void print() {
        System.out.println(this);
    }
    /**
     * Recherche un compte-client à partir de son numéro.
     *
     * @param numeroCompteClient le numéro du compte-client
     * @return le compte-client s'il a été trouvé. Sinon, retourne null
     */
    public CompteClient getCompteClient(String numeroCompteClient) {
        CompteClient cpt = new CompteClient(numeroCompteClient,"");
        int index = this.comptes.indexOf(cpt);
        if (index != -1)
            return this.comptes.get(index);
        else
            return null;
    }

    /**
     * Vérifier qu'un compte-bancaire appartient bien au compte-client.
     *
     * @param numeroCompteBancaire numéro du compte-bancaire
     * @param numeroCompteClient    numéro du compte-client
     * @return  true si le compte-bancaire appartient au compte-client
     */
    public boolean appartientA(String numeroCompteBancaire, String numeroCompteClient) {
        throw new NotImplementedException();
    }

    /**
     * Effectue un dépot d'argent dans un compte-bancaire
     *
     * @param montant montant à déposer
     * @param numeroCompte numéro du compte
     * @return true si le dépot s'est effectué correctement
     */
    public boolean deposer(double montant, String numeroCompte) {
        CompteBancaire cpt = getCompteBancaireParNumero(numeroCompte);
        if (cpt!=null)
            return cpt.crediter(montant);
        return false;
    }

    /**
     * Effectue un retrait d'argent d'un compte-bancaire
     *
     * @param montant montant retiré
     * @param numeroCompte numéro du compte
     * @return true si le retrait s'est effectué correctement
     */
    public boolean retirer(double montant, String numeroCompte) {
        CompteBancaire cpt = getCompteBancaireParNumero(numeroCompte);
        if (cpt!=null)
            return cpt.debiter(montant);
        return false;
    }

    /**
     * Effectue un transfert d'argent d'un compte à un autre de la même banque
     * @param montant montant à transférer
     * @param numeroCompteInitial   numéro du compte d'où sera prélevé l'argent
     * @param numeroCompteFinal numéro du compte où sera déposé l'argent
     * @return true si l'opération s'est déroulée correctement
     */
    public boolean transferer(double montant, String numeroCompteInitial, String numeroCompteFinal) {
        CompteBancaire cpt1, cpt2;
        if (numeroCompteInitial==numeroCompteFinal)
            return false;
        cpt1 = getCompteBancaireParNumero(numeroCompteInitial);
        if (cpt1==null)
            return false;
        cpt2 = getCompteBancaireParNumero(numeroCompteFinal);
        if (cpt2==null)
            return false;
        if (cpt1.transferer(montant,numeroCompteFinal))
            return cpt2.transferer(-montant,numeroCompteInitial);
        return false;
    }

    /**
     * Effectue un paiement de facture.
     * @param montant montant de la facture
     * @param numeroCompte numéro du compte bancaire d'où va se faire le paiement
     * @param numeroFacture numéro de la facture
     * @param description texte descriptif de la facture
     * @return true si le paiement s'est bien effectuée
     */
    public boolean payerFacture(double montant, String numeroCompte, String numeroFacture, String description) {
        CompteBancaire cpt = getCompteBancaireParNumero(numeroCompte);
        if (cpt!=null)
            return cpt.payerFacture(numeroFacture,montant,description);
        return false;
    }

    /**
     * Crée un nouveau compte-client avec un numéro et un nip et l'ajoute à la liste des comptes.
     *
     * @param numCompteClient numéro du compte-client à créer
     * @param nip nip du compte-client à créer
     * @return true si le compte a été créé correctement
     */
    public boolean ajouter(String numCompteClient, String nip) {
        /*À compléter et modifier :
            - Vérifier que le numéro a entre 6 et 8 caractères et ne contient que des lettres majuscules et des chiffres.
              Sinon, retourner false.
            - Vérifier que le nip a entre 4 et 5 caractères et ne contient que des chiffres. Sinon,
              retourner false.
            - Vérifier s'il y a déjà un compte-client avec le numéro, retourner false.
            - Sinon :
                . Créer un compte-client avec le numéro et le nip;
                . Générer (avec CompteBancaire.genereNouveauNumero()) un nouveau numéro de compte bancaire qui n'est
                  pas déjà utilisé;
                . Créer un compte-chèque avec ce numéro et l'ajouter au compte-client;
                . Ajouter le compte-client à la liste des comptes et retourner true.
         */
        //Question 2.2 :
        int taille = numCompteClient.length();
        char car;
        CompteClient compteClient;
        CompteBancaire compteBancaire;
        String numero;

        if (taille<6 || taille>8) {
            return false;
        }
        for (int i=0; i<taille; i++) {
            car = numCompteClient.charAt(i);
            if (!Util.estAlphabetiqueMajuscule(car) && !Util.estChiffre(car)) {
                return false;
            }
        }
        taille = nip.length();
        if (taille<4 || taille>5) {
            return false;
        }
        for (int i=0; i<taille; i++) {
            car = nip.charAt(i);
            if (!Util.estChiffre(car)) {
                return false;
            }
        }
        taille = this.comptes.size();
        for (int i=0;i<taille;i++) {
            if (numCompteClient.equals(comptes.get(i).getNumero())) {
                return false;
            }
        }
        compteClient = new CompteClient(numCompteClient,nip);
        numero = CompteBancaire.genereNouveauNumero();
        //TODO : Vérifier que le numéro n'est pas déjà utilisé

        compteBancaire = new CompteCheque(numero);
        compteClient.ajouter(compteBancaire);

        return this.comptes.add(compteClient);
    }

    /**
     * Retourne le numéro du compte-chèque d'un client à partir de son numéro de compte-client.
     *
     * @param numCompteClient numéro de compte-client
     * @return numéro du compte-chèque du client ayant le numéro de compte-client
     */
    public String getNumeroCompteParDefaut(String numCompteClient) {
        //Question 2.3 :
        CompteClient compteClient = this.getCompteClient(numCompteClient);
        CompteBancaire compteBancaire;
        ListIterator<CompteBancaire> listeComptes;
        if (compteClient!=null) {
            listeComptes = compteClient.getComptes().listIterator();
            while (listeComptes.hasNext()) {
                compteBancaire = listeComptes.next();
                if (compteBancaire.getType()==TypeCompte.CHEQUE) {
                    return compteBancaire.getNumero();
                }
            }
        }
        return null;
    }
    //Question 4.2 :
    public boolean possedeCompteEpargne(String numCompteClient) {
        ListIterator<CompteBancaire> comptes = getCompteClient(numCompteClient).getComptes().listIterator();
        while (comptes.hasNext())
            if (comptes.next().getType()==TypeCompte.EPARGNE)
                return true;
        return false;
    }

    public boolean numeroDejaUtilise(String numero) {
        int nbCptClient = this.comptes.size();
        int nbCptBancaire;
        List<CompteBancaire> cpts;
        for (int i=0;i<nbCptClient;i++) {
            cpts = comptes.get(i).getComptes();
            nbCptBancaire = cpts.size();
            for (int j=0;j<nbCptBancaire;j++) {
                if (cpts.get(j).getNumero().equals(numero))
                    return true;
            }
        }
        return false;
    }
    public String getNouveauNumeroDeCompteBancaire() {
        String num = CompteBancaire.genereNouveauNumero();
        while (numeroDejaUtilise(num))
            num = CompteBancaire.genereNouveauNumero();
        return num;
    }

    public boolean ajouterCompteEpargne(String numCompteClient) {
        CompteBancaire compte;
        if (possedeCompteEpargne(numCompteClient))
            return false;
        String numero = getNouveauNumeroDeCompteBancaire();
        compte = new CompteEpargne(numero,0.05);
        System.out.println("Ajout epargne");
        return getCompteClient(numCompteClient).ajouter(compte);
    }

    public String getNumeroCompteBancaire(String numCompteClient, String typeCompte) {
        String numCompte = null;
        CompteClient compteClient = this.getCompteClient(numCompteClient);
        CompteBancaire compteBancaire;
        if ("cheque".equals(typeCompte)) {
            compteBancaire = compteClient.getCompteCheque();
            if (compteBancaire!=null) {
                numCompte = compteBancaire.getNumero();
            }
        }
        else if ("epargne".equals(typeCompte)) {
            compteBancaire = compteClient.getCompteEpargne();
            if (compteBancaire!=null) {
                numCompte = compteBancaire.getNumero();
            }
        }
        return numCompte;
    }
    public CompteBancaire getCompteBancaireParNumero(String numero) {
        int nbCptClient = this.comptes.size();
        int nbCptBancaire;
        CompteBancaire cpt;
        List<CompteBancaire> cpts;
        for (int i=0;i<nbCptClient;i++) {
            cpts = comptes.get(i).getComptes();
            nbCptBancaire = cpts.size();
            for (int j=0;j<nbCptBancaire;j++) {
                cpt = cpts.get(j);
                if (cpt.getNumero().equals(numero))
                    return cpt;
            }
        }
        return null;
    }
}