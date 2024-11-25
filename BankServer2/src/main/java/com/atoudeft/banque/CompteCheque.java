package com.atoudeft.banque;

//Question 2.1 :
public class CompteCheque extends CompteBancaire {

    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     */
    public CompteCheque(String numero) {
        super(numero, TypeCompte.CHEQUE);
    }

    @Override
    public boolean crediter(double montant) {
        if (montant>0) {
            solde += montant;
            historique.empiler(new OperationDepot(montant));
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant) {
        if (montant>0 && solde>=montant) {
            solde -= montant;
            historique.empiler(new OperationRetrait(montant));
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        if (montant>0 && solde>=montant) {
            solde -= montant;
            historique.empiler(new OperationFacture(montant,numeroFacture,description));
            return true;
        }
        return false;
    }
    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        if (montant>0) { //prélèvement de ce compte
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