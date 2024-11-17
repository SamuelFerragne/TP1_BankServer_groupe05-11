public class OperationFacture {
    public String numFacture;
    public String descritpion;

    public OperationFacture(double montant, String numFacture, String descritpion){
        super(montant, "FACTURE");
        this.numFacture = numFacture;
        this.descritpion = descritpion;
    }
}