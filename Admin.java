import java.util.Scanner;

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
	public static int getUserChoice(){
		System.out.print("Enter Your Choice: ");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		return choice;
	}

}