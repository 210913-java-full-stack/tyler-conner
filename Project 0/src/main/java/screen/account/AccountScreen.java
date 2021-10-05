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
            //set variables
            String message = null;
            boolean quit = false;

            //while loop to run screen as long as need.
            while (!quit) {
                //clear screen
                Screen.clearScreen();
                //print message if not null
                Screen.printMessage(message);
                    // initialize a String to determine if account is plural or not
                    String acct;

                    if (a.getAccounts().size() > 1) {
                        acct = "s";
                    } else {
                        acct = "";
                    }

                    //print title of menu
                    System.out.println("\t\t\t\t\t\t\t=========================" + a.getFirstName() + " " + a.getLastName() + "'s account" + acct + "===========================");
                    //set the total balance
                    a.setTotalBalance(a.getAccounts());
                    // format the total balance
                    String stringTotalBalance = NumberFormat.getCurrencyInstance().format(a.getTotalBalance());
                    //print total balance
                    System.out.println("\t\t\t\t\t\t\tYou have a total balance of " + stringTotalBalance);
                    //create an int equal to the size of account array
                    int numAcct = a.getAccounts().size();

                    //print the number of accounts
                    System.out.println("\t\t\t\t\t\t\tYou have " + numAcct + " account" + acct + ".\n\t\t\t\t\t\t\t==========================================================\n\t\t\t\t\t\t\tAcct#");

                    //print all the accounts
                    a.getAccounts().display();
                    // get the user to select what count they want
                    System.out.print("\t\t\t\t\t\t\tWhich account would you like to view?\n\t\t\t\t\t\t\tEnter the account number or enter \"B\" to go back to the customer screen: ");
                    //Create the scanner
                    Scanner sc = new Scanner(System.in);
                    String entry = sc.next();



                    // if B quit else validate
                    if (entry.equals("B")|| entry.equals("b")){
                        quit = true;
                    } else {
                        Pattern p = Pattern.compile("9[0-9]{5}");
                        Matcher m = p.matcher(entry);
                        boolean bool = m.matches();

                        if (bool) {

                            //get the index of entry
                            boolean entryIsInUserAccountList = false;
                            int index = 0;
                            for (int i = 0; i < a.getAccounts().size(); i++) {
                                if (Integer.parseInt(entry) == a.getAccounts().get(i).getAccount_id()) {
                                    entryIsInUserAccountList = true;
                                    index = i;

                                }//endif
                            }// end for

                            if (entryIsInUserAccountList) {
                                // create the account view screen and send the user and account as parameters
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
