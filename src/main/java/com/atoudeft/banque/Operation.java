import java.io.Serializable;

public class Operation implements Serializable{
    private static final long serialVersionUID = 1L;

    protected double montant;
    protected Date date;
    protected String typeOperation;

    public Operation(double montant, String typeOperation){
        this.montant = montant;
        this.typeOperation = typeOperation;
        this.date = new Date();
    }

    public double getMontant() {
        return montant;
    }

    public Date getDate() {
        return date;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }
}