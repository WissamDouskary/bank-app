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

    public static Compte logIn(String code) {
        Compte existingAcc = null;
        boolean isFound = false;

        if (comptes.isEmpty()) {
            System.out.println("There is no account on storage!");
        }

        for (String i : comptes.keySet()) {
            if (code.equals(i)) {
                System.out.println("Type your account password: ");
                sc.nextLine();
                String password = sc.nextLine();
                if (comptes.get(i).getPassword().equals(password)) {
                    existingAcc = comptes.get(i);
                    LoggedInAccount = existingAcc;
                    isLoggedIn = true;
                    isFound = true;
                    System.out.println("Welcome!");
                    break;
                } else {
                    System.out.println("invalid cardinals!");
                    break;
                }
            }
            if (!isFound) {
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
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Please enter a valid number.");
                sc.nextLine();
            }
        }
        return number;
    }

    public static void main(String[] args) {
        boolean isClaimed = false;
        while (true) {
            if (!isLoggedIn && LoggedInAccount == null) {
                System.out.println("BANK ACCOUNT ============");
                System.out.println("1. Create Accout");
                System.out.println("2. Log in");
                System.out.println("3. Exit");
                System.out.println("==========================");

                int choice = intValidator("choose a number (1-3)");

                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("REGISTRATION -------------");
                        int code = intValidator("Enter code (just numbers) you want for you account (CPT-XXXXX): ");
                        String form = "CPT-" + code;
                        while (!form.matches("CPT-\\d{5}")) {
                            System.out.println("You have an issue, please enter 5 numbers for UUID: ");
                            code = sc.nextInt();
                            form = "CPT-" + code;
                        }
                        for (String i : comptes.keySet()) {
                            while (form.equals(i)) {
                                code = intValidator("This code is defined before, please choose another one: ");
                                form = "CPT-" + code;
                            }
                        }

                        sc.nextLine();

                        int type = intValidator("Enter account type (1. Courant / 2. Epargne): ");
                        while (type != 1 && type != 2) {
                            type = intValidator("Please enter a valid type (1. Courant / 2. Epargne): ");
                        }
                        System.out.println("Enter your password (more than 8 caracters): ");
                        String password = sc.nextLine();
                        while (password.length() <= 8) {
                            System.out.println("please enter a strong password (more than 8 caracters): ");
                            password = sc.nextLine();
                        }
                        createAccount(type, "CPT-" + code, password);
                        System.out.println("account created success!");
                        isLoggedIn = true;
                        break;
                    case 2:
                        System.out.println("LOGIN --------");
                        int loginCode = intValidator("Type your Account Code (CPT-XXXXX):");
                        String codeFormat = "CPT-" + loginCode;
                        while (!codeFormat.matches("CPT-\\d{5}")) {
                            System.out.println("Type your Account Code (CPT-XXXXX) with 5 numbers:");
                            codeFormat = "CPT-" + loginCode;
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
            } else {
                System.out.println("BANK ACCOUNT ===========================");
                System.out.println("1. Account Information");
                System.out.println("2. Withdraw");
                if (LoggedInAccount instanceof CompteEpargne) {
                    if (!isClaimed) {
                        System.out.println("3. Apply Interet");
                    }
                    System.out.println(isClaimed ? "3. Logout" : "4. Logout");
                }
                if (LoggedInAccount instanceof CompteCourant) {
                    System.out.println("3. Logout");
                }

                System.out.println("4. Make a transfer between accounts");
                System.out.println("5. View List Of Transactions");
                System.out.println("=======================================");

                int choice = 0;

                try {
                    System.out.println("Choose a number: ");
                    choice = sc.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number!");
                    sc.nextLine();
                }

                sc.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("ACCOUNT INFORMATION'S =============");
                        System.out.println("Account ID: " + LoggedInAccount.
                                getCode());
                        System.out.println("Solde: " + LoggedInAccount.getSolde() + " " + currency);
                        if (LoggedInAccount instanceof CompteCourant) {
                            CompteCourant courantAccount = (CompteCourant) LoggedInAccount;
                            System.out.println("Decouvert available: " + CompteCourant.getDecouvert() + " " + currency + "\n");
                        }
                        break;
                    case 2:
                        System.out.println("\n WITHDRAW =============");
                        double amount = 0;
                        try {
                            System.out.println("Enter amount you want to withdraw: ");
                            amount = sc.nextDouble();
                        } catch (InputMismatchException e) {
                            System.out.println("Please enter a valid number!");
                            sc.nextLine();
                        }
                        System.out.println("Enter destination of this retrait: ");
                        System.out.println("1. Distributeur ATM");
                        System.out.println("2. Chèque");
                        System.out.println("3. Virement sortant");

                        int Destchoice = 0;
                        String destination = null;
                        try {
                            System.out.println("Choose a selection");
                            Destchoice = sc.nextInt();
                        } catch (InputMismatchException e) {
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
                        if (LoggedInAccount instanceof CompteEpargne) {
                            CompteEpargne epargneAccount = (CompteEpargne) LoggedInAccount;
                            epargneAccount.appliquerInteret();
                            isClaimed = true;
                            System.out.println("Congratulations, Interet applicated successfuly!");
                        } else {
                            System.out.println("LOGOUT =============");
                            int wantToLogOut = 0;
                            try {
                                System.out.println("Do you want to log out (1. Yes / 2. No): ");
                                wantToLogOut = sc.nextInt();
                            } catch (InputMismatchException e) {
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
                        if (LoggedInAccount instanceof CompteEpargne) {
                            System.out.println("LOGOUT =============");
                            int wantToLogOut = 0;
                            try {
                                System.out.println("Do you want to log out (1. Yes / 2. No): ");
                                wantToLogOut = sc.nextInt();
                            } catch (InputMismatchException e) {
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
                        System.out.println("VERSEMENT =============");

                        Compte receiverAcc = null;
                        boolean isFound = false;

                        while (!isFound) {
                            System.out.print("Enter 5-digit recipient account number (UUID): ");
                            String input = sc.nextLine();

                            if (!input.matches("\\d{5}")) {
                                System.out.println("Invalid format. Please enter exactly 5 digits.");
                                continue;
                            }

                            String codeForm = "CPT-" + input;

                            if (!comptes.containsKey(codeForm)) {
                                System.out.println("User not found! Please try again.");
                            } else if (codeForm.equals(LoggedInAccount.getCode())) {
                                System.out.println("You can't send money to yourself!");
                            } else {
                                receiverAcc = comptes.get(codeForm);
                                isFound = true;
                            }
                        }

                        System.out.println("User Found.");

                        double versementAmount = 0;
                        while (true) {
                            System.out.print("Type amount you want to send: ");
                            if (sc.hasNextDouble()) {
                                versementAmount = sc.nextDouble();
                                sc.nextLine();
                                if (versementAmount > 0) break;
                                else System.out.println("Amount must be positive.");
                            } else {
                                System.out.println("Invalid input. Please enter a number.");
                                sc.nextLine();
                            }
                        }

                        System.out.println("Type source of this money:");
                        System.out.println("1. Virement externe");
                        System.out.println("2. Dépôt espèces");
                        System.out.println("3. Salaire");

                        String versementType = null;
                        while (versementType == null) {
                            System.out.print("Select a number (1-3): ");
                            if (sc.hasNextInt()) {
                                int transfertChoice = sc.nextInt();
                                sc.nextLine();

                                switch (transfertChoice) {
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
                                        System.out.println("Invalid selection!");
                                }
                            } else {
                                System.out.println("Invalid input.");
                                sc.nextLine();
                            }
                        }

                        Versement versement = new Versement(versementAmount, versementType);
                        versement.makeOperation(LoggedInAccount, receiverAcc);

                        LoggedInAccount.setListOperations(versement.saveOperation(LoggedInAccount.getCode(), receiverAcc.getCode()));
                        receiverAcc.setListOperations(versement.saveOperation(LoggedInAccount.getCode()));

                        System.out.println("Versement done!");
                        break;

                    case 6:
                        System.out.println("TRANSACTION HISTORY =============");
                        if (LoggedInAccount.getListOperations().isEmpty()) {
                            System.out.println("There is no transaction yet!");
                        }
                        for (String t : LoggedInAccount.getListOperations()) {
                            System.out.println(t);
                        }
                        break;
                    case 7:
                        for (String i : comptes.keySet()) {
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
