import java.util.Scanner;

public class Admin{
	public static void printMenu(){
		System.out.println("-----Operations for administrator menu-----");
		System.out.println("What kinds of operation would you like to perform?");
		System.out.println("1. Create all tables");
		System.out.println("2. Delete all tables");
		System.out.println("3. Load from datafile");
		System.out.println("4. Show number of records in each table");
		System.out.println("5. Return to the main menu");
	}

	public static int prompt(){
		System.out.print("Enter Your Choice: ");
		Scanner sc = new Scanner(System.in);

		return sc.nextInt();

	}

	public static void createTable(String tableName, String query){


	}

	public static void deleteTable(String tableName){

	}

	public static void loadDataset(){

	}

	public static int showRecNum(){

		return 0;

	}


}

