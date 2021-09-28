package banking;

import java.text.NumberFormat;

public class Accounts {
    int account_id;
    double balance;
    String acctType;
    int createdBy;


    //Constructor
    public Accounts(int account_id, double balance, String acctType, int createdBy) {
        this.account_id = account_id;
        this.balance = balance;
        this.acctType = acctType;
        this.createdBy = createdBy;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }

    public Accounts() {

    }


    // Getter and Setters
    public int getAccount_id() { return account_id; }

    public void setAccount_id(int account_id) { this.account_id = account_id; }

    public double getBalance() { return balance; }

    public void setBalance(double balance) { this.balance = balance; }


    //Other Methods




    public String toString() {
        return "\t\t\t\t\t\t\t  " + account_id + "\tis a " +acctType +" account with a " +
                "balance of\t"+ NumberFormat.getCurrencyInstance().format(balance);
    }
}
