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


    private static void runLoginScreen() {
        String message = null;
        boolean quit = false;
        while(!quit) {
            Screen.clearScreen();

            Screen.printMessage(message);

            System.out.print("\t\t\t\t\t\t\t=====Login Screen =====\n\t\t\t\t\t\t\tEnter your username :");
            Scanner sc = new Scanner(System.in);
            String username = sc.next();
            System.out.print("\t\t\t\t\t\t\tEnter your password: ");
            String password = sc.next();


                    // uncomment below for easier testing
                    // V
                    // V
//             String username = "test";
//             String password = "password";

            try {
                Connection conn = ConnectionManager.getConnection();
                String sql = "SELECT c.customer_id, c.first_name, c.last_name, c.address, c.city, c.state, c.zip, a.account_id, a.balance , a.type_account , a.created_by, u.username, u.password " +
                                "FROM customers c " +
                                "JOIN accounts_customers ac ON c.customer_id = ac.customer_id " +
                                "JOIN accounts a ON ac.account_id = a.account_id " +
                                "JOIN users u ON c.customer_id = u.user_id " +
                                "WHERE u.username = ? AND u.password = ?;";
                PreparedStatement pstm = conn.prepareStatement(sql);
                pstm.setString(1, username);
                pstm.setString(2, password);
                ResultSet rs = pstm.executeQuery();

                rs.next();


                int tempCustId = rs.getInt("customer_id");
                String tempFirstName = rs.getString("first_name");
                String tempLastName = rs.getString("last_name");
                String tempAddress = rs.getString("address");
                String tempCity = rs.getString("city");
                String tempState = rs.getString("state");
                int tempZip = rs.getInt("zip");


                ResultSet rs2 = pstm.executeQuery();

                MyArrayList<Accounts> accountArray = new MyArrayList<>();



                while (rs2.next()) {
                    int tempAcctId = rs2.getInt("account_id");
                    double tempBalance = rs2.getDouble("balance");
                    String tempAcctType = rs2.getString("type_account");
                    int tempCreatedBy = rs2.getInt("created_by");

                    rs2.getRow();
//                    System.out.println(tempAcctId);
//                    System.out.println(tempBalance);
                    Accounts tempAcct = new Accounts(tempAcctId, tempBalance, tempAcctType, tempCreatedBy);
                    accountArray.add(tempAcct);


                }

                Customer newCust = new Customer(tempCustId, tempFirstName, tempLastName, tempAddress,tempCity,tempState,  tempZip, accountArray);
                Screen customerScreen = new CustomerScreen();
                customerScreen.runScreen(newCust);
                quit = true;




            } catch (SQLNonTransientConnectionException e){
                System.out.println("\t\t\t\t\t\t\tThe connection to the database has timed out.\n\t\t\t\t\t\t\tYou will need to restart the program.");
            } catch (SQLException | IOException e) {

//      e.printStackTrace();
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
    public void runScreen() { LoginScreen.runLoginScreen(); }

    @Override
    public void runScreen(Customer a) {} //Do Not Use

    @Override
    public void runScreen(Customer a, Accounts b) {} //Do Not Use
}
