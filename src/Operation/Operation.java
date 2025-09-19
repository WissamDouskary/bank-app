package Operation;

import Compte.Compte;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;


public abstract class Operation {
    protected UUID numero;
    protected LocalDateTime date;
    protected double montant;

    public Operation(double montant) {
        this.numero = UUID.randomUUID();
        this.date = LocalDateTime.now();
        this.montant = montant;
    }

    public abstract UUID getNumero();
    public abstract LocalDateTime getDate();
    public abstract double getMontant();
    public abstract String makeOperation();
    public abstract String saveOperation();

    public Integer generateOperationNumber(){
        Random rn = new Random();
        return rn.nextInt(1000000);
    }
}
