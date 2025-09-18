package Operation;

import Compte.Compte;

import java.time.LocalDateTime;

public class Versement extends Operation {
    private String source;

    public Versement(double montant, String source) {
        super(montant);
        this.source = source;
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

    public String getSource() {
        return source;
    }

    public String makeVersement(Compte sender, Compte reciever){
        if(sender.getSolde() < montant){
            return "you don't have enough money to send!";
        }else{
            sender.setSolde(sender.getSolde() - montant);
            reciever.setSolde(reciever.getSolde() + montant);

            return "Versement complete successfully!";
        }
    }

    public String saveOperation() {
        return "Versement :" + getNumero() + " on " + getDate();
    }

    public String saveOperation(String senderCode, String recieverCode){
        return "Versement :"+getNumero()+" from "+senderCode+" to "+recieverCode+" on"+getDate();
    }
}
