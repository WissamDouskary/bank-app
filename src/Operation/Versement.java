package Operation;


public class Versement extends Operation{
    protected String source;

    public Versement(double montant, String source){
        super(montant);
        this.source = source;
    }

}
