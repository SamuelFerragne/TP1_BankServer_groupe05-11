package com.atoudeft.banque;

public class OperationRetrait extends Operation {

    public OperationRetrait(double montant){
        super(montant, "RETRAIT");
    }

    @Override
    public String toString(){
        return date + "\t" + typeOperation + "\t" + montant;
    }
}