package com.atoudeft.banque;

public class OperationTransfer extends Operation {
    public String numCompteDestinataire;

    public OperationTransfer(double montant, String numCompte){
        super(montant,"TRANSFER");
        this. numCompteDestinataire = numCompte;
    }

    @Override
    public String toString(){
        return date + "\t" + typeOperation + "\t" + montant + "\t" + numCompteDestinataire;
    }

}