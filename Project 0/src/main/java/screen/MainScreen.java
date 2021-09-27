package screen;

import banking.Accounts;
import banking.Customer;

import java.util.Scanner;

public class MainScreen extends Screen{



    private  static  void runMainScreen() {
        // Set variables
        boolean quit = false;
        String message = null;


        // while loop to run main menu, until user quits the program
        while(!quit) {

            //Clear old screens from the console
            Screen.clearScreen();

            // if message contains a string print it to the console
            Screen.printMessage(message);

            // print the Main menu to the console
            System.out.print(
                    "\t\t\t\t\t====================Main Menu=======================" +
                            "\n\t\t\t\t\tInset the number from the option you want below:" +
                            "\n\n\t\t\t\t\t\t\t1)Login" +
                            "\n\t\t\t\t\t\t\t2)Create an account" +
                            "\n\t\t\t\t\t\t\tQ)Quit the program" +
                            "\n\t\t\t\t\t===================================================" +
                            "\n\n\n\t\t\t\t\tEnter selection : ");

            // create a scanner to get the users input and assign it to entry
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            // implement a switch statement runs off the users input
            switch (entry){
                case "1":
                    // send user to login screen
                    Screen ls = new LoginScreen();
                    ls.runScreen();

                    //set message to null, in case it has a string
                    message = null;
                    break;

                case "2":
                    Screen ccs = new CreateACustomer();
                    ccs.runScreen();
                    break;
                case "Q":
                case "q":
                    // Shut down the program
                    quit = true;
                    break;
                default:
                    // handle invalid selection
                    message = "Invalid selection. Try entering 1, 2, or q.";


            }//end switch

        }//end while
    }



    @Override
    public void runScreen() {
        MainScreen.runMainScreen();
    }

    @Override
    public void runScreen(Customer a) {} //Do Not Use

    @Override
    public void runScreen(Customer a, Accounts b) {} //Do Not Use


}
