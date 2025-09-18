package Operation;

import java.time.LocalDateTime;
import java.util.Random;

public abstract class Operation {
    protected String numero;
    protected LocalDateTime date;
    protected double montant;

    public Operation(double montant) {
        this.numero = generateOperationNumber().toString();
        this.date = LocalDateTime.now();
        this.montant = montant;
    }

    public abstract String getNumero();
    public abstract LocalDateTime getDate();
    public abstract double getMontant();

    public Integer generateOperationNumber(){
        Random rn = new Random();
        return rn.nextInt(1000000);
    }
}
