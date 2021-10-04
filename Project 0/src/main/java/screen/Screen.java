package screen;

import banking.Accounts;
import banking.Customer;

public abstract class Screen implements ScreenInterface{
    @Override
    public abstract void runScreen() throws InterruptedException;

    @Override
    public abstract void runScreen(Customer a);

    @Override
    public abstract void runScreen(Customer a, Accounts b);

    public static void printMessage(String s) {
        if (s != null) {
            System.out.println("\t\t\t\t\t\t\t"+ s + "\n\n");
        }//end if

    }//end method

    public static void clearScreen() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }



}
