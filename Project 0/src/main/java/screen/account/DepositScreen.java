package screen.account;

import banking.Accounts;
import banking.Customer;
import screen.Screen;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepositScreen extends Screen {


    private static void runDepositScreen(Customer a,Accounts b) {
        boolean quit = false;
        String message = null;

        while (!quit) {

            Screen.clearScreen();
            Screen.printMessage(message);
            System.out.print("\t\t\t\t\t\t\tEnter the amount you want to deposit: ");

            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            Pattern p = Pattern.compile("[0-9]+[.][0-9]{2}|[0-9]+");
            Matcher m = p.matcher(entry);
            boolean bool = m.matches();



            if (bool){
                quit = true;
                double deposit = Double.parseDouble(entry);
                b.setBalance(b.getBalance() + deposit);


                try{
                    Connection conn = ConnectionManager.getConnection();
                    String sql = "UPDATE accounts a SET a.balance = a.balance + ? WHERE a.account_id = ?;";
                    PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.setDouble(1,deposit);
                    pstm.setInt(2,b.getAccount_id());

                    pstm.executeUpdate();

                    String sql2 = "INSERT INTO transactions (amount, acct_to, trans_type) VALUES (?, ?, \"Deposit\")";
                    PreparedStatement pstmTwo = conn.prepareStatement(sql2);
                    pstmTwo.setDouble(1, deposit);
                    pstmTwo.setInt(2,b.getAccount_id());

                    pstmTwo.executeUpdate();

                } catch (SQLException | IOException e){
                    e.printStackTrace();
                }


            }else {
                message = "You need to enter an integer or a double with two decimal places.";
            }


        }
    }

    @Override
    public void runScreen() {

    }

    @Override
    public void runScreen(Customer a) {

    }

    @Override
    public void runScreen(Customer a, Accounts b) {
    DepositScreen.runDepositScreen(a,b);
    }
}
