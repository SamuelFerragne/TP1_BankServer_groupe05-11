package com.atoudeft.banque;

public class OperationDepot extends Operation {

    public OperationDepot(double montant){
        super(montant, "DEPOT");
    }

    @Override
    public String toString(){
        return date + "\t" + typeOperation + "\t" + montant;
    }
}