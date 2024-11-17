public class OperationTransfer {
    public String numCompteDestinataire;

    public OperationTransfer(double montant, String numCompte){
        super(montant,"TRANSFER");
        this. numCompteDestinataire = numCompte;
    }

}