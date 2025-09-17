package Compte;

import java.util.ArrayList;

public abstract class Compte {
    private String code;
    private double solde;
    private String password;
    private ArrayList<String> listOperations = new ArrayList<>();

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getSolde() {
        return solde;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void setListOperations(ArrayList<String> listOperations) {
        this.listOperations = listOperations;
    }

    public ArrayList<String> getListOperations() {
        return listOperations;
    }

    public abstract String retirer(double montant);
    public abstract double calculerInteret();
    public abstract ArrayList<String> afficherDetails();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
