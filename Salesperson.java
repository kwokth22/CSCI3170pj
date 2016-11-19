import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Calendar;
import java.util.Date;

public class Salesperson {

    public static String[] menuArr = {
            "-----Operations for salesperson menu-----",
            "What kinds of operation would you like to perform?",
            "1. Search for parts",
            "2. Sell a part",
            "3. Return to the main menu"
    };

    Salesperson(){

        while(SalesSystem.choice != 3) {
            Functions.printMenu(menuArr);

            SalesSystem.choice = Functions.prompt();
            switch (SalesSystem.choice) {
                case 1:
                    searchPart();
                    break;
                case 2:
                    sellPart();
                    break;
                case 3:
                    return;
                default:
                    break;
            }
        }
    }

    public static void searchPart(){
        System.out.print("Choose the Search Criterion:\n1. Part Name\n2. Manufacturer Name\nChoose the Search Criterion: ");
        int criterion = SalesSystem.sc.nextInt();

        if(criterion!=1 && criterion!=2){
            return;
        }

        System.out.print("Type in the Search keyword: ");
        Scanner scanner = new Scanner(System.in);
        String keyword = scanner.nextLine();

        System.out.print("Choose ordering:\n1. By price, ascending order\n2. By price, descending order\nChoose the Search criterion: ");
        int orderingInput = SalesSystem.sc.nextInt();
        String ordering = "ASC";
        if(orderingInput==1){
            ordering = "ASC";
        } else if(orderingInput==2){
            ordering = "DESC";
        } else {
            return;
        }

        Map<String, String> condition = new HashMap<String, String>();
        ResultSet rs = null;

        if(criterion==1){
            try {
                SqlOp.stmt = SqlOp.conn.createStatement();
                String query = "SELECT * FROM part natural join manufacturer natural join category where pName=\"" + keyword + "\" ORDER BY pPrice " + ordering ;

                rs = SqlOp.stmt.executeQuery(query);

            } catch (SQLException x){
                System.out.println("SQL Exception (searchPart criterion 1): " + x.getMessage());
            }

        } else if(criterion==2){
            try {
                SqlOp.stmt = SqlOp.conn.createStatement();
                String query = "SELECT * FROM part natural join manufacturer natural join category where mName=\"" + keyword + "\" ORDER BY pPrice " + ordering;

                rs = SqlOp.stmt.executeQuery(query);

            } catch (SQLException x){
                System.out.println("SQL Exception (searchPart criterion 2): " + x.getMessage());
            }

        }

        try{
            System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
            while(rs.next()) {
                System.out.println("| " + rs.getInt("pID") + " | " + rs.getString("pName") + " | " + rs.getString("mName") + " | " + rs.getString("cName") + " | " + rs.getInt("pAvailableQuantity") + " | " + rs.getInt("pWarrantyPeriod") + " | " + rs.getInt("pPrice") + " | ");
            }

            SqlOp.stmt.close();

        } catch (SQLException x){
            System.out.println("SQL Exception (searchPart print table): " + x.getMessage());
        }




    }

    public static void sellPart(){
        System.out.print("Enter The Part ID: ");
        int partID = SalesSystem.sc.nextInt();

        System.out.print("Enter The Salesperson ID: ");
        int salespersonId = SalesSystem.sc.nextInt();

        //Add new transaction record
        //Is the transaction ID auto-increment? Sorted?


        if(!SqlOp.isPartAvailable(partID)) {

            System.out.println("The part remaining quantity is 0 and cannot be sold");

        } else{

            int currtID = -1;

            try {
                //Get current largest tID

                Map<String, String> tmpMap = new HashMap<String, String>();
                ResultSet r = SqlOp.selectAnd("max(tID)", "transaction", tmpMap, false, null, false);

                while (r.next()) {
                    currtID = r.getInt(1);
                }

                ++currtID;

            } catch (SQLException x){
                System.out.println("SQL Exception (get curr tID): " + x.getMessage());
            }


            String currDate = "";
            try {
                SqlOp.stmt = SqlOp.conn.createStatement();
                ResultSet r = SqlOp.stmt.executeQuery("SELECT NOW()");

                while(r.next()){
                    currDate = r.getDate(1).toString();
                }

            } catch (SQLException x){
                System.out.println("SQL Exception (sellPart show product detail): " + x.getMessage());
            }


            System.out.println("currtID: " + currtID);
            System.out.println("currDate: " + currDate);



            try {
                SqlOp.stmt.executeUpdate("INSERT INTO transaction VALUES (" + currtID + ", " + partID + ", " + Integer.toString(salespersonId) + ", \'" + currDate + "\')");
                //SqlOp.insert("transaction", new String[] {Integer.toString(currtID), Integer.toString(partID), Integer.toString(salespersonId), });
            } catch (SQLException x) {
                System.out.println("SQL Exception (insert into transaction): " + x.getMessage());
            }

            //Decrease part volume by one
            Map<String,String> condition = new HashMap<String, String>();
            condition.put("pID", Integer.toString(partID));
            SqlOp.updateAnd("part", "pAvailableQuantity=pAvailableQuantity-1", condition);

            //Show message
            ResultSet rs = SqlOp.selectAnd("*", "part", condition, false, "", false);
            try {
                while (rs.next()) {
                    System.out.println("Product: " + rs.getString("pName") + "(id: " + rs.getInt("pID") + ") Remaining Quantity: " + rs.getInt("pAvailableQuantity"));
                }
            } catch (SQLException x) {
                System.out.println("SQL Exception (sellPart show product detail): " + x.getMessage());
            }

        }


    }
}
