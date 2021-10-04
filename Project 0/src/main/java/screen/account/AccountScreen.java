package screen.account;

import banking.Accounts;
import banking.Customer;
import screen.Screen;

import java.text.NumberFormat;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountScreen extends Screen {



    private static void runAccountScreen(Customer a) {


            String message = null;
            boolean quit = false;
            while (!quit) {
                Screen.clearScreen();

                Screen.printMessage(message);

                    String acct;

                    if (a.getAccounts().size() > 1) {
                        acct = "s";
                    } else {
                        acct = "";
                    }
                    System.out.println("\t\t\t\t\t\t\t=========================" + a.getFirstName() + " " + a.getLastName() + "'s account" + acct + "===========================");
                    a.setTotalBalance(a.getAccounts());
                    String stringTotalBalance = NumberFormat.getCurrencyInstance().format(a.getTotalBalance());
                    System.out.println("\t\t\t\t\t\t\tYou have a total balance of " + stringTotalBalance);
                    int numAcct = a.getAccounts().size();


                    System.out.println("\t\t\t\t\t\t\tYou have " + numAcct + " account" + acct + ".\n\t\t\t\t\t\t\t==========================================================\n\t\t\t\t\t\t\tAcct#");
                    a.getAccounts().display();
//            for (int i = 0; i<numAcct; i++) {
//                String acctBal = NumberFormat.getCurrencyInstance().format(a.getAccounts().get(i).getBalance());
//                System.out.println((i+1) + ") " +a.getAccounts().get(i).getAccount_id() + " has a balance of " + acctBal + ".");
//            }
                    System.out.print("\t\t\t\t\t\t\tWhich account would you like to view?\n\t\t\t\t\t\t\tEnter the account number or enter \"B\" to go back to the customer screen: ");
                    //Create the scanner
                    Scanner sc = new Scanner(System.in);
                    String entry = sc.next();



                    // trying something new
                    if (entry.equals("B")|| entry.equals("b")){
                        quit = true;
                    } else {
                        Pattern p = Pattern.compile("9[0-9]{5}");
                        Matcher m = p.matcher(entry);
                        boolean bool = m.matches();

                        if (bool) {


                            boolean entryIsInUserAccountList = false;
                            int index = 0;
                            for (int i = 0; i < a.getAccounts().size(); i++) {
                                if (Integer.parseInt(entry) == a.getAccounts().get(i).getAccount_id()) {
                                    entryIsInUserAccountList = true;
                                    index = i;

                                }//endif
                            }// end for

                            if (entryIsInUserAccountList) {
                                Screen avs = new AccountViewScreen();
                                avs.runScreen(a, a.getAccounts().get(index));
                                message = null;
                            } else {
                                message = "Not a valid selection";
                            }
                        } else {
                            message = "Not a valid selection.";
                        }

                    }//end else






            }//end while loop
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
