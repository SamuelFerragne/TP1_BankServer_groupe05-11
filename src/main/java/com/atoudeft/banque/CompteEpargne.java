package com.atoudeft.banque;

public class CompteEpargne extends CompteBancaire{
    static final double frais = 2; //Frais de 2$
    static final double limiteMinimum = 1000; //Solde minimum sans frais

    private double interets; // Interets en pourcentage

    /**
     * Crée un compte bancaire.
     *
     * @param numero numéro du compte
     * @param interets taux d'interets en pourcentage du compte epargne
     */
    public CompteEpargne(String numero, double interets) {
        super(numero, TypeCompte.EPARGNE);
        this.interets = interets;
    }

    @Override
    public boolean crediter(double montant) {
        if(montant > 0) {
            solde += montant;
            return true;
        }
        return false;
    }

    @Override
    public boolean debiter(double montant) {
        if(montant > 0 && solde >= montant) {
            solde -= montant;
            if(solde < CompteEpargne.limiteMinimum) {
                solde -= CompteEpargne.frais;
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean payerFacture(String numeroFacture, double montant, String description) {
        return false;
    }

    @Override
    public boolean transferer(double montant, String numeroCompteDestinataire) {
        return false;
    }

    public boolean ajouterInterets() {
        solde += (solde * (interets / 100));
        return true;
    }
}
