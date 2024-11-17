public class OperationRetrait{

    public OperationRetrait(double montant){
        super(montant, "RETRAIT");
    }

    @Override
    public String toString(){
        return date + "\t" + typeOperation + "\t" + montant;
    }
}