import java.sql.SQLException;
import java.util.Scanner;
import java.io.*;
import java.text.ParseException;

public class Admin {
    public static void printAdminMenu() {
        System.out.println("-----Operations for administrator menu-----");
        System.out.println("What kinds of operation would you like to perform?");
        System.out.println("1. Create all tables");
        System.out.println("2. Delete all tables");
        System.out.println("3. Load from datafile");
        System.out.println("4. Show number of records in each table");
        System.out.println("5. Return to the main menu");
    }

    // public static int getUserChoice(){
    // 	System.out.print("Enter Your Choice: ");
    // 	Scanner sc = new Scanner(System.in);
    // 	int choice = sc.nextInt();
    // 	return choice;
    // }
    public static void loadData() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nType in the Source Data Folder Path: ");
        String path = sc.nextLine();
//        File currentDirectory = new File(new File(".").getAbsolutePath());
//        System.out.println(currentDirectory.getCanonicalPath());
//        System.out.println(currentDirectory.getAbsolutePath());
        //get current dir
//        System.out.println("Work DIR" + System.getProperty("user.dir"));
        loadCategory("/" + path);
        // loadManufacturer(path);
        // loadPart(path);
        // loadSalesperson(path);
        // loadTransaction(path);
    }

    public static void loadCategory(String path) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/category.txt"));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (!strLine.equals("")) {
                    String[] tokens = strLine.split("\t");
                    sqlOperation.insertCategory(tokens[1], tokens[2]);
                }

            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }

    }

    public static void loadManufacturer(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/manufacturer.txt"));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (!strLine.equals("")) {
                    String[] tokens = strLine.split("\t");
                    sqlOperation.insertManufacturer(tokens[1], tokens[2], tokens[3], tokens[4]);
                }

            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public static void loadPart(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/part.txt"));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (!strLine.equals("")) {
                    String[] tokens = strLine.split("\t");
                    sqlOperation.insertPart(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5], tokens[6], tokens[7]);
                }

            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public static void loadSalesperson(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/salesperson.txt"));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (!strLine.equals("")) {
                    String[] tokens = strLine.split("\t");
                    sqlOperation.insertSalesperson(tokens[1], tokens[2], tokens[3], tokens[4], tokens[5]);
                }

            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public static void loadTransaction(String path) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path + "/transation.txt"));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (!strLine.equals("")) {
                    String[] tokens = strLine.split("\t");
                    sqlOperation.insertTransaction(tokens[1], tokens[2], tokens[3], tokens[4]);
                }

            }
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
        }
    }

    public static void showNumOfRecord() {
        try {
            //Show Category
            int num = sqlOperation.showAllRecord("category");
            System.out.println("Table_category: " + num);
            //Show Manufacturer
            num = sqlOperation.showAllRecord("manufacturer");
            System.out.println("Table_manufacturer: " + num);
            //Show Part
            num = sqlOperation.showAllRecord("part");
            System.out.println("Table_part: " + num);
            //Show Salesperson
            num = sqlOperation.showAllRecord("salesperson");
            System.out.println("Table_salesperson: " + num);
            //Show Transaction
            num = sqlOperation.showAllRecord("transaction");
            System.out.println("Table_transaction: " + num);
        } catch (Exception e) {
            System.err.println("SQL Exception: " + e.getMessage());
        }
    }
}