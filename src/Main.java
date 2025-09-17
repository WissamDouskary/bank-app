import Compte.Compte;
import Compte.CompteCourant;
import Compte.CompteEpargne;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static HashMap<String, Compte> comptes = new HashMap<>();
    private static Compte LoggedInAccount = null;
    private static final String currency = "DH";

    public static Compte createAccount(int type, String code) {
        if (type == 1) {
            CompteCourant newCompte = new CompteCourant(150.0, code);
            comptes.put(code, newCompte);
            LoggedInAccount = newCompte;
            return newCompte;
        } else if (type == 2) {
            CompteEpargne newCompte = new CompteEpargne(150.0, code);
            comptes.put(code, newCompte);
            LoggedInAccount = newCompte;
            return newCompte;
        } else {
            throw new IllegalArgumentException("Type de compte invalide");
        }
    }

    public static Compte logIn(String code){
        Compte existingAcc = null;

        if(comptes.isEmpty()){
            System.out.println("There is no account on storage!");
        }

        for(String i : comptes.keySet()){
            if(code.equals(i)){
                existingAcc = comptes.get(i);
                LoggedInAccount = existingAcc;
            }else{
                System.out.println("Account doesn't found, check you cardinals!");
            }
        }
        return existingAcc;
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
                    System.out.println("REGISTRATION -------------");
                    System.out.println("Enter code (just numbers) you want for you account (CPT-XXXXX): ");
                    int code = sc.nextInt();
                    String form = "CPT-"+code;
                    while(!form.matches("CPT-\\d{5}")){
                        System.out.println("You have an issue, please enter 5 numbers for UUID: ");
                        code = sc.nextInt();
                    }
                    for(String i : comptes.keySet()){
                        String codeformat = "CPT-"+code;
                        if(codeformat.equals(i)){
                            System.out.println("This code is defined before, please choose another one: ");
                            code = sc.nextInt();
                        }
                    }
                    sc.nextLine();

                    System.out.println("Enter account type (1. Courant / 2. Epargne): ");
                    int type = sc.nextInt();
                    while (type != 1 && type != 2) {
                        System.out.println("Please enter a valid type (1. Courant / 2. Epargne): ");
                        type = sc.nextInt();
                    }
                    createAccount(type, "CPT-"+code);
                    System.out.println("account created success!");
                    isLoggedIn = true;
                    break;
                case 2:
                    System.out.println("LOGIN --------");
                    System.out.println("Type your Account Code (CPT-XXXXX):");
                    int loginCode = sc.nextInt();
                    String codeFormat = "CPT-"+loginCode;
                    if(!codeFormat.matches("CPT-\\d{5}")){
                        System.out.println("Type your Account Code (CPT-XXXXX) with 5 numbers:");
                        loginCode = sc.nextInt();
                    }else{
                        Compte existingAcc = logIn(codeFormat);
                        isLoggedIn = true;
                    }
                    break;
                case 3:
                    System.out.println("bye!");
                    return;
                default:
                    System.out.println("Invalid choice!");
                    break;
            }
        }

        while(isLoggedIn && LoggedInAccount != null){
            System.out.println("BANK ACCOUNT ----");
            System.out.println("1. Account Information");
            System.out.println("2. Withdraw");
            System.out.println("3. Logout");
            System.out.println("Choose a number (1-3): ");

            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\n ACCOUNT INFORMATION'S --------");
                    System.out.println("Account ID: " + LoggedInAccount.getCode());
                    System.out.println("Solde: " + LoggedInAccount.getSolde() + " " + currency);
                    if (LoggedInAccount instanceof CompteCourant) {
                        CompteCourant courantAccount = (CompteCourant) LoggedInAccount;
                        System.out.println("Decouvert available: " + CompteCourant.getDecouvert() + " " + currency + "\n");
                    }
                    break;
                case 2:
                    System.out.println("\n WIDTHRAW --------");
                    System.out.println("Enter amount you want to withdraw: ");
                    double amount = sc.nextDouble();
                    System.out.println(LoggedInAccount.retirer(amount));
                    break;
                case 3:
                    System.out.println("LOGOUT --------");
                    System.out.println("Are you want to logout (1. Yes / 2. No): ");
                    int wantToLogOut = sc.nextInt();
                    if(wantToLogOut == 1){
                        isLoggedIn = false;
                        LoggedInAccount = null;
                    }
                    break;
            }
        }

        sc.close();
    }
}
