package Compte;

import java.util.ArrayList;

abstract class Compte {
    private String Code;
    private float solde;
    private ArrayList<String> listeOperations;

    public abstract void retirer();
    public abstract float calculerInteret();
    public abstract ArrayList<String> afficherDetails();
}
