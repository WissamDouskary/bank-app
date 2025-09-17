package Compte;

import java.util.ArrayList;

public class CompteEpargne extends Compte{
    private float tauxInteret;

    public CompteEpargne(double solde, String code){
        setCode(code);
        setSolde(solde);
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
