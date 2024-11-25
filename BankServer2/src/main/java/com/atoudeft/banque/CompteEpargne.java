package com.atoudeft.banque;

//Question 4.1 :
public class CompteEpargne extends CompteBancaire {
    public final double FRAIS_OPERATION = 2;
    public final double LIMITE = 1000;
    private double tauxInterets;
    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     */
    public CompteEpargne(String numero, double tauxInterets) {
        super(numero, TypeCompte.EPARGNE);
        this.tauxInterets = tauxInterets;
    }
    public void ajouterInterets() {
       solde += solde*tauxInterets;
    }
    @Override
    public boolean crediter(double montant) {
        if (montant>0) {
            solde += montant;
            return true;
        }
        return false;
    }
    @Override
    public boolean debiter(double montant) {
        if (montant<=0 || solde<montant) {
            return false;
        }
        if (solde<LIMITE) {
            if (solde+FRAIS_OPERATION<montant)
                return false;
            solde -= FRAIS_OPERATION;
        }
        solde -= montant;
        historique.empiler(new OperationRetrait(montant));
        return true;
    }
    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        if (montant<=0 || solde<montant) {
            return false;
        }
        if (solde<LIMITE) {
            if (solde+FRAIS_OPERATION<montant)
                return false;
            solde -= FRAIS_OPERATION;
        }
        solde -= montant;
        historique.empiler(new OperationFacture(montant,numeroFacture,description));
        return true;
    }
    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        if (montant>0) { //prélèvement de ce compte
            if (solde<montant) {
                return false;
            }
            if (solde<LIMITE) {
                if (solde+FRAIS_OPERATION<montant)
                    return false;
                solde -= FRAIS_OPERATION;
            }
            solde -= montant;
            historique.empiler(new OperationTransfer(montant,numeroCompteDestinataire));
            return true;
        }
        if (montant<0) { //dépot dans ce compte
            solde += -montant;
            historique.empiler(new OperationTransfer(montant,numeroCompteDestinataire));
            return true;
        }
        return false;
    }
}
