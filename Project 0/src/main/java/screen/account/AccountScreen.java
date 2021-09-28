package screen.account;

import banking.Accounts;
import banking.Customer;
import screen.Screen;

import java.text.NumberFormat;
import java.util.Scanner;

public class AccountScreen extends Screen {



    private static void runAccountScreen(Customer a) {
        if (a.getAccounts().size() != 0) {

            String message = null;
            boolean quit = false;
            while (!quit) {
                Screen.clearScreen();

                Screen.printMessage(message);
                if (a.getAccounts().size() >= 1) {
                    System.out.println("\t\t\t\t\t\t\t=========================" + a.getFirstName() + " " + a.getLastName() + "'s account===========================");
                    a.setTotalBalance(a.getAccounts());
                    String stringTotalBalance = NumberFormat.getCurrencyInstance().format(a.getTotalBalance());
                    System.out.println("\t\t\t\t\t\t\tYou have a total balance of " + stringTotalBalance);
                    int numAcct = a.getAccounts().size();

                    String acct;

                    if (a.getAccounts().size() > 1) {
                        acct = "s";
                    } else {
                        acct = "";
                    }
                    System.out.println("\t\t\t\t\t\t\tYou have " + numAcct + " account" + acct + ".\n\t\t\t\t\t\t\t==========================================================\n\t\t\t\t\t\t\tAcct#");
                    a.getAccounts().display();
//            for (int i = 0; i<numAcct; i++) {
//                String acctBal = NumberFormat.getCurrencyInstance().format(a.getAccounts().get(i).getBalance());
//                System.out.println((i+1) + ") " +a.getAccounts().get(i).getAccount_id() + " has a balance of " + acctBal + ".");
//            }
                    System.out.println("\t\t\t\t\t\t\tWhich account would you like to view?\n\t\t\t\t\t\t\tEnter the account number or enter \"B\" to go back to the customer screen");
                    //Create the scanner
                    Scanner sc = new Scanner(System.in);
                    String entry = sc.next();
                    // implement a switch statment below
                    if (entry.equals("B") || entry.equals("b"))
                        quit = true;

                }//end if

            }//end while loop
        }
    }






    @Override
    public void runScreen() {
        //will not need for this class.
    } //Do Not Use

    @Override
    public void runScreen(Customer a) {
        AccountScreen.runAccountScreen(a);
    }

    @Override
    public void runScreen(Customer a, Accounts b) {}// Do Not use


}
