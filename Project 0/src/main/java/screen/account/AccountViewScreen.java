package screen.account;

import banking.Accounts;
import banking.Customer;
import screen.Screen;

import java.text.NumberFormat;
import java.util.Scanner;

public class AccountViewScreen extends Screen {





    private static void runAccountViewScreen(Customer a, Accounts b ){
        boolean quit = false;
        String message = null;

        while(!quit){
            Screen.clearScreen();
            Screen.printMessage(message);
            System.out.print("\t\t\t\t\t\t\tAccount number: "+ b.getAccount_id() +" has a balance of "+ NumberFormat.getCurrencyInstance().format(b.getBalance())+"\n" +
                    "\t\t\t\t\t\t\tInsert the number for which option you would like below.\n" +
                    "\t\t\t\t\t\t\t========================================================\n" +
                    "\t\t\t\t\t\t\t1) Deposit money\n" +
                    "\t\t\t\t\t\t\t2) Withdraw money\n" +
                    "\t\t\t\t\t\t\tB) to go back\n" +
                    "\t\t\t\t\t\t\t========================================================\n" +
                    "\t\t\t\t\t\t\tEnter selection: ");

            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            switch (entry){
                case "1":
                    message = null;
                    DepositScreen ds = new DepositScreen();
                    ds.runScreen(a,b);
                    break;
                case "2":
                    message = null;
                    WithdrawScreen ws = new WithdrawScreen();
                    ws.runScreen(a,b);
                    break;
                case "B":
                case "b":
                    quit = true;
                    break;
                default:
                    message = "Invalid selection. Try 1,2 or B";
                    break;

            }

        }//end while loop

    }

















    @Override
    public void runScreen() {

    }

    @Override
    public void runScreen(Customer a) {

    }

    @Override
    public void runScreen(Customer a, Accounts b) {
        AccountViewScreen.runAccountViewScreen(a,b);

    }
}
