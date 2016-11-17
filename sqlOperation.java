import java.sql.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

public class sqlOperation{
	static Connection conn;
	static Statement stmt;

	static SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
	static java.util.Date date;

	public static boolean conn(){
		try { 
			Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection( "jdbc:mysql://projgw.cse.cuhk.edu.hk:2712/db22?autoReconnect=true&useSSL=false", "db22", "1122574b"); 
			System.err.println("Connection Success");
		} catch(Exception x) { 
			System.err.println("Unable to load the driver class!"); 
			System.err.println("Exception: " + x.getMessage());
            return false;
		}
		return true;
	}
	public static void dropTable(){
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE IF EXISTS transaction");	
			stmt.executeUpdate("DROP TABLE IF EXISTS part");	
			stmt.executeUpdate("DROP TABLE IF EXISTS category");
			stmt.executeUpdate("DROP TABLE IF EXISTS manufacturer");
			stmt.executeUpdate("DROP TABLE IF EXISTS salesperson");
			stmt.close();			
			
		}catch (SQLException x) {
            System.err.println("SQL Exception: " + x.getMessage());
        }

	}
	public static void createTable(){
		try{
			//Drop table if exist
			dropTable();

			stmt = conn.createStatement();


			//Create table category
			stmt.executeUpdate("CREATE TABLE category (" + 
							   "cID INTEGER(2) PRIMARY KEY CHECK (mID >= 0), " +
							   "cName VARCHAR(20) NOT NULL )" );

			//Create table manufacturer
			stmt.executeUpdate("CREATE TABLE manufacturer (" +
							   "mID INTEGER(2) PRIMARY KEY CHECK (mID >= 0), " +
							   "mName VARCHAR(20) NOT NULL, " +
							   "mAdress VARCHAR(50) NOT NULL, " +
							   "mPhoneNumber INTEGER(8) NOT NULL CHECK (mPhoneNumber >= 0) )" );

			//Create table part
			stmt.executeUpdate("CREATE TABLE part (" +
							   "pID INTEGER(3) PRIMARY KEY CHECK (pID >= 0), " +
							   "pName VARCHAR(20) NOT NULL, " +
							   "pPrice INTEGER(5) NOT NULL CHECK (pPrice >= 0), " +
							   "mID INTEGER(2) CHECK (mID >= 0), " +
							   "cID INTEGER(2) CHECK (cID >= 0), " +
							   "pWarrantyPeriod INTEGER(2) NOT NULL CHECK (pWarrantyPeriod >= 0), " +
							   "pAvailableQuantity INTEGER(2) NOT NULL CHECK (pAvailableQuantity >= 0), " +
							   "CONSTRAINT part_manufacturer_fk FOREIGN KEY (mID) REFERENCES manufacturer(mID) ON DELETE CASCADE, " +
							   "CONSTRAINT part_category_fk FOREIGN KEY (cID) REFERENCES category(cID) ON DELETE CASCADE)" ); 

			//Create table salesperson
			stmt.executeUpdate("CREATE TABLE salesperson (" +
							   "sID INTEGER(2) PRIMARY KEY CHECK (sID >= 0), " +
							   "sName VARCHAR(20) NOT NULL, " +
							   "sAddress VARCHAR(50) NOT NULL, " +
						       "sPhoneNumber INTEGER(8) NOT NULL CHECK (sPhoneNumber >= 0), " +
							   "sExperience INTEGER(1) NOT NULL CHECK (sExperience >= 0) )" );

			stmt.executeUpdate("CREATE TABLE transaction (" + 
							   "tID INTEGER(4) PRIMARY KEY CHECK (tID >= 0), " +
							   "pID INTEGER(3), " +
							   "sID INTEGER(2), "+
							   "tDate Date NOT NULL, " + 
							   "CONSTRAINT transaction_part_fk FOREIGN KEY (pID) REFERENCES part(pID), " +
							   "CONSTRAINT transaction_salesperson_fk FOREIGN KEY (sID) REFERENCES salesperson(sID) ON DELETE CASCADE )" );

			System.err.println("Processing...Done! Database is initialized! ");
			stmt.close();
		}catch (SQLException x) {
            System.out.println("SQL Exception: " + x.getMessage());
        }
	}

	public static void insertCategory(String s1, String s2){
		try{
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO category VALUES(?,?)");
			pstmt.setInt(1,Integer.parseInt(s1.trim()));
			pstmt.setString(2,s2.trim());

			pstmt.executeUpdate();
			pstmt.close();

		}catch (SQLException x){
			System.err.println("SQL Exception: " + x.getMessage());
		}

	}
	public static void insertManufacturer(String s1, String s2, String s3, String s4){
		try{
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO manufacturer VALUES(?,?,?,?)");
			pstmt.setInt(1,Integer.parseInt(s1.trim()));
			pstmt.setString(2,s2.trim());
			pstmt.setString(3,s3.trim());
			pstmt.setInt(4,Integer.parseInt(s4.trim()));

			pstmt.executeUpdate();
			pstmt.close();

		}catch (SQLException x){
			System.err.println("SQL Exception: " + x.getMessage());
		}

	}
	public static void insertPart(String s1, String s2, String s3, String s4,String s5,String s6,String s7){
		try{
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO part VALUES(?,?,?,?,?,?,?)");
			pstmt.setInt(1,Integer.parseInt(s1.trim()));
			pstmt.setString(2,s2.trim());
			pstmt.setInt(3,Integer.parseInt(s3.trim()));
			pstmt.setInt(4,Integer.parseInt(s4.trim()));
			pstmt.setInt(5,Integer.parseInt(s5.trim()));
			pstmt.setInt(6,Integer.parseInt(s6.trim()));
			pstmt.setInt(7,Integer.parseInt(s7.trim()));

			pstmt.executeUpdate();
			pstmt.close();

		}catch (SQLException x){
			System.err.println("SQL Exception: " + x.getMessage());
		}
	}
	public static void insertSalesperson(String s1, String s2, String s3, String s4,String s5){
		try{
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO part VALUES(?,?,?,?,?)");
			pstmt.setInt(1,Integer.parseInt(s1.trim()));
			pstmt.setString(2,s2.trim());
			pstmt.setString(3,s3.trim());
			pstmt.setInt(4,Integer.parseInt(s4.trim()));
			pstmt.setInt(5,Integer.parseInt(s5.trim()));

			pstmt.executeUpdate();
			pstmt.close();

		}catch (SQLException x){
			System.err.println("SQL Exception: " + x.getMessage());
		}
	}
	public static void insertTransaction(String s1, String s2, String s3, String s4){
		try{
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO part VALUES(?,?,?,?)");
			pstmt.setInt(1,Integer.parseInt(s1.trim()));
			pstmt.setString(2,s2.trim());
			pstmt.setString(3,s3.trim());

			date = formatter.parse(s4.trim());
			java.sql.Date tdate = new java.sql.Date(date.getTime());

			pstmt.setDate(4,tdate);

			pstmt.executeUpdate();
			pstmt.close();

		}catch (Exception x){
			System.err.println("SQL Exception: " + x.getMessage());
		}
	}

}	