import java.util.Scanner;
public class projectMain{
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) {
		int choice = 0;
		//if connect db successful
		System.out.println("Welcome to sales system!");
		do{
			printMainMenu();
			
			choice = sc.nextInt();
			//Selected role
			if(choice == 1)//admin
			{
				Admin admin = new Admin();
				admin.printAdminMenu();
				getUserChoice();
				
			}
			else if (choice == 2) //salesperson
			{

			}
			else if (choice == 3 ) //manager
			{

			}
		}while(choice!=4);

	}
	public static void printMainMenu(){
		System.out.println("-----Main menu-----");
		System.out.println("What kinds of operation would you like to perform?");
		System.out.println("1. Operation for administrator");
		System.out.println("2. Operation for salesperson");
		System.out.println("3. Operation for manager");
		System.out.println("4. Exit this program");
		printEnterChoice();
	}

}