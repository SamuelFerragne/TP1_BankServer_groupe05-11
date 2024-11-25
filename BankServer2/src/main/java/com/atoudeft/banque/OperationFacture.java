package com.atoudeft.banque;

public class OperationFacture extends Operation {
    private double montant;
    private String numero, description;

    public OperationFacture(double montant, String numero, String description) {
        super(TypeOperation.FACTURE);
        this.montant = montant;
        this.numero = numero;
        this.description = description;
    }
    @Override
    public String toString() {
        return super.toString() + "\t"+ montant + "$\t" + numero+ "$\t" + description;
    }
}