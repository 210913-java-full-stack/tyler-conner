package list;

import banking.Accounts;

import java.util.Iterator;

public class TestArrayList {

    public static void main(String[] args) {
        MyArrayList<Accounts> accountList = new MyArrayList<Accounts>();
        Accounts accountsThree = new Accounts(3, 5667.77, "Checking", 1);
        Accounts accountTwo = new Accounts(2, 450.25, "Savings", 2);
        Accounts accountOne = new Accounts(0001, 205.45, "Checking", 3);

        Accounts addIndex = new Accounts(4,56.76, "checking", 4);

        accountList.add(accountOne);
        accountList.add(accountTwo);
        accountList.add(accountsThree);

        accountList.add(addIndex, 1);





        Iterator<Accounts> o = accountList.iterator();

        while (o.hasNext()) {
            System.out.println(o.next());
        }











    }












}
