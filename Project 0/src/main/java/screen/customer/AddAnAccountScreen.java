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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddAnAccountScreen extends Screen {

    private static double enterBalance() {
        //set variables
        String message = null;
        boolean quit = false;
        double result = 0;
        //while loop to run screen as long as needed.
        while(!quit){
            //clear screen
            Screen.clearScreen();
            //print message if not null
            Screen.printMessage(message);
            //get user to enter the amount the want to start the account with.
            System.out.print("\t\t\t\t\t\t\tEnter the amount you want to start your account with: ");

            //get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            //validate user input is a double.
            Pattern p = Pattern.compile("[0-9]+[.][0-9]{2}|[0-9]+");
            Matcher m = p.matcher(entry);
            boolean bool = m.matches();

            if(bool){
                quit = true;
                result = Double.parseDouble(entry);
            }else {
                message = "Not a valid entry. try an integer or a number with two decimal places.";
            }
        }//end while loop

        return result;
    }//end method


    private static String getAccountType() {
        //set variables
        String message = null;
        boolean quit = false;
        String result = null;

        // while loop to run screen as long as needed
        while(!quit){
            //clear screen
            Screen.clearScreen();
            //print message if not null
            Screen.printMessage(message);
            //get user to decide if they want the new account to be a checking or savings account.
            System.out.print("\t\t\t\t\t\t\tEnter 'c' for checking account or 's' for savings account: ");
            //get user input
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
        //set variable equal to the related method.
        double balance = AddAnAccountScreen.enterBalance();
        String acctType = AddAnAccountScreen.getAccountType();

        //declare lastAccountID
        int lastAccountID = 0;

        //try to get all data from account table to get the last account ID.
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

            // insert the account into the database
            String insertAccountSQL = "INSERT INTO accounts (account_id, balance, type_account, created_by) VALUES (?, ?, ?, ?);";
            PreparedStatement pstmt2 = conn.prepareStatement(insertAccountSQL);
            pstmt2.setInt(1, lastAccountID);
            pstmt2.setDouble(2, balance);
            pstmt2.setString(3, acctType);
            pstmt2.setInt(4, userID);
            pstmt2.executeUpdate();

            //connect the account to the customer with the junction table in database.
            String insertJunctionSQL = "INSERT INTO accounts_customers (account_id, customer_id) VALUES (?, ?); ";
            PreparedStatement pstmt = conn.prepareStatement(insertJunctionSQL);
            pstmt.setInt(1,lastAccountID);
            pstmt.setInt(2, userID);
            pstmt.executeUpdate();

            //create a new account obj
            Accounts newAcct = new Accounts(lastAccountID,balance,acctType,userID);
            //add it to the customer's array of accounts
            a.getAccounts().add(newAcct);



            //catch SQL Exceptions
        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
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
