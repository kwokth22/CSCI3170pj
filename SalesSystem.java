mport java.util.Scanner;
import java.sql.*;


public class SalesSystem {
	static Scanner sc = new Scanner(System.in);
	static Connection conn;

	SalesSystem(){
		System.out.println("constructor");
	}

	public static void main(String[] args) {
		int c = -1;

		if(!conn()){
			//For debugging
			System.out.println("Cannot create connection");
			return;
		}

		System.out.println("Conn created");

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


	public static boolean conn(){
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(
					"jdbc:mysql://projgw.cse.cuhk.edu.hk:2712/db11?autoReconnect=true&useSSL=false", "db11", "b84cfdf8");

			return true;
		} catch(Exception e) {

			return false;
		}

	}



}

