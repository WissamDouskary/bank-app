import Compte.Compte;
import Compte.CompteCourant;
import Compte.CompteEpargne;

import Operation.Retrait;
import Operation.Versement;
import Operation.Operation;
import sun.rmi.runtime.Log;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static HashMap<String, Compte> comptes = new HashMap<>();
    private static Compte LoggedInAccount = null;
    private static final String currency = "DH";
    private static boolean isLoggedIn = false;

    public static Compte createAccount(int type, String code, String password) {
        if (type == 1) {
            Compte newCompte = new CompteCourant(150.0, code, password);
            comptes.put(code, newCompte);
            LoggedInAccount = newCompte;
            return newCompte;
        } else if (type == 2) {
            Compte newCompte = new CompteEpargne(150.0, code, password);
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
                System.out.println("Type your account password: ");
                sc.nextLine();
                String password = sc.nextLine();
                if(comptes.get(i).getPassword().equals(password)){
                    existingAcc = comptes.get(i);
                    LoggedInAccount = existingAcc;
                    isLoggedIn = true;
                    break;
                }else{
                    System.out.println("invalid cardinals!");
                }
            }else{
                System.out.println("Account doesn't found, check you cardinals!");
            }
        }
        return existingAcc;
    }

    public static int intValidator(String prompt) {
        int number = -1;
        while (true) {
            try {
                System.out.println(prompt);
                number = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.nextLine();
            }
        }
        return number;
    }

    public static void main(String[] args){
        boolean isClaimed = false;
        while(true){
            if(!isLoggedIn && LoggedInAccount == null){
                System.out.println("BANK ACCOUNT ----");
                System.out.println("1. Create Accout");
                System.out.println("2. Log in");
                System.out.println("3. Exit");

                int choice = intValidator("choose a number (1-3)");

                sc.nextLine();

                switch (choice){
                    case 1:
                        System.out.println("REGISTRATION -------------");
                        int code = intValidator("Enter code (just numbers) you want for you account (CPT-XXXXX): ");
                        String form = "CPT-"+code;
                        while(!form.matches("CPT-\\d{5}")){
                            System.out.println("You have an issue, please enter 5 numbers for UUID: ");
                            code = sc.nextInt();
                            form = "CPT-"+code;
                        }
                        for(String i : comptes.keySet()){
                            while(form.equals(i)){
                                System.out.println("This code is defined before, please choose another one: ");
                                code = sc.nextInt();
                                form = "CPT-"+code;
                            }
                        }

                        sc.nextLine();

                        System.out.println("Enter account type (1. Courant / 2. Epargne): ");
                        int type = sc.nextInt();
                        while (type != 1 && type != 2) {
                            System.out.println("Please enter a valid type (1. Courant / 2. Epargne): ");
                            type = sc.nextInt();
                        }
                        System.out.println("Enter your password (more than 8 caracters): ");
                        String password = sc.nextLine();
                        while(password.length() <= 8){
                            System.out.println("please enter a strong password (more than 8 caracters): ");
                            password = sc.nextLine();
                        }
                        createAccount(type, "CPT-"+code, password);
                        System.out.println("account created success!");
                        isLoggedIn = true;
                        break;
                    case 2:
                        System.out.println("LOGIN --------");
                        System.out.println("Type your Account Code (CPT-XXXXX):");
                        int loginCode = sc.nextInt();
                        String codeFormat = "CPT-"+loginCode;
                        while(!codeFormat.matches("CPT-\\d{5}")){
                            System.out.println("Type your Account Code (CPT-XXXXX) with 5 numbers:");
                            codeFormat = "CPT-"+loginCode;
                        }
                        Compte existingAcc = logIn(codeFormat);
                        break;
                    case 3:
                        System.out.println("bye!");
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid choice!");
                        break;
                }
            }else{
                System.out.println("BANK ACCOUNT ----");
                System.out.println("1. Account Information");
                System.out.println("2. Withdraw");
                if(LoggedInAccount instanceof CompteEpargne){
                    if(!isClaimed){
                        System.out.println("3. Apply Interet");
                    }
                    System.out.println(isClaimed ? "3. Logout" : "4. Logout");
                }
                if(LoggedInAccount instanceof CompteCourant){
                    System.out.println("3. Logout");
                }

                System.out.println("5. Make a transfer between accounts");
                System.out.println("6. View List Of Transactions");

                int choice = 0;

                try{
                    System.out.println("Choose a number: ");
                    choice = sc.nextInt();
                }
                catch (InputMismatchException e){
                    System.out.println("Please enter a valid number!");
                    sc.nextLine();
                }

                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("\n ACCOUNT INFORMATION'S --------");
                        System.out.println("Account ID: " + LoggedInAccount.
                                getCode());
                        System.out.println("Solde: " + LoggedInAccount.getSolde() + " " + currency);
                        if (LoggedInAccount instanceof CompteCourant) {
                            CompteCourant courantAccount = (CompteCourant) LoggedInAccount;
                            System.out.println("Decouvert available: " + CompteCourant.getDecouvert() + " " + currency + "\n");
                        }
                        break;
                    case 2:
                        System.out.println("\n WITHDRAW --------");
                        double amount = 0;
                        try{
                            System.out.println("Enter amount you want to withdraw: ");
                            amount = sc.nextDouble();
                        }
                        catch (InputMismatchException e){
                            System.out.println("Please enter a valid number!");
                            sc.nextLine();
                        }
                        System.out.println("Enter destination of this retrait: ");
                        System.out.println("1. Distributeur ATM");
                        System.out.println("2. Chèque");
                        System.out.println("3. Virement sortant");

                        int Destchoice = 0;
                        String destination = null;
                        try{
                            System.out.println("Choose a selection");
                            Destchoice = sc.nextInt();
                        }
                        catch (InputMismatchException e){
                            System.out.println("Please enter a valid number!");
                            sc.nextLine();
                        }
                        switch (Destchoice) {
                            case 1:
                                destination = "Distributeur ATM";
                                break;
                            case 2:
                                destination = "Chèque";
                                break;
                            case 3:
                                destination = "Virement sortant";
                                break;
                            default:
                                System.out.println("Invalid selection");
                                break;
                        }
                        System.out.println(LoggedInAccount.retirer(amount));
                        if (destination == null) {
                            System.out.println("Invalid destination. Operation cancelled.");
                            break;
                        }
                        Retrait retrait = new Retrait(amount, destination);
                        LoggedInAccount.setListOperations(retrait.saveOperation());
                        break;
                    case 3:
                        if(LoggedInAccount instanceof CompteEpargne){
                            CompteEpargne epargneAccount = (CompteEpargne) LoggedInAccount;
                            epargneAccount.appliquerInteret();
                            isClaimed = true;
                            System.out.println("Congratulations, Interet applicated successfuly!");
                        }else{
                            System.out.println("LOGOUT --------");
                            int wantToLogOut = 0;
                            try{
                                System.out.println("Do you want to log out (1. Yes / 2. No): ");
                                wantToLogOut = sc.nextInt();
                            }
                            catch (InputMismatchException e){
                                System.out.println("Please enter a valid number!");
                                sc.nextLine();
                            }
                            if (wantToLogOut == 1) {
                                isLoggedIn = false;
                                LoggedInAccount = null;
                                System.out.println("You have successfully logged out.");
                            }
                        }
                        break;
                    case 4:
                        if(LoggedInAccount instanceof CompteEpargne){
                            System.out.println("LOGOUT --------");
                            int wantToLogOut = 0;
                            try{
                                System.out.println("Do you want to log out (1. Yes / 2. No): ");
                                wantToLogOut = sc.nextInt();
                            }
                            catch (InputMismatchException e){
                                System.out.println("Please enter a valid number!");
                                sc.nextLine();
                            }
                            if (wantToLogOut == 1) {
                                isLoggedIn = false;
                                LoggedInAccount = null;
                                System.out.println("You have successfully logged out.");
                            }
                        }
                        break;
                    case 5:
                        System.out.println("VERSEMENT----------------");
                        System.out.println("Enter Code of reciever (CPT-XXXXX / X is number!): ");
                        int code = sc.nextInt();
                        String form = "CPT-"+code;

                        Compte recieverAcc = null;

                        while(!form.matches("CPT-\\d{5}")){
                            System.out.println("You have an issue, please enter 5 numbers for UUID: ");
                            code = sc.nextInt();
                            form = "CPT-"+code;
                        }

                        boolean isFound = false;

                        while (!isFound) {
                            System.out.print("Enter user code: ");
                            String userEnter = sc.nextLine();
                            String codeForm = "CPT-"+userEnter;

                            for (String i : comptes.keySet()) {
                                if (codeForm.equals(i)) {
                                    recieverAcc = comptes.get(i);
                                    isFound = true;
                                    break;
                                }
                            }

                            if (!isFound) {
                                System.out.println("User not found! Please try again.");
                            }
                        }

                        System.out.println("User Found.");

                        System.out.println("Type amount you want to send: ");
                        double versementAmount = sc.nextDouble();
                        System.out.println("Type source of this money:");
                        System.out.println("1. Virement externe");
                        System.out.println("2. Dépôt espèces");
                        System.out.println("3. Salaire");
                        System.out.println("Select a number (1-3): ");
                        sc.nextLine();

                        int choiceType = sc.nextInt();
                        String versementType = null;

                        switch (choiceType){
                            case 1:
                                versementType = "Virement externe";
                                break;
                            case 2:
                                versementType = "Dépôt espèces";
                                break;
                            case 3:
                                versementType = "Salaire";
                                break;
                            default:
                                System.out.println("invalid selection!");
                                break;
                        }
                        Versement versement = new Versement(versementAmount, versementType);
                        versement.makeOperation(LoggedInAccount, recieverAcc);
                        LoggedInAccount.setListOperations(versement.saveOperation(LoggedInAccount.getCode(), recieverAcc.getCode()));
                        recieverAcc.setListOperations(versement.saveOperation(LoggedInAccount.getCode()));
                        System.out.println("Versement done!");
                        break;
                    case 6:
                        System.out.println("TRANSACTION HISTORY------");
                        if(LoggedInAccount.getListOperations().isEmpty()){
                            System.out.println("There is no transaction yet!");
                        }
                        for(String t : LoggedInAccount.getListOperations()){
                            System.out.println(t);
                        }
                        break;
                    case 7:
                        for(String i : comptes.keySet()){
                            System.out.println(i);
                        }
                        break;
                    default:
                        System.out.println("invalid choice");
                        break;
                }
            }
        }
    }
}
