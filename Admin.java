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
        Functions.printMenu(menuArr);

        SalesSystem.choice = Functions.prompt();
        switch (SalesSystem.choice){
            case 1: createTables(); break;
            case 2: dropTables(); break;
            case 3: loadDatafile(); break;
            case 4: showRecNum(); break;
            default: break;
        }

    }


    //Read data from given text files
    public static boolean readData(){
        //Read data files
        for(int i=0; i<SqlOp.loadDatafileArr.length; ++i){
            Functions.loadDatafiles(SqlOp.datafileArr[i]);
        }

        return true;
    }

    public static void createTables(){
        SqlOp.createTable();
        readData();


        //For debugging
        SqlOp.printAllTable();
    }

    public static void dropTables(){
        SqlOp.dropTables();
    }

    public static void loadDatafile(){

    }

    public static int showRecNum(){

        return 0;

    }




}

