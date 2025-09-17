package Compte;

import java.util.ArrayList;

public class CompteEpargne extends Compte{
    private float tauxInteret;

    public CompteEpargne(double solde, String code, String password){
        setCode(code);
        setSolde(solde);
        setPassword(password);
    }

    public String retirer(double montant){
        return "ah";
    }
    public double calculerInteret(){
        return 0;
    }

    public ArrayList<String> afficherDetails(){
        return getListOperations();
    }
}
