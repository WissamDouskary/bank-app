package Compte;

import java.util.ArrayList;

public class CompteEpargne extends Compte{
    private static double tauxInteret = 0.04;

    public CompteEpargne(double solde, String code, String password){
        setCode(code);
        setSolde(solde);
        setPassword(password);
    }

    public String retirer(double montant){
        String message = null;
        if(getSolde() >= montant){
            setSolde(getSolde() - montant);

            message = "Withdraw completed successfuly!";
        }else{
            message = "you don't have enough money!";
        }
        return message;
    }

    public double calculerInteret() {
        return getSolde() * tauxInteret;
    }

    public void appliquerInteret() {
        setSolde(getSolde() + calculerInteret());
        System.out.println("Interet : " + calculerInteret());
    }

    public ArrayList<String> afficherDetails(){
        return getListOperations();
    }
}
