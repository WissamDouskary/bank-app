package Operation;


import Compte.Compte;

import java.time.LocalDateTime;

public class Retrait extends Operation {
    private String destination;

    public Retrait(double montant, String destination) {
        super(montant);
        this.destination = destination;
    }


    @Override
    public String getNumero() {
        return numero;
    }

    @Override
    public LocalDateTime getDate() {
        return date;
    }

    @Override
    public double getMontant() {
        return montant;
    }

    public String getDestination() {
        return destination;
    }

    public String makeOperation(){
        return "";
    }

    public String saveOperation(){
        return "Retrait: retraitID: "+getNumero()+" from "+getDestination()+" amount: "+ getMontant()+ "DH on Date :"+getDate();
    }
}