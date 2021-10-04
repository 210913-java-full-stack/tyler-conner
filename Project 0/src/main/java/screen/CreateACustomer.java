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
        String messageFN = null;
        boolean quitFN = false;

        String result = null;
        while(!quitFN){
            boolean valid = true;
            Screen.clearScreen();
            Screen.printMessage(messageFN);
            System.out.println("\t\t\t\t\t\t\tUsername can be between 4 and 50 characters\n\t\t\t\t\t\t\tOnly use alphabetical characters.");
            System.out.print("\t\t\t\t\t\t\tEnter a username: ");

            //Use scanner to get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

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
            }

            //validate entry for only alphabetical
            if (valid) {
                Iterator<Character> list = splitEntry.iterator();
                while (list.hasNext()){
                    if(!Character.isAlphabetic(list.next())){
                        valid = false;
                        messageFN = "Your username can only obtain characters from the alphabet.";
                    }//end if
                }//end while loop iterator
            }



            if(valid) {

                try {
                    Connection conn = ConnectionManager.getConnection();
                    String sql = "SELECT username FROM users WHERE username = ?;";
                    PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.setString(1, entry);
                    ResultSet rs = pstm.executeQuery();

                    if (rs.next()) { valid = false; messageFN = "Username is already in use";}

                } catch (SQLException | IOException e) {
                    valid = true;
                }
            }
            if(valid){

                quitFN = true;
                result = entry;
            }
        }// end while loop
        return result;
    }

    private static String enterPassword() {
        String messageFN = null;
        boolean quitFN = false;

        String result = null;
        while(!quitFN){
            boolean valid = true;
            Screen.clearScreen();
            Screen.printMessage(messageFN);
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


            if(valid){

                quitFN = true;
                result = entry;
            }
        }// end while loop
        return result;
    }

    private static String enterFirstName() {
        String messageFN = null;
        boolean quitFN = false;

        String result = null;
        while(!quitFN){
            boolean valid = true;
            Screen.clearScreen();
            Screen.printMessage(messageFN);
            System.out.print("\t\t\t\t\t\t\tEnter your first name: ");

            //Use scanner to get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            //create a list to put all characters of user's input
            MyArrayList<Character> splitEntry = new MyArrayList<>();
            for (int i = 0; i < entry.length()-1; i++){
                splitEntry.add(entry.charAt(i));
            }

            Iterator<Character> list = splitEntry.iterator();
            while (list.hasNext()){
                if(!Character.isAlphabetic(list.next())){
                    valid = false;
                }//end if
            }//end while loop iterator
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
        String messageFN = null;
        boolean quitFN = false;
        boolean valid = true;
        String result = null;
        while(!quitFN){
            Screen.clearScreen();
            Screen.printMessage(messageFN);
            System.out.print("\t\t\t\t\t\t\tEnter your last name: ");

            //Use scanner to get user input
            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            //create a list to put all characters of user's input
            MyArrayList<Character> splitEntry = new MyArrayList<>();
            for (int i = 0; i < entry.length()-1; i++){
                splitEntry.add(entry.charAt(i));
            }

            Iterator<Character> list = splitEntry.iterator();
            while (list.hasNext()){
                if(!Character.isAlphabetic(list.next())){
                    valid = false;
                }//end if
            }//end while loop iterator
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
        String username = CreateACustomer.enterUsername();
        String password = CreateACustomer.enterPassword();
        String firstName = CreateACustomer.enterFirstName();
        String lastName = CreateACustomer.enterLastName();

        int userID = 0;

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

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Failure to create an account");
        } catch (IOException t) {
           t.printStackTrace();
        }

        try{
            Connection conn = ConnectionManager.getConnection();
            String twoSQL = "SELECT user_id FROM users WHERE username = ?;";
            PreparedStatement pstm2 = conn.prepareStatement(twoSQL);
            pstm2.setString(1, username);
            ResultSet rs2 = pstm2.executeQuery();
            rs2.next();
            userID = rs2.getInt("user_id");



        } catch (SQLException | IOException e) {
            System.out.println("Failure to create a Customer object, and see the Customer Screen");
        }
        //get last account id that was used then create an account for the new user.
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

            String sql2 = "INSERT INTO accounts_customers (account_id, customer_id) VALUES (?, ?); ";
            String sql3 = "INSERT INTO accounts ( balance, type_account, created_by) VALUES ( 0, \"Checking\", ?);";
            PreparedStatement pstmt = conn.prepareStatement(sql2);
            PreparedStatement pstmt2 = conn.prepareStatement(sql3);
            lastAccountID++;


            pstmt.setInt(1, lastAccountID);
            pstmt.setInt(2, userID);
            pstmt2.setInt(1, userID);


            pstmt2.executeUpdate();
            pstmt.executeUpdate();

            MyArrayList<Accounts>  accounts = new MyArrayList<Accounts>();
            Accounts newAcct = new Accounts(lastAccountID, 0, "Checking", userID);
            accounts.add(newAcct);
            Customer newCustomer = new Customer(userID, firstName, lastName,  accounts);

            CustomerScreen cs = new CustomerScreen();

            cs.runScreen(newCustomer);

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