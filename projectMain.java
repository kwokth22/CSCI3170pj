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
					int op = 0;
					while(op != 5){
						admin.printAdminMenu();
						op = getUserChoice();
						switch (op)
						{
							case 1: //Create table
								sqlOperation.createTable();
								break;
							case 2: //drop table
								sqlOperation.dropTable();
								System.out.println("Processing...Done! Database is removed!");
								break;
							case 3: //Load from Datafile
								admin.loadData();
								break;
							case 4: //show number of records in each table
								break;
							case 5:
								break;
							default:
								break;
						}
					}

				}
				else if (choice == 2) //salesperson
				{
					System.out.println("sales");
				}
				else if (choice == 3 ) //manager
				{
					System.out.println("sales");
				}
			}while(choice!=4); //exit
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