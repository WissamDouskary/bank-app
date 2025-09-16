package Compte;

import java.util.ArrayList;

// courant avait decouvert!!

public class CompteCourant extends Compte{

    public static double decouvert = 1000;

    public CompteCourant(double solde, String code){
        setCode(code);
        setSolde(solde);
    }

    public void retirer(){

    }
    public double calculerInteret(){
        return 0;
    }

    public ArrayList<String> afficherDetails(){
        return getListOperations();
    }
}
