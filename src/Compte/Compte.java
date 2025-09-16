package Compte;

import java.util.ArrayList;

public abstract class Compte {
    private String code;
    private double solde;
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


    public abstract void retirer();
    public abstract double calculerInteret();
    public abstract ArrayList<String> afficherDetails();
}
