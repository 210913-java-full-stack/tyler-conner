import screen.LoginScreen;
import screen.MainScreen;
import screen.Screen;

import java.util.Scanner;

public class Driver {


    public static void main(String[] args) {
        Screen main = new MainScreen();
        try {
            main.runScreen();
        } catch (InterruptedException e){
            e.printStackTrace();//should never occur
        }
    }//end main method

}//end class
