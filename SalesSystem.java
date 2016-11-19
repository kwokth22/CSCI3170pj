import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.Scanner;


public class SalesSystem {


    public static void main(String[] args){

        while(!isEnd){
            System.out.println("1: create");
            System.out.println("2: drop");
            System.out.println("3: Quit");
            System.out.print("Input: ");

            int n = sc.nextInt();

            switch (n){
                case 1: SqlOp.createTable(); break;
                case 2: SqlOp.dropTables(); break;
                case 3: isEnd = true; break;
                default: break;
            }
        }
    }



    //Global variables
    public static Scanner sc = new Scanner(System.in);

    public static int choice = -1;
    public static boolean isEnd = false;

/*
    public static void main(String[] args) {
        int c = -1;

        if(SqlOp.conn == null) {
            //For debugging
            System.out.println("Cannot create connection");
            return;
        }

        //For debugging
        System.out.println("Conn created");

        SqlOp.initTableMap();


        while(!isEnd){
            printMainMenu();

            switch (choice){
                case 1: new Admin(); break;
                case 2: new Salesperson(); break;
                case 3: new Manager(); break;
                case 4: isEnd = true; break;
                default: break;
            }
        }

    }


    public static void printMainMenu(){
        System.out.println("-----Main menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Operation for administrator");
        System.out.println("2. Operation for salesperson");
        System.out.println("3. Operation for manager");
        System.out.println("4. Exit this program");
        choice = getUserChoice();
    }


    public static int getUserChoice(){
        System.out.print("Enter Your Choice: ");
        return sc.nextInt();
    }

*/



}

