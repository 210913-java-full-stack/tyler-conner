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
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TransferScreen extends Screen {


    private static int enterAccountId() {
        boolean quit = false;
        String message = null;
        int transAcctNumber = 0;
        while (!quit){
            Screen.clearScreen();
            Screen.printMessage(message);
            System.out.print("\t\t\t\t\t\t\tEnter the account number you want to transfer money to :");

            Scanner sc = new Scanner(System.in);
            String entry = sc.next();

            Pattern p = Pattern.compile("9[0-9]{5}");
            Matcher m = p.matcher(entry);
            boolean bool = m.matches();
            if (bool){
                transAcctNumber = Integer.parseInt(entry);

                try {
                    Connection conn = ConnectionManager.getConnection();
                    String sql = "SELECT * FROM accounts WHERE account_id = ?;";
                    PreparedStatement pstm = conn.prepareStatement(sql);
                    pstm.setInt(1,transAcctNumber);
                    ResultSet rs = pstm.executeQuery();

                    rs.next();

                } catch (SQLException | IOException e) {
                    message = "This account number not in use. Try a valid account number.";
                    continue;
                }// end try/catch block
                quit = true;




            } else {
                message = "Invalid format for an account number.";
            }//end if/else
    }// end while loop
        return transAcctNumber;
    }

    private static void runTransferScreen(Customer a, Accounts b){
        int transAcctNum = TransferScreen.enterAccountId();

        boolean quit = false;
        String message = null;

        while (!quit){
            Screen.clearScreen();
            Screen.printMessage(message);
            System.out.print("\t\t\t\t\t\t\tEnter the amount you want to transfer: ");

            Scanner sc = new Scanner(System.in);
            String amntTrans = sc.next();

            Pattern p = Pattern.compile("[0-9]+[.][0-9]{2}|[0-9]+");
            Matcher m = p.matcher(amntTrans);
            boolean bool = m.matches();



            if (bool){
                double transfer = Double.parseDouble(amntTrans);


                if (transfer <= b.getBalance()){


                try {
                    Connection conn = ConnectionManager.getConnection();
                    String subtractSql = "UPDATE accounts SET balance = balance - ? WHERE account_id = ?;";
                    PreparedStatement subPS = conn.prepareStatement(subtractSql);
                    subPS.setDouble(1, transfer);
                    subPS.setInt(2,b.getAccount_id());
                    subPS.executeUpdate();
                    b.setBalance(b.getBalance() - transfer);



                    String addSQL = "UPDATE accounts SET balance = balance + ? WHERE account_id = ?;";
                    PreparedStatement addPS = conn.prepareStatement(addSQL);
                    addPS.setDouble(1, transfer);
                    addPS.setInt(2,transAcctNum);
                    addPS.executeUpdate();
                    for (Accounts x : a.getAccounts()){
                        if (x.getAccount_id() == transAcctNum){
                            x.setBalance(x.getBalance() + transfer);
                        }
                    }// end for loop

                    String transactionSQL = "INSERT INTO transactions (amount, acct_from, acct_to, trans_type) VALUES (?,?,?,\"Transfer\");";
                    PreparedStatement transPS = conn.prepareStatement(transactionSQL);
                    transPS.setDouble(1,transfer);
                    transPS.setInt(2,b.getAccount_id());
                    transPS.setInt(3, transAcctNum);
                    transPS.executeUpdate();


                    quit = true;





                }catch (SQLException | IOException e){
                    e.printStackTrace();
                }


                } else {
                    message = "You cannot transfer more than your account balance.";
                }
            } else {
                message = "You must insert an integer or  a number with two decimal places.";
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
    TransferScreen.runTransferScreen(a,b);
    }
}
