import java.util.Scanner;
import java.io.*;
import java.text.ParseException;

public class Admin{
	public static void printAdminMenu(){
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
	public static void loadData(){
		Scanner sc = new Scanner(System.in);
		System.out.print("\nType in the Source Data Folder Path: ");
		String path = sc.nextLine();
		loadCategory("/"+path);
		// loadManufacturer(path);
		// loadPart(path);
		// loadSalesperson(path);
		// loadTransaction(path);	
	}
	public static void loadCategory(String path){

		try{
			BufferedReader br = new BufferedReader(new FileReader(path + "/category.txt"));
			String strLine;
			while((strLine=br.readLine()) != null)
			{
				if(!strLine.equals("")){
					String[] tokens = strLine.split("\t");
					sqlOperation.insertCategory(tokens[1],tokens[2]);
				}

			}
		}catch (IOException e){
			System.err.println("IOException: " + e.getMessage());
		}

	}
}