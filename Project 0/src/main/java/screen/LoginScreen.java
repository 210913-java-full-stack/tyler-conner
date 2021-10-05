package screen;

import banking.Accounts;
import banking.Customer;
import list.MyArrayList;
import screen.account.AccountScreen;
import screen.customer.CustomerScreen;
import utils.ConnectionManager;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class LoginScreen extends Screen {


    private static void runLoginScreen() throws InterruptedException {
        //set variables
        String message = null;
        boolean quit = false;

        //while loop to run the screen as long as needed.
        while(!quit) {
            //clear screen
            Screen.clearScreen();
            //print message if not null
            Screen.printMessage(message);

            //print login Screen and take the users input
            System.out.print("\t\t\t\t\t\t\t=====Login Screen =====\n\t\t\t\t\t\t\tEnter your username :");
            Scanner sc = new Scanner(System.in);
            String username = sc.next();
            System.out.print("\t\t\t\t\t\t\tEnter your password: ");
            String password = sc.next();

            //try block to connect to database and check to see if there is a user with the username and password  that was inputted.

            try {
                Connection conn = ConnectionManager.getConnection();
                String sql = "SELECT c.customer_id, c.first_name, c.last_name, a.account_id, a.balance , a.type_account , a.created_by, u.username, u.password " +
                                "FROM customers c " +
                                "JOIN accounts_customers ac ON c.customer_id = ac.customer_id " +
                                "JOIN accounts a ON ac.account_id = a.account_id " +
                                "JOIN users u ON c.customer_id = u.user_id " +
                                "WHERE u.username = ? AND u.password = ?;";
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setString(1, username);
                pstm.setString(2, password);
                ResultSet rs = pstm.executeQuery();

                //move cursor to next line
                rs.next();

                // grab the id, first name, and last name from the Result Set
                int tempCustId = rs.getInt("customer_id");
                String tempFirstName = rs.getString("first_name");
                String tempLastName = rs.getString("last_name");

                //Create another Result set to loop through and grab the user's accounts info
                ResultSet rs2 = pstm.executeQuery();

                //Create an Array list for the Account object to be added to
                MyArrayList<Accounts> accountArray = new MyArrayList<>();

                // while loop through the result set
                while (rs2.next()) {
                    //Grab the account info
                    int tempAcctId = rs2.getInt("account_id");
                    double tempBalance = rs2.getDouble("balance");
                    String tempAcctType = rs2.getString("type_account");
                    int tempCreatedBy = rs2.getInt("created_by");

                    //move the cursor to the next row.
                    rs2.getRow();

                    //create an account with the info grabbed from above.
                    Accounts tempAcct = new Accounts(tempAcctId, tempBalance, tempAcctType, tempCreatedBy);
                    //add the new acct to the Array list.
                    accountArray.add(tempAcct);
                }// end while loop

                //Create a new customer object with the info grabed from 1st Result set and the Account Array.
                Customer newCust = new Customer(tempCustId, tempFirstName, tempLastName,  accountArray);

                //Create a customer screen and call runScreen with the Customer object as a paramater.
                Screen customerScreen = new CustomerScreen();
                customerScreen.runScreen(newCust);
                //Set quit to true so when the user logs out of the customer screen the user will be sent to the main menu.
                quit = true;



                //catch and handle the exception when the connection to the database times out.
            } catch (SQLNonTransientConnectionException e){

                Screen.clearScreen();
                System.out.println("\t\t\t\t\t\t\tThe connection to the database has timed out.\n\t\t\t\t\t\t\tYou will need to restart the program.");
                Thread.sleep(5000);
                quit = true;


                //catch and handle when the user input did not match username and password for a customer in the database
            } catch (SQLException | IOException e) {
                System.out.print("\n\t\t\t\t\t\t\t!!!!!!!!!!\n\n\t\t\t\t\t\t\tYour username or password is incorrect.\n\t\t\t\t\t\t\tWould you like to try logging in again?\n\t\t\t\t\t\t\t=========================\n\n\n\t\t\t\t\t\t\tEnter (Y) Yes or (N) No: ");
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
                    default:
                        message = "Invalid entry! Try logging in again.";
                        break;
                }
            }
        }//end of while loop

    }

    @Override
    public void runScreen() throws InterruptedException { LoginScreen.runLoginScreen(); }

    @Override
    public void runScreen(Customer a) {} //Do Not Use

    @Override
    public void runScreen(Customer a, Accounts b) {} //Do Not Use
}
