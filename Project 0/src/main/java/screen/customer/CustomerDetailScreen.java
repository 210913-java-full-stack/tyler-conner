package screen.customer;

import banking.Accounts;
import banking.Customer;
import screen.Screen;

import java.util.Scanner;

public class CustomerDetailScreen extends Screen {

    private static void runCustomerDetailScreen (Customer a) {

    boolean quit = false;

    while (!quit) {
        Screen.clearScreen();


        System.out.print("\t\t\t\t\t\t\t==================" + a.getFirstName()+ " " + a.getLastName() + "'s account details ==================" +
                "\n\t\t\t\t\t\t\tFirst name: " + a.getFirstName() + "\tLast name: " + a.getLastName() +
                "\n\t\t\t\t\t\t\t\tStreet Address: " + a.getAddress() +
                "\n\t\t\t\t\t\t\t\tCity: " + a.getCity() +
                "\n\t\t\t\t\t\t\t\tState: " + a.getState() +
                "\n\t\t\t\t\t\t\t\tZip: " + a.getZip() +
                "\n\n\t\t\t\t\t\t\t\tEnter B to go back." +
                "\n\t\t\t\t\t\t\t==============================================");

        Scanner sc = new Scanner(System.in);
        String entry = sc.next();

        if (entry.equals("b")||entry.equals("B")){
            quit = true;
        }
    }//end while loop
    }// end method













    @Override
    public void runScreen() {

    }

    @Override
    public void runScreen(Customer a) {
        CustomerDetailScreen.runCustomerDetailScreen(a);
    }

    @Override
    public void runScreen(Customer a, Accounts b) {

    }
}
