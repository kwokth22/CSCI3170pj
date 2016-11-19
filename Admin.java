import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

public class Admin{

    public static String[] menuArr = {
            "-----Operations for administrator menu-----",
            "What kinds of operation would you like to perform?",
            "1. Create all tables",
            "2. Delete all tables",
            "3. Load from datafile",
            "4. Show number of records in each table",
            "5. Return to the main menu"
    };

    Admin(){

        while(SalesSystem.choice!=5) {
            Functions.printMenu(menuArr);

            SalesSystem.choice = Functions.prompt();
            switch (SalesSystem.choice) {
                case 1:
                    createTables();
                    break;
                case 2:
                    dropTables();
                    break;
                case 3:
                    loadDatafile();
                    break;
                case 4:
                    showRecNum();
                    break;
                default:
                    break;
            }
        }

    }


    //Read data from given text files
    public static boolean readData(String folderPath){
        //Read data files
        for(int i=0; i<SqlOp.loadDatafileArr.length; ++i){
            Functions.loadDatafiles(folderPath, SqlOp.loadDatafileArr[i]);
        }


        //For debugging
        SqlOp.printAllSchema();
        SqlOp.printAllTable();

        return true;
    }

    public static void createTables(){
        SqlOp.createTable();

        //For debugging
        SqlOp.printAllSchema();
        SqlOp.printAllTable();
    }

    public static void dropTables(){
        SqlOp.dropTables();
    }

    public static void loadDatafile(){
        System.out.println("Type in the Source Data Folder Path: ");
        Scanner s = new Scanner(System.in);

        String folderPath = s.nextLine();

        readData(folderPath);

    }

    public static int showRecNum(){
        try {

            System.out.println("Number of records in each table:");
            for(int i=0; i<SqlOp.loadDatafileArr.length; ++i){
                System.out.println("Table_" + SqlOp.loadDatafileArr[i] + ": " + SqlOp.countRec(SqlOp.loadDatafileArr[i]));
            }

        } catch (Exception e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }

        return 0;

    }




}

