package screen;

import banking.Accounts;
import banking.Customer;

public interface ScreenInterface {
    public void runScreen() throws InterruptedException;
    public  void runScreen(Customer a);
    public void runScreen(Customer a, Accounts b);
}
