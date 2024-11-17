public class OperationDepot {

    public OperationDepot(double montant){
        super(montant, "DEPOT");
    }

    @Override
    public String toString(){
        return date + "\t" + typeOperation + "\t" + montant;
    }
}