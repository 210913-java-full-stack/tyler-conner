package screen.customer;

import banking.Accounts;
import banking.Customer;
import list.MyArrayList;
import screen.Screen;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Scanner;

public class JoinAnAccountScreen extends Screen {

    private static int enterAcctNumber(Customer a) {
        String message = null;
        boolean quit = false;

        int result = 0;

        while(!quit) {
            boolean valid = true;
            Screen.clearScreen();
            Screen.printMessage(message);
            System.out.print("\t\t\t\t\t\t\tEnter the account number you wish to join: ");

            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            for (Accounts x:a.getAccounts()) {
                if (x.getAccount_id() == Integer.parseInt(entry)){
                    valid = false;
                    message = "You are already in that account";
                }
            }

            MyArrayList<Character> splitEntry = new MyArrayList<>();
            for (int i = 0; i < entry.length()-1; i++){
                splitEntry.add(entry.charAt(i));
            }

            Iterator<Character> list = splitEntry.iterator();
            while (list.hasNext()){
                if(!Character.isDigit(list.next())) {
                    valid = false;
                    message = "Your entry should only contain digits.";
                }
            }//end while loop iterator

            if(valid){
                quit = true;
                result = Integer.parseInt(entry);
            }
        }// end while loop
        return result;
    }


    private static boolean enterPassword(int acctNum) {





            Screen.clearScreen();

            System.out.print("\t\t\t\t\t\t\tEnter the password of the account you want to join: ");

            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            try {
                Connection conn = ConnectionManager.getConnection();
                String sql = "SELECT * " +
                        "FROM customers c " +
                        "JOIN accounts_customers ac ON c.customer_id = ac.customer_id " +
                        "JOIN accounts a ON ac.account_id = a.account_id " +
                        "JOIN users u ON c.customer_id = u.user_id " +
                        "WHERE a.account_id = ? AND u.password = ?;";

                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setInt(1, acctNum);
                pstm.setString(2, entry);

                ResultSet rs = pstm.executeQuery();

                if (rs.next()) {
                    return true;
                }else {
                    return false;
                }





            } catch (SQLException | IOException e) {
                System.out.println(" You enter an incorrect password.");

                return false;
            }

    }





    private static void runJoinAnAccountScreen(Customer a) {
        int acctNum = JoinAnAccountScreen.enterAcctNumber(a);

        boolean quit = false;
        while (!quit){
            boolean didJoin = JoinAnAccountScreen.enterPassword(acctNum);

            if (didJoin) {
                try {
                    Connection conn = ConnectionManager.getConnection();
                    String sql = "INSERT INTO accounts_customers (account_id, customer_id) VALUES (?,?);";
                    PreparedStatement pstmt = conn.prepareStatement(sql);
                    pstmt.setInt(1, acctNum);
                    pstmt.setInt(2, a.getId());

                    pstmt.executeUpdate();


                    Screen.clearScreen();


                    String sql2 = "SELECT * FROM accounts WHERE account_id = ?;";
                    PreparedStatement pstmt2 = conn.prepareStatement(sql2);
                    pstmt2.setInt(1, acctNum);
                    ResultSet rs = pstmt2.executeQuery();

                    rs.next();
                    double getBalance = rs.getDouble("balance");
                    String getAcctType = rs.getString("type_account");

                    Accounts newAcct = new Accounts(acctNum,getBalance,getAcctType);

                    a.getAccounts().add(newAcct);







                    quit = true;


                } catch (SQLException | IOException throwables) {
                    throwables.printStackTrace();
                    System.out.println("Error in joining the account");
                }



            } else {
            System.out.print("\n\t\t\t\t\t\t\t!!!!!!!!!!\n\n\t\t\t\t\t\t\tThe password you entered is incorrect.\n\t\t\t\t\t\t\tWould you like to try joining that account again?\n\t\t\t\t\t\t\t=========================\n\n\n\t\t\t\t\t\t\tEnter (Y) Yes or (N) No: ");
            Scanner scException = new Scanner(System.in);
            String scanEntryException = scException.next();
            switch (scanEntryException) {
                case "Y":
                case "y":

                    break;
                case "N":
                case "n":
                    quit = true;
                    break;

            }
        }
        }// end while

    }



    @Override
    public void runScreen() {

    }

    @Override
    public void runScreen(Customer a) {
    JoinAnAccountScreen.runJoinAnAccountScreen(a);
    }

    @Override
    public void runScreen(Customer a, Accounts b) {

    }
}
