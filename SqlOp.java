import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.HashMap;


public class SqlOp {

	/***
	  Functions to be implemented:
	  select()
	  delete()
	  update()
	 ***/

	public static String[] textFileArr = {"category.txt", "manufacturer.txt", "part.txt", "salesperson.txt", "transaction.txt"};
	public static Map<String, int[]> tableMap = new HashMap<String, int[]>();

	public static void initTableMap(){
		tableMap.put("category", new int[]{0, 1});
		tableMap.put("manufacturer", new int[]{0, 1, 1, 0});
		tableMap.put("part", new int[]{0, 1, 0, 0, 0, 0, 0});
		tableMap.put("salesperson", new int[]{0, 1, 1, 0, 0});
		tableMap.put("transaction", new int[]{0, 0, 0, 2});
	}


	public static Connection conn = SqlOp.connect(
			"com.mysql.jdbc.Driver",
			"jdbc:mysql://projgw.cse.cuhk.edu.hk:2712/db11?autoReconnect=true&useSSL=false",
			"db11",
			"b84cfdf8"
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
			pStmtStr = ")";

			pstmt = conn.prepareStatement(pStmtStr);
			for(int i=0; i<values.length; ++i){
				set(tableMap.get(tableName)[i], i+1, values[i]);
			}

			pstmt.executeUpdate();
			pstmt.close();

		} catch (SQLException x) {
			System.err.println("SQL Exception: " + x.getMessage());
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

			for(int i=0; i<textFileArr.length; ++i) {
				stmt.executeUpdate("DROP TABLE IF EXISTS " + textFileArr[i]);
			}

			stmt.close();

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
					"CONSTRAINT part_manufacturer_fk FOREIGN KEY (mID) REFERENCES manufacturer(mID) ON DELETE CASCADE, " +
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
					"CONSTRAINT transaction_salesperson_fk FOREIGN KEY (sID) REFERENCES salesperson(sID) ON DELETE CASCADE )");

			System.err.println("Processing...Done! Database is initialized! ");

			stmt.close();

		} catch (SQLException x) {
			System.out.println("SQL Exception: " + x.getMessage());
		}
	}




}

