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

    public String makeOperation() {
        return "";
    }

    public String makeOperation(Compte sender, Compte reciever){
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
        return "Versement : VersementID: "+getNumero()+" for "+getSource()+" from "+senderCode+" to "+recieverCode+" with amount "+getMontant()+"Dh on Date: "+getDate();
    }

    public String saveOperation(String senderCode){
        return "Versement : VersementID: "+getNumero()+" from "+senderCode+" send amount "+getMontant()+"Dh on Date: "+getDate();
    }
}
