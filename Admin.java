import javax.xml.transform.Result;
import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.*;

public class Admin {

	public static String[] menuArr = {
		"-----Operations for administrator menu-----",
		"What kinds of operation would you like to perform?",
		"1. Create all tables",
		"2. Delete all tables",
		"3. Load from datafile",
		"4. Show number of records in each table",
		"5. Return to the main menu"
	};

	Admin() {
		while (SalesSystem.choice != 5) {

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
	public static boolean readData(String folderPath) {
		//Read data files
		int loadData = 0;
		boolean loadDataSuccess = false;
		for (int i = 0; i < SqlOp.loadDatafileArr.length; ++i) {
			loadDataSuccess = Functions.loadDatafiles(folderPath, SqlOp.loadDatafileArr[i]);
			if(loadDataSuccess == true)
				loadData += 1;
		}
		System.out.println("loadData: "+loadData);
		if(loadData != 5)
			return false;
		else
			return true;
		/*
		//For debugging
		SqlOp.printAllSchema();
		SqlOp.printAllTable();
		 */

	}

	public static void createTables() {
		SqlOp.createTable();

		/*
		//For debugging
		SqlOp.printAllSchema();
		SqlOp.printAllTable();
		*/
	}

	public static void dropTables() {
		SqlOp.dropTables(true);
	}

	public static void loadDatafile() {
		System.out.println("Type in the Source Data Folder Path: ");
		Scanner s = new Scanner(System.in);

		String folderPath = s.nextLine();

		System.out.print("Process...");
		boolean readTrue = readData(folderPath);

		if(readTrue == true)
			System.out.println("Done! Data is inputted to the database");

	}

	public static void showRecNum() {
		try {
			System.out.println("Number of records in each tables");
			//Show Category
			int num = SqlOp.countRec("category");
			System.out.println("Table_category: " + num);
			//Show Manufacturer
			num = SqlOp.countRec("manufacturer");
			System.out.println("Table_manufacturer: " + num);
			//Show Part
			num = SqlOp.countRec("part");
			System.out.println("Table_part: " + num);
			//Show Salesperson
			num = SqlOp.countRec("salesperson");
			System.out.println("Table_salesperson: " + num);
			//Show Transaction
			num = SqlOp.countRec("transaction");
			System.out.println("Table_transaction: " + num);
		} catch (Exception e) {
			System.err.println("SQL Exception: " + e.getMessage());
		}

	}


}
