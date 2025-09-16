package Operation;

import java.time.LocalDateTime;
import java.util.Random;

public class Operation {
    protected String numero;
    protected LocalDateTime date;
    protected double montant;

    public Operation(double montant) {
        this.numero = generateOperationNumber().toString();
        this.date = LocalDateTime.now();
        this.montant = montant;
    }

    public String getNumero() { return numero; }
    public LocalDateTime getDate() { return date; }
    public double getMontant() { return montant; }

    public Integer generateOperationNumber(){
        Random rn = new Random();
        return rn.nextInt(1000000);
    }
}
