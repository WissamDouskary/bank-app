package Operation;


public class Retrait extends Operation{
    protected String destination;

    public Retrait(double montant, String destination){
        super(montant);
        this.destination = destination;
    }
}
