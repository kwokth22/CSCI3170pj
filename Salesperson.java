import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class Salesperson {

    public static String[] menuArr = {
            "-----Operations for salesperson menu-----",
            "What kinds of operation would you like to perform?",
            "1. Search for parts",
            "2. Sell a part",
            "3. Return to the main menu"
    };

    Salesperson(){
        Functions.printMenu(menuArr);

        SalesSystem.choice = Functions.prompt();
        switch (SalesSystem.choice){
            case 1: searchPart(); break;
            case 2: sellPart(); break;
            case 3: return;
            default: break;
        }
    }

    public static void searchPart(){
        System.out.print("Choose the Search Criterion:\n1. Part Name\n2. Manufacturer Name\nChoose the Search Criterion: ");
        int criterion = SalesSystem.sc.nextInt();

        if(criterion!=1 && criterion!=2){
            return;
        }

        System.out.print("Type in the Search keyword:");
        String keyword = SalesSystem.sc.nextLine();

        System.out.print("Choose ordering:\n1. By price, ascending order\n2. By price, descending order\nChoose the Search criterion: ");
        int orderingInput = SalesSystem.sc.nextInt();
        boolean ordering;
        if(orderingInput==1){
            ordering = true;
        } else if(orderingInput==2){
            ordering = false;
        } else {
            return;
        }

        Map<String, String> condition = new HashMap<String, String>();
        ResultSet rs = null;

        if(criterion==1){
            try {
                SqlOp.stmt = SqlOp.conn.createStatement();
                String query = "SELECT * FROM part natural join manufacturer natural join category where pName=\"" + keyword + "\"";
                rs = SqlOp.stmt.executeQuery(query);
            } catch (SQLException x){
                System.out.println("SQL Exception: " + x.getMessage());
            }

        } else if(criterion==2){
            try {
                SqlOp.stmt = SqlOp.conn.createStatement();
                String query = "SELECT * FROM part natural join manufacturer natural join category where mName=\"" + keyword + "\"";
                rs = SqlOp.stmt.executeQuery(query);
            } catch (SQLException x){
                System.out.println("SQL Exception: " + x.getMessage());
            }

        }

        try{
            System.out.println("| ID | Name | Manufacturer | Category | Quantity | Warranty | Price |");
            while(rs.next()) {
                System.out.println("| " + rs.getInt("pID") + " | " + rs.getString("pName") + " | " + rs.getString("mName") + " | " + rs.getString("cName") + " | " + rs.getInt("pAvailableQuantity") + " | " + rs.getInt("pWarrantyPeriod") + " | " + rs.getInt("pPrice") + " | ");
            }

            SqlOp.stmt.close();

        } catch (SQLException x){
            System.out.println("SQL Exception: " + x.getMessage());
        }




    }

    public static void sellPart(){
        System.out.print("Enter The Part ID: ");
        int partID = SalesSystem.sc.nextInt();

        System.out.print("Enter The Salesperson ID: ");
        int salespersonId = SalesSystem.sc.nextInt();

        //Add new transaction record
        //Is the transaction ID auto-increment? Sorted?
        if(SqlOp.isPartAvailable(partID)){
            SqlOp.insert("transaction", new String[] {"", Integer.toString(partID), Integer.toString(salespersonId), ""});

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
                System.out.println("SQL Exception: " + x.getMessage());
            }

        }


    }
}
