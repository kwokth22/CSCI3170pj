import java.util.Scanner;
import java.sql.*;



public class projectMain{
	static Scanner sc = new Scanner(System.in);
	// static Connection conn;
	// static Statement stmt;
	public static void main(String[] args) {
		int choice = 0;
		
		//if connect db successful
		if(sqlOperation.conn()){
			System.out.println("Welcome to sales system!");
			do{
				printMainMenu();
				
				choice = getUserChoice();
				//Selected role
				if(choice == 1)//admin
				{
					Admin admin = new Admin();
					admin.printAdminMenu();
					int op = admin.getUserChoice();
					if(op == 1)
						{
							sqlOperation.createTable();
							choice = 0;
						}
					else if (op == 2)
					{
						sqlOperation.dropTable();
						choice = 0;
					}
				}
				else if (choice == 2) //salesperson
				{

				}
				else if (choice == 3 ) //manager
				{

				}

			}while(choice!=4);
		}
	}
	public static void printMainMenu(){
		System.out.println("-----Main menu-----");
		System.out.println("What kinds of operation would you like to perform?");
		System.out.println("1. Operation for administrator");
		System.out.println("2. Operation for salesperson");
		System.out.println("3. Operation for manager");
		System.out.println("4. Exit this program");
		System.out.print("Enter Your Choice: ");
	}
	public static int getUserChoice(){
		System.out.print("Enter Your Choice: ");
		Scanner sc = new Scanner(System.in);
		int choice = sc.nextInt();
		return choice;
	}

}