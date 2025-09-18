package Compte;

import java.util.ArrayList;
import java.util.Scanner;

// courant avait decouvert!!

public class CompteCourant extends Compte{

    private static double decouvert = 1000;

    public CompteCourant(double solde, String code, String password){
        setCode(code);
        setSolde(solde);
        setPassword(password);
    }

    public String retirer(double montant){
        String message = null;

        if (montant <= 0) {
            message = "Please enter a valid number!";
        }
        else if (getSolde() >= montant) {
            setSolde(getSolde() - montant);
            message = "Withdraw completed successfully | This operation affected your native balance.";
        }
        else if ((getSolde() + decouvert) >= montant) {
            System.out.println("This withdrawal will affect your decouvert. Do you want to proceed? (1. Yes / 2. No):");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if (choice == 1) {
                double remaining = montant - getSolde();
                setSolde(0);
                decouvert -= remaining;
                message = "Withdraw completed successfully - your decouvert now is: " + decouvert;
            } else {
                message = "Operation canceled.";
            }
        }
        else {
            message = "You don't have enough money to withdraw!";
        }
        return message;
    }

    public static double getDecouvert() {
        return decouvert;
    }

    public double calculerInteret(){
        return 0;
    }

    public ArrayList<String> afficherDetails(){
        return getListOperations();
    }
}
