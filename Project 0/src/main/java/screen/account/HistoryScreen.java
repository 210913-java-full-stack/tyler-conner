package screen.account;

import banking.Accounts;
import banking.Customer;
import screen.Screen;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.Scanner;

public class HistoryScreen extends Screen {

    private static void runHistoryScreen(Customer a, Accounts b){
        boolean quit = false;
        String message = null;

        while (!quit){
            Screen.clearScreen();
            Screen.printMessage(message);
            System.out.println("\t\t\t\t\t\t\t=========== " + b.getAccount_id() + " account history ==========" );
            try {
                Connection conn = ConnectionManager.getConnection();
                String sql = "SELECT * FROM transactions WHERE acct_from = ? OR acct_to = ?";
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setInt(1,b.getAccount_id());
                pstm.setInt(2,b.getAccount_id());
                ResultSet rs = pstm.executeQuery();

                while(rs.next()){
                    if(rs.getString(5).equals("Transfer")){
                        System.out.println("\t\t\t\t\t\t\t" + rs.getInt(3) + " transferred " + NumberFormat.getCurrencyInstance().format(rs.getDouble(2)) + " to account number : " + rs.getInt(4));
                    } else if (rs.getString(5).equals("Withdraw")){
                        System.out.println("\t\t\t\t\t\t\tYou withdrew " + NumberFormat.getCurrencyInstance().format(rs.getDouble(2)) + " from " + rs.getInt(3));
                    } else if (rs.getString(5).equals("Deposit")){
                        System.out.println("\t\t\t\t\t\t\tYou deposited " + NumberFormat.getCurrencyInstance().format(rs.getDouble(2)) + " into " + rs.getInt(4));
                    }
                }


            } catch (SQLException | IOException e){
                e.printStackTrace();
            }

            System.out.print("\t\t\t\t\t\t\tEnter \"B\" to go back: ");
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            switch (entry){
                case "B":
                case "b":
                    quit = true;
                    break;
                default:
                    message = "Invalid selection. You have to insert B to go back.";
            }


        }// end while loop
    }


    @Override
    public void runScreen() {

    }

    @Override
    public void runScreen(Customer a) {

    }

    @Override
    public void runScreen(Customer a, Accounts b) {
        HistoryScreen.runHistoryScreen(a,b);
    }
}
