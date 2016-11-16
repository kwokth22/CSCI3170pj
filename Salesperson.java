import java.util.Scanner;

public class Salesperson {

	//Print salesperson menu right after creation
	public Salesperson() {
		System.out.println("\n-----Operations for salesperson menu-----\nWhat kinds of operation would you like to perform?\n1. Search for parts\n2. Sell a part\n3. Return to the main menu\nEnter Your Choice: ");

		Scanner sc = new Scanner(System.in);
		int c = sc.nextInt();

		switch(c){
			case 1: searchParts(); break;
			case 2: sellPart(); break;
			case 3: return;
			default: return; break;
		}

	}


	//If input is 3, should return false and return main
	public static boolean searchParts(int input){
		//

		/***
		Input:
		1:	part name
		2:	manufacturer name	
		 ***/

		/***
		searchCriterion:
		Used in query, either "pName" or "mId"
		 ***/

		if(input!=1 && input!=2){
			return false;
		}


		string searchCriterion;
		if(input == 1){
			searchCriterion = "pName";
		} else{
			searchCriterion = "mId";
		}


		//Prompt for keyword
		System.out.println("Type in the Search Keyword:");

		Scanner sc = new Scanner(System.in);
		String keyWord = sc.nextLine();


		//Prompt for ordering
		System.out.println("1. By price, ascending order\n2. By price, descending order\nChoose the search criterion: ");
		int orderInput = sc.nextInt();
		//Input should be either 1 or 2
		if(orderInput!=1 || orderInput!=2){
			return;
		}

		String orderStr;
		if(orderInput==1){
			orderStr = "ASC";
		} else{
			orderStr = "DESC";
		}


		//















	}

}

