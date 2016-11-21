import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;


public class SqlOp {

    public static String[] datafileArr = {"transaction", "part", "category", "manufacturer", "salesperson"};
    public static String[] loadDatafileArr = {"category", "manufacturer", "part", "salesperson", "transaction"};
    public static Map<String, int[]> tableMap = new HashMap<String, int[]>();

    public static void initTableMap(){
        tableMap.put("category", new int[]{0, 1});
        tableMap.put("manufacturer", new int[]{0, 1, 1, 0});
        tableMap.put("part", new int[]{0, 1, 0, 0, 0, 0, 0});
        tableMap.put("salesperson", new int[]{0, 1, 1, 0, 0});
        tableMap.put("transaction", new int[]{0, 0, 0, 2});
    }

    final static String DB_DRIVER = "com.mysql.jdbc.Driver";
    final static String DB_URL = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2712/db11?autoReconnect=true&useSSL=false";
    final static String DB_USER = "db11";
    final static String DB_PW = "b84cfdf8";

    final static String DEBUG_DB_DRIVER = "com.mysql.jdbc.Driver";
    final static String DEBUG_DB_URL = "jdbc:mysql://localhost:3306/csci_3170_proj?autoReconnect=true&useSSL=false";
    final static String DEBUG_BD_USER = "root";
    final static String DEBUG_DB_PW = "wutszkin28";

    public static Connection conn = SqlOp.connect(
            DEBUG_DB_DRIVER,
            DEBUG_DB_URL,
            DEBUG_BD_USER,
            DEBUG_DB_PW
    );


    public static Statement stmt;
    public static PreparedStatement pstmt;

    public static Connection connect(String forName, String host, String username, String password){
        try{
            Class.forName(forName);
            return DriverManager.getConnection(host, username, password);
        } catch (Exception e){
            return null;
        }
    }

    public static void insert(String tableName, String[] values){
        try {
            String pStmtStr = "INSERT INTO " + tableName + " VALUES(?";
            for(int i=0; i<values.length-1; ++i){
                pStmtStr += ",?";
            }
            pStmtStr += ")";


            //For debugging
            System.out.println("insert(): " + pStmtStr);



            pstmt = conn.prepareStatement(pStmtStr);
            for(int i=0; i<values.length; ++i){
                set(tableMap.get(tableName)[i], i+1, values[i]);
            }

            pstmt.executeUpdate();
            pstmt.close();

        } catch (SQLException x) {
            System.err.println("SQL Exception (insert error): " + x.getMessage());
        }
    }

    public static void set(int type, int index, String s){
        try {
            switch (type) {
                case 0: pstmt.setInt(index, Integer.parseInt(s.trim())); break;
                case 1: pstmt.setString(index, s.trim()); break;
                case 2:
                    java.sql.Date tdate = new java.sql.Date(new SimpleDateFormat("dd/MM/yyyy").parse(s.trim()).getTime());
                    pstmt.setDate(index, tdate);
                    break;
                default: break;
            }
        } catch (Exception x){
            System.err.println("SQL Exception: " + x.getMessage());
        }
    }

    public static void findNMosePopularPart(int N){
        try{
            String sql =  "SELECT p.pID, p.pName, COUNT(*) As nTrans FROM part p, transaction t "+
                          "WHERE t.pID = p.pID " +
                          "GROUP BY p.pID " +
                          "HAVING COUNT(*)>0 " +
                          "ORDER BY nTrans DESC "+
                          "LIMIT ? ";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, N);

            ResultSet rs = pstmt.executeQuery();
            String pID, pName, nTrans;
            System.out.println("| Part ID | Part Name | No. of Transaction |");
            while(rs.next()){
                pID = rs.getString(1);
                System.out.print("| " + pID + " ");
                pName = rs.getString(2);
                System.out.print("| " + pName + " ");
                nTrans = rs.getString(3);
                System.out.print("| " + nTrans + " | ");
                System.out.println("");
            }
            System.out.println("End of Query");

            rs.close();
            pstmt.close();

        }catch (SQLException x) {
            System.err.println("SQL Exception in findNMosePopularPart(Manager)");
            System.err.println("SQL Exception: " + x.getMessage());
        }
    }

    public static int countRec(String tName){
        int n = 0;

        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + tName);
            while (rs.next()) {
                n = rs.getInt(1);
            }
            stmt.close();
        } catch (Exception x) {
            System.err.println("Exception: " + x.getMessage());
        }

        return n;
    }

    public static void dropTables() {
        try {
            stmt = conn.createStatement();

            for(int i=0; i<datafileArr.length; ++i) {
                stmt.executeUpdate("DROP TABLE IF EXISTS " + datafileArr[i]);
            }

            stmt.close();

            System.out.println("Processing...Done! Database is removed!");

        } catch (SQLException x) {
            System.err.println("SQL Exception: " + x.getMessage());
        }

    }

    public static void createTable() {
        try {
            //Drop table if exist
            dropTables();

            stmt = conn.createStatement();


            //Create table category
            stmt.executeUpdate("CREATE TABLE category (" +
                    "cID INTEGER(2) PRIMARY KEY CHECK (mID >= 0), " +
                    "cName VARCHAR(20) NOT NULL )");

            //Create table manufacturer
            stmt.executeUpdate("CREATE TABLE manufacturer (" +
                    "mID INTEGER(2) PRIMARY KEY CHECK (mID >= 0), " +
                    "mName VARCHAR(20) NOT NULL, " +
                    "mAdress VARCHAR(50) NOT NULL, " +
                    "mPhoneNumber INTEGER(8) NOT NULL CHECK (mPhoneNumber >= 0) )");

            //Create table part
            stmt.executeUpdate("CREATE TABLE part (" +
                    "pID INTEGER(3) PRIMARY KEY CHECK (pID >= 0), " +
                    "pName VARCHAR(20) NOT NULL, " +
                    "pPrice INTEGER(5) NOT NULL CHECK (pPrice >= 0), " +
                    "mID INTEGER(2) CHECK (mID >= 0), " +
                    "cID INTEGER(2) CHECK (cID >= 0), " +
                    "pWarrantyPeriod INTEGER(2) NOT NULL CHECK (pWarrantyPeriod >= 0), " +
                    "pAvailableQuantity INTEGER(2) NOT NULL CHECK (pAvailableQuantity >= 0), " +
                    "CONSTRAINT part_manufacturer_fk FOREIGN KEY (mID) REFERENCES manufacturer(mID) ON DELETE CASCADE," +
                    "CONSTRAINT part_category_fk FOREIGN KEY (cID) REFERENCES category(cID) ON DELETE CASCADE)");

            //Create table salesperson
            stmt.executeUpdate("CREATE TABLE salesperson (" +
                    "sID INTEGER(2) PRIMARY KEY CHECK (sID >= 0), " +
                    "sName VARCHAR(20) NOT NULL, " +
                    "sAddress VARCHAR(50) NOT NULL, " +
                    "sPhoneNumber INTEGER(8) NOT NULL CHECK (sPhoneNumber >= 0), " +
                    "sExperience INTEGER(1) NOT NULL CHECK (sExperience >= 0) )");

            stmt.executeUpdate("CREATE TABLE transaction (" +
                    "tID INTEGER(4) PRIMARY KEY CHECK (tID >= 0), " +
                    "pID INTEGER(3), " +
                    "sID INTEGER(2), " +
                    "tDate Date NOT NULL, " +
                    "CONSTRAINT transaction_part_fk FOREIGN KEY (pID) REFERENCES part(pID), " +
                    "CONSTRAINT transaction_salesperson_fk FOREIGN KEY (sID) REFERENCES salesperson(sID) ON DELETE CASCADE)");

            System.out.println("Processing...Done! Database is initialized! ");

            stmt.close();

        } catch (SQLException x) {
            System.out.println("SQL Exception: " + x.getMessage());
        }
    }

    public static ResultSet selectAnd(String selection, String tName, Map<String, String> conditions, boolean order, String orderCol, boolean asc){
        try {
            stmt = conn.createStatement();
            String query="SELECT " + selection + " FROM " + tName + " WHERE ";

            for(Map.Entry<String, String> entry: conditions.entrySet()){
                query += entry.getKey() + "=" + "\"" + entry.getValue() + "\"" + " AND ";
            }

            query = query.substring(0, query.length()-5);

            if(order){
                query += " ORDER BY " + orderCol;
                if(asc){
                    query += " ASC";
                } else{
                    query += " DESC";
                }
            }

            ResultSet rs = stmt.executeQuery(query);
            //stmt.close();

            return rs;

        } catch (SQLException x) {
            System.out.println("SQL Exception (selectAnd): " + x.getMessage());
        }

        return null;
    }

    public static void sortAndlistTotalSalesDesc() {
          try {
              stmt = conn.createStatement();
              String query = "DROP VIEW IF EXISTS temp";
              stmt.execute(query);
              //Create View for number of sold part
              query = "CREATE OR REPLACE VIEW temp as SELECT pID, count(*) as number from transaction group by pID";
              stmt.execute(query);

      //            System.out.println("Successful create view");
      //            //For testing
      //            query = "SELECT m.mName, p.pName, pPrice*temp.number as Sub from manufacturer m, part p, " +
      //                    "temp where temp.pID = p.pID and m.mID = p.mID";
      //
      //            stmt.execute(query);
      //            System.out.println("Sub total ready");

      //          Calculation Total sales
              query = "SELECT m.mID, m.mName, SUM(pPrice*temp.number) as Total from manufacturer m, part p, " +
                      "temp where temp.pID = p.pID and m.mID = p.mID group by m.mID order by Total DESC";
              ResultSet rs = stmt.executeQuery(query);
              //Testing
              System.out.println("total ready");

              String mID, mName, Total;
              System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value");

              //print result
              while (rs.next()) {
                  mID = rs.getString(1);
                  System.out.print("| " + mID + " ");
                  mName = rs.getString(2);
                  System.out.print("| " + mName + " ");
                  Total = rs.getString(3);
                  System.out.print("| " + Total + " |");
                  System.out.println("");
              }
              System.out.println("End of Query");
              //Finish working please drop our temp view
              query = "DROP VIEW IF EXISTS temp";
              stmt.execute(query);

              stmt.close();
          } catch (SQLException x) {
              System.err.println("SQL Exception: " + x.getMessage());
          }

      }

    public static void countTransBasedOnYrs(int lower, int upper) {
        try {
            stmt = conn.createStatement();
            //Drop view if exists
            String query = "DROP VIEW IF EXISTS temp2";
            stmt.execute(query);
            //Create View for counting the number of trans for each sales
            query = "CREATE OR REPLACE VIEW temp2 as SELECT sID, count(*) as number from transaction group by sID";
            stmt.execute(query);
            System.out.println("Create view temp2 success");

            query = "SELECT s.sID, s.sName, s.sExperience, temp2.number as numTrans from salesperson s, temp2 " +
                    "where s.sid = temp2.sid and s.sExperience between " + lower + " AND " + upper+" ORDER BY s.sID DESC";
            ResultSet rs = stmt.executeQuery(query);

            //Print title
            System.out.println("Transaction Record:");
            System.out.println("| ID | Name | Years of Experience | Number of Transaction |");
            String sID, sName, sExp, numOfTrans;
            //print result
            while (rs.next()) {
                sID = rs.getString(1);
                System.out.print("| " + sID + " ");
                sName = rs.getString(2);
                System.out.print("| " + sName + " ");
                sExp = rs.getString(3);
                System.out.print("| " + sExp + " ");
                numOfTrans = rs.getString(4);
                System.out.print("| " + numOfTrans + " | ");
                System.out.println("");
            }
            System.out.println("End of Query");
            //Finish working please drop our temp view
            query = "DROP VIEW IF EXISTS temp2";
            stmt.execute(query);

            stmt.close();
        } catch (SQLException x) {
            System.err.println("SQL exception: "+ x.getMessage());
        }
    }

    public static boolean isPartAvailable(int id){
        try {
            Map<String, String> conditionMap= new HashMap<String, String>();
            conditionMap.put("pID", Integer.toString(id));

            ResultSet rs = selectAnd("pAvailableQuantity", "part", conditionMap, false, "", false);

            int n = 0;

            while(rs.next()){
                n = rs.getInt("pAvailableQuantity");
            }

            return n > 0;


        } catch (SQLException x) {
            System.out.println("SQL Exception (isPartAvailable): " + x.getMessage());
        }

        return false;

    }

    public static void updateAnd(String tName, String action, Map<String, String> conditions){
        try {
            stmt = conn.createStatement();
            String query="UPDATE " + tName + " SET " + action + " WHERE ";

            for(Map.Entry<String, String> entry: conditions.entrySet()){
                query += "" + entry.getKey() + "=" + "\"" + entry.getValue() + "\"" + " AND ";
            }

            query = query.substring(0, query.length()-5);


            stmt.executeUpdate(query);

            stmt.close();


        } catch (SQLException x) {
            System.out.println("SQL Exception (updateAnd): " + x.getMessage());
        }

    }


    public static void printAllTable(){
        try {
            stmt = conn.createStatement();

            for(int i=0; i<loadDatafileArr.length; ++i) {
                ResultSet r = stmt.executeQuery("SELECT * FROM " + loadDatafileArr[i]);
                ResultSetMetaData rsmd = r.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                System.out.println("\n*** " + SqlOp.loadDatafileArr[i] + " ***\n");
                while(r.next()){
                    for(int j = 1; j < columnsNumber+1; ++j) {
                        System.out.print(r.getString(j) + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }


        } catch (SQLException x){
            System.out.println("SQL Exception: " + x.getMessage());
        }
    }

    public static void printAllSchema(){
        try {
            stmt = conn.createStatement();

            for(int i=0; i<loadDatafileArr.length; ++i) {
                ResultSet r = stmt.executeQuery("DESC " + loadDatafileArr[i]);
                ResultSetMetaData rsmd = r.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                System.out.println("\n*** " + SqlOp.loadDatafileArr[i] + " ***\n");
                while(r.next()){
                    for(int j = 1; j < columnsNumber+1; ++j) {
                        System.out.print(r.getString(j) + " ");
                    }
                    System.out.println();
                }
                System.out.println();
            }


        } catch (SQLException x){
            System.out.println("SQL Exception: " + x.getMessage());
        }
    }


}
