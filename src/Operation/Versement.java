package Operation;


import java.time.LocalDateTime;

public class Versement extends Operation{
    protected String source;

    public Versement(double montant, String source){
        super(montant);
        this.source = source;
    }

    public String getNumero() {
        return "";
    }

    public LocalDateTime getDate() {
        return null;
    }

    public double getMontant() {
        return 0;
    }
}
