package Operation;


import java.time.LocalDateTime;

public class Retrait extends Operation{
    protected String destination;

    public Retrait(double montant, String destination){
        super(montant);
        this.destination = destination;
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
