package screen;

import banking.Accounts;
import banking.Customer;
import list.MyArrayList;
import screen.customer.CustomerScreen;
import utils.ConnectionManager;

import java.io.IOException;
import java.sql.*;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.Callable;

public class CreateACustomer extends Screen{

    private static String enterUsername() {
        //set variables
        String messageFN = null;
        boolean quitFN = false;
        String result = null;

        // while loop to run this question until answered correctly.
        while(!quitFN){
            // valid needs to be set to true at the beginning of the loop.
            boolean valid = true;

            // clear screen
            Screen.clearScreen();

            //print message if not null
            Screen.printMessage(messageFN);

            //print screen to get user to enter a username
            System.out.println("\t\t\t\t\t\t\tUsername can be between 4 and 50 characters\n\t\t\t\t\t\t\tOnly use alphabetical characters.");
            System.out.print("\t\t\t\t\t\t\tEnter a username: ");

            //Use scanner to get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            //Validate the user input

            //create a list to put all characters of user's input
            MyArrayList<Character> splitEntry = new MyArrayList<>();
            for (int i = 0; i < entry.length()-1; i++){
                splitEntry.add(entry.charAt(i));
            }

            //validate the entry length
            if(entry.length() < 4 ) {
                valid = false;
                messageFN = "Your username is too short";
            } else if(entry.length() > 50) {
                valid = false;
                messageFN = "Your username is too long";
            }//end else if

            //validate entry for only alphabetical
            if (valid) {
                Iterator<Character> list = splitEntry.iterator();
                while (list.hasNext()){
                    if(!Character.isAlphabetic(list.next())){
                        valid = false;
                        messageFN = "Your username can only obtain characters from the alphabet.";
                    }//end if
                }//end while loop iterator
            }// end if


            //if still valid
            if(valid) {
                // try seeing if that username is in the database.
                try {
                    Connection conn = ConnectionManager.getConnection();
                    String sql = "SELECT username FROM users WHERE username = ?;";
                    PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.setString(1, entry);
                    ResultSet rs = pstm.executeQuery();
                    // if the resultset has something set valid to false, and let user know username is in use
                    if (rs.next()) { valid = false; messageFN = "Username is already in use";}

                    // catch the SQLException if the username has not been used before
                } catch (SQLException | IOException e) {
                    messageFN = null;
                }//end try/catch
            } //end if

            //if still valid set quit to true to end while loop and set result to the user input.
            if(valid){

                quitFN = true;
                result = entry;
            }//end if

        }// end while loop

        return result;
    }

    private static String enterPassword() {
        //set variables
        String messageFN = null;
        boolean quitFN = false;
        String result = null;

        //while loop to print screen as long as needed
        while(!quitFN){
            // valid must be set to true at the beginning of the loop.
            boolean valid = true;

            //clear screen
            Screen.clearScreen();

            //print message if not null
            Screen.printMessage(messageFN);
            //print string to get user to input  a password
            System.out.println("\t\t\t\t\t\t\tPassword can be between 8 and 50 characters");
            System.out.print("\t\t\t\t\t\t\tEnter a password: ");

            //Use scanner to get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            //validate the entry length
            if(entry.length() < 8 ) {
                valid = false;
                messageFN = "Your password is too short";
            } else if(entry.length() > 50) {
                valid = false;
                messageFN = "Your password is too long";
            }

            //if valid quit the loop and set result to the input
            if(valid){

                quitFN = true;
                result = entry;
            }
        }// end while loop
        return result;
    }

    private static String enterFirstName() {
        //set variables
        String messageFN = null;
        boolean quitFN = false;
        String result = null;

        //while loop to print screen as long as needed
        while(!quitFN){
            // valid must be set to true at the beginning of the loop.
            boolean valid = true;

            //clear screen
            Screen.clearScreen();

            //print message if not null
            Screen.printMessage(messageFN);
            //get user to input name
            System.out.print("\t\t\t\t\t\t\tEnter your first name: ");

            //Use scanner to get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            //create a list to put all characters of user's input
            MyArrayList<Character> splitEntry = new MyArrayList<>();
            for (int i = 0; i < entry.length()-1; i++){
                splitEntry.add(entry.charAt(i));
            }

            //validate that all characters are alphabetic
            Iterator<Character> list = splitEntry.iterator();
            while (list.hasNext()){
                if(!Character.isAlphabetic(list.next())){
                    valid = false;
                }//end if
            }//end while loop iterator

            //if valid set result to input and quit while loop; else set message to not a valid name.
            if(valid){
                quitFN = true;
                result = entry;
            } else {
                messageFN = "You did not enter a valid name";
            }
        }// end while loop
        return result;

    }//end method

    private static String enterLastName() {
        //set variables
        String messageFN = null;
        boolean quitFN = false;
        String result = null;

        // while loop to run the screen as many times as needed.
        while(!quitFN){
            // set valid to true at beginning of the while loop
            boolean valid = true;
            //clear screen
            Screen.clearScreen();
            //print message if not null
            Screen.printMessage(messageFN);
            //get user to enter their last name
            System.out.print("\t\t\t\t\t\t\tEnter your last name: ");

            //Use scanner to get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            //create a list to put all characters of user's input
            MyArrayList<Character> splitEntry = new MyArrayList<>();
            for (int i = 0; i < entry.length()-1; i++){
                splitEntry.add(entry.charAt(i));
            }
            //validate the last name is only alphabetic
            Iterator<Character> list = splitEntry.iterator();
            while (list.hasNext()){
                if(!Character.isAlphabetic(list.next())){
                    valid = false;
                }//end if
            }//end while loop iterator

            //if still vaild set result to user input and quit the while loop; else set message to invalid name.
            if(valid){
                quitFN = true;
                result = entry;
            } else {
                messageFN = "You did not enter a valid name";
            }
        }// end while loop
        return result;
    }//end method


    private static void runCreateACustomer() {
        //set variables to the method that returns the user input
        String username = CreateACustomer.enterUsername();
        String password = CreateACustomer.enterPassword();
        String firstName = CreateACustomer.enterFirstName();
        String lastName = CreateACustomer.enterLastName();

        //set a user id
        int userID = 0;

        //try to insert users data into user and customer table at the same time so the id number will match up.
        try {
            Connection conn = ConnectionManager.getConnection();
            String sql = "INSERT INTO users (username, password) VALUES (?, ?);";

            String sql2 = "INSERT INTO customers (first_name, last_name) VALUES (?,?);";
            PreparedStatement pstm = conn.prepareStatement(sql);
            PreparedStatement pstm2 = conn.prepareStatement(sql2);
            pstm.setString(1, username);
            pstm.setString(2, password);
            pstm2.setString(1, firstName);
            pstm2.setString(2, lastName);

            pstm.executeUpdate();
            pstm2.executeUpdate();

            //catch if there were problems creating an account
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failure to create an account");
        } catch (IOException t) {
           t.printStackTrace();
        }//end first try catch block

        //try grabbing the user id from the user we just created.

        try{
            Connection conn = ConnectionManager.getConnection();
            String twoSQL = "SELECT user_id FROM users WHERE username = ?;";
            PreparedStatement pstm2 = conn.prepareStatement(twoSQL);
            pstm2.setString(1, username);
            ResultSet rs2 = pstm2.executeQuery();
            rs2.next();
            //set user id to the user_id
            userID = rs2.getInt("user_id");


            //catch if there's a problem with the get the user id
        } catch (SQLException | IOException e) {
            System.out.println("Failure to create a Customer object, and see the Customer Screen");
        }// end second try catch block


        //get last account id that was used then create an account for the new user.
        // set last account id variable
        int lastAccountID = 0;

        //pull all from accounts; while rs.next() set  lastAccountID to the accountId so that you get the last account id
        try {

            Connection conn = ConnectionManager.getConnection();
            String sql = "SELECT * FROM accounts;";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                lastAccountID = rs.getInt("account_id");
                rs.getRow();
            }
            //increase accountid by 1
            lastAccountID++;

            //insert initial account for new user
            String insertAcct = "INSERT INTO accounts ( balance, type_account, created_by) VALUES ( 0, \"Checking\", ?);";
            PreparedStatement insertAcctPS = conn.prepareStatement(insertAcct);
            insertAcctPS.setInt(1, userID);
            insertAcctPS.executeUpdate();

            //connect account to the user with the junction table
            String insertJunction = "INSERT INTO accounts_customers (account_id, customer_id) VALUES (?, ?); ";
            PreparedStatement insertJunctionPS = conn.prepareStatement(insertJunction);
            insertJunctionPS.setInt(1, lastAccountID);
            insertJunctionPS.setInt(2, userID);
            insertJunctionPS.executeUpdate();

            //create a new custom list for the new user
            MyArrayList<Accounts>  accounts = new MyArrayList<Accounts>();
            //create the new user account and add it to the array list.
            Accounts newAcct = new Accounts(lastAccountID, 0, "Checking", userID);
            accounts.add(newAcct);
            //create a new customer with the info the user inputed.
            Customer newCustomer = new Customer(userID, firstName, lastName,  accounts);

            //create a new customer screen.
            CustomerScreen cs = new CustomerScreen();
            // call the runScreen witht he new customer obj as a parameter.
            cs.runScreen(newCustomer);


            //catch any SQLExceptions; there shouldn't be any.
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

    }//end method

    @Override
    public void runScreen() {  CreateACustomer.runCreateACustomer();}

    @Override
    public void runScreen(Customer a) {} //Won't use

    @Override
    public void runScreen(Customer a, Accounts b) {} //Won't use
}