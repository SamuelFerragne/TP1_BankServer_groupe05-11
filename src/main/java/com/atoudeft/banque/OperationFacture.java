public class OperationFacture extends Operation {
    public String numFacture;
    public String descritpion;

    public OperationFacture(double montant, String numFacture, String descritpion){
        super(montant, "FACTURE");
        this.numFacture = numFacture;
        this.descritpion = descritpion;
    }

    public String toString(){
        return date + "\t" + typeOperation + "\t" + montant + "\t" + numFacture + "\t" + descritpion;
    }
}