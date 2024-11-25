package com.atoudeft.banque;

public class OperationTransfer extends Operation{
    private double montant;
    private String numeroDestinataire;

    public OperationTransfer(double montant, String numeroDestinataire) {
        super(TypeOperation.TRANSFER);
        this.montant = montant;
        this.numeroDestinataire = numeroDestinataire;
    }

    @Override
    public String toString() {
        String s = (montant>0)?"Envoye a":"Recu de";
        return super.toString() + "\t"+ Math.abs(montant) + "$\t"+s+" " + numeroDestinataire;
    }
}
