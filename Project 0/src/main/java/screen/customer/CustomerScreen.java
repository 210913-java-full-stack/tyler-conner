package screen.customer;


import banking.Accounts;
import banking.Customer;
import screen.account.AccountScreen;
import screen.Screen;

import java.util.Scanner;



public class CustomerScreen extends Screen {


    private static void runAccountScreen(Customer a) {

        //set variables
        String message = null;
        boolean quit = false;

        //start the screen
        while(!quit){
            //Clear old screen from console
            Screen.clearScreen();




            //if message contains a string, print message
            Screen.printMessage(message);



            //print customer menu
            System.out.print(
                    "\t\t\t\t\t\t\t==================  " + a.getFirstName()+ " " + a.getLastName() + "  ========================" +
                    "\n\t\t\t\t\t\t\tInset the number from the option you want below:" +
                            "\n\t\t\t\t\t\t\t\t1) View accounts" +
                            "\n\t\t\t\t\t\t\t\t2) Create an account" +
                            "\n\t\t\t\t\t\t\t\t3) Join an account" +
                            "\n\t\t\t\t\t\t\t\t4) Logout" +
                            "\n\t\t\t\t\t\t\t==============================================" +
                            "\n\t\t\t\t\t\t\tEnter Selection:");


            //Use scanner to get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();


            //Switch statement to decide what page to go to.
            switch (entry){


                case "1":
                    Screen as = new AccountScreen();
                    as.runScreen(a);
                    message = null;
                    break;
                case "2":
                    Screen aaas = new AddAnAccountScreen();
                    aaas.runScreen(a);
                    message = null;
                    break;
                case "3":

                    Screen jaas = new JoinAnAccountScreen();
                    jaas.runScreen(a);
                    message = null;
                    break;
                case "4":
                    quit = true;
                    break;
                default:
                    message = "That was an invalid selection.";
            }//end switch statement


        }//end while loop


    }//end method

    @Override
    public void runScreen() {} //Do Not Use

    @Override
    public void runScreen(Customer a) {
        CustomerScreen.runAccountScreen(a);
    }

    @Override
    public void runScreen(Customer a, Accounts b) {} //Do Not Use
}// end class
