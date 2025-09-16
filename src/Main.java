import Compte.Compte;
import Compte.CompteCourant;
import Compte.CompteEpargne;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static HashMap<String, Compte> comptes = new HashMap<>();

    public static Compte createAccount(int type, String code) {
        if (type == 1) {
            CompteCourant newCompte = new CompteCourant(0.0, code);
            comptes.put(code, newCompte);
            return newCompte;
        } else if (type == 2) {
            CompteEpargne newCompte = new CompteEpargne(0.0, code);
            comptes.put(code, newCompte);
            return newCompte;
        } else {
            throw new IllegalArgumentException("Type de compte invalide");
        }
    }

    public static void main(String[] args){
        boolean isLoggedIn = false;

        while(!isLoggedIn){
            System.out.println("BANK ACCOUNT ----");
            System.out.println("1. Create Accout");
            System.out.println("2. Log in");
            System.out.println("3. Exit");
            System.out.println("Choose a number (1-3): ");
            int choice = sc.nextInt();

            switch (choice){
                case 1:
                    System.out.println("Enter code (just numbers) you want for you account (CPT-XXXXX): ");
                    int code = sc.nextInt();
                    sc.nextLine();

                    System.out.println("Enter account type (1. Courant / 2. Epargne): ");
                    int type = sc.nextInt();
                    while (type != 1 && type != 2) {
                        System.out.println("Please enter a valid type (1. Courant / 2. Epargne): ");
                        type = sc.nextInt();
                    }
                    createAccount(type, "CPT-"+code);
                    System.out.println("account created success!");
                    break;
                case 2:
                    System.out.println(comptes.get("CPT-12345").getSolde());
                    break;
                case 3:
                    System.out.println("bye!");
                    return;
            }
        }


        sc.close();
    }
}
