package com.atoudeft.banque;

public class OperationRetrait extends Operation {
    private double montant;
    public OperationRetrait(double montant) {
        super(TypeOperation.RETRAIT);
        this.montant = montant;
    }
    @Override
    public String toString() {
        return super.toString() + "\t"+ montant;
    }
}