package com.atoudeft.banque;

import java.lang.reflect.Type;

public class CompteCheque extends CompteBancaire{
    private String numero;
    private TypeCompte type;
    private double solde;
    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     */
    public CompteCheque(String numero) {
        super(numero, TypeCompte.CHEQUE);
        this.solde = 0;
    }

    @Override
    public boolean crediter(double montant) {
        if(montant > 0) {
            solde += montant;
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean debiter(double montant) {
        if(montant > 0) {
            solde -= montant;
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }
}
