package banking;

import list.MyArrayList;

import java.util.Iterator;

public class Customer {
    // Variables
    private int id;
    private String firstName;
    private String lastName;

    private double totalBalance;
    private MyArrayList<Accounts> accounts;




    // Constructor
    public Customer(int id, String firstName, String lastName, MyArrayList<Accounts> accounts) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;

        double total = 0;
        this.accounts = accounts;


        Iterator<Accounts> b = accounts.iterator();
        while(b.hasNext()) {
            total += b.next().balance;
        }
        totalBalance = total;


    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(MyArrayList<Accounts> a) {
        double sum = 0;
        for (Accounts x : a){
            sum = sum + x.balance;
        }
        this.totalBalance = sum;
    }

    public MyArrayList<Accounts> getAccounts() {
        return accounts;
    }

    public void setAccounts(MyArrayList<Accounts> accounts) {
        this.accounts = accounts;
    }


// Getters and Setters



}