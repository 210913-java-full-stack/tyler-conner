package screen.customer;

import banking.Accounts;
import banking.Customer;
import list.MyArrayList;
import screen.Screen;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.Scanner;

public class AddAnAccountScreen extends Screen {

    private static double enterBalance() {

        String message = null;
        boolean quit = false;
        boolean valid = true;
        double result = 0;
        while(!quit){
            Screen.clearScreen();
            Screen.printMessage(message);
            System.out.print("\t\t\t\t\t\t\tEnter the amount you want to start your account with: ");

            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            MyArrayList<Character> splitEntry = new MyArrayList<>();
            for (int i = 0; i < entry.length()-1; i++){
                splitEntry.add(entry.charAt(i));
            }

            Iterator<Character> list = splitEntry.iterator();
            while (list.hasNext()){
                if(Character.isAlphabetic(list.next())) {
                    valid = false;
                    message = "Your entry did not contain only digits.";
                    break;
                }
            }//end while loop iterator

            if(valid){
                quit = true;
                result = Double.parseDouble(entry);
            }
        }//end while loop

        return result;
    }//end method


    private static String getAccountType() {
        String message = null;
        boolean quit = false;
        boolean valid = true;
        String result = null;
        while(!quit){
            Screen.clearScreen();
            Screen.printMessage(message);
            System.out.print("\t\t\t\t\t\t\tEnter 'c' for checking account or 's' for savings account: ");

            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            if (entry.equals("S") || entry.equals("s")){
                result = "Savings";
                quit = true;
            } else if (entry.equals("C") || entry.equals("c")){
                result = "Checking";
                quit = true;
            } else {
                message = "You did not select a valid entry.";
            }

        }//end while loop
        return result;
    }

    private static void runAddAnAccountScreen(Customer a) {
        double balance = AddAnAccountScreen.enterBalance();
        String acctType = AddAnAccountScreen.getAccountType();


        int lastAccountID = 0;

        try {
            Connection conn = ConnectionManager.getConnection();
            String sql = "SELECT * FROM accounts;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                lastAccountID = rs.getInt("account_id");
                rs.getRow();
            }

            lastAccountID++;
            int userID = a.getId();


            String sql2 = "INSERT INTO accounts_customers (account_id, customer_id) VALUES (?, ?); ";
            String sql3 = "INSERT INTO accounts (account_id, balance, type_account, created_by) VALUES (?, ?, ?, ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql2);
            PreparedStatement pstmt2 = conn.prepareStatement(sql3);

            pstmt.setInt(1,lastAccountID);
            pstmt.setInt(2, userID);


            pstmt2.setInt(1, lastAccountID);
            pstmt2.setDouble(2, balance);
            pstmt2.setString(3, acctType);
            pstmt2.setInt(4, userID);



            pstmt2.executeUpdate();
            pstmt.executeUpdate();


            Accounts newAcct = new Accounts(lastAccountID,balance,acctType,userID);

            a.getAccounts().add(newAcct);




        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void runScreen() {

    }

    @Override
    public void runScreen(Customer a) {
        AddAnAccountScreen.runAddAnAccountScreen(a);
    }

    @Override
    public void runScreen(Customer a, Accounts b) {

    }
}
