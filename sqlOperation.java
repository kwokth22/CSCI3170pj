import java.sql.*;

public class sqlOperation{
	static Connection conn;
	static Statement stmt;
	public static boolean conn(){
		try { 
			Class.forName("com.mysql.jdbc.Driver");
		    conn = DriverManager.getConnection( "jdbc:mysql://projgw.cse.cuhk.edu.hk:2712/db22?autoReconnect=true&useSSL=false", "db22", "1122574b"); 
			System.err.println("Connection Success");
		} 
		catch(Exception x) { 
			System.err.println("Unable to load the driver class!"); 
			System.err.println("Exception: " + x.getMessage());
            return false;
		}
		return true;
	}
	public static void dropTable(){
		try{
			stmt = conn.createStatement();
			stmt.executeUpdate("DROP TABLE category");
			stmt.executeUpdate("DROP TABLE manufacturer ");
			stmt.close();
		}catch (SQLException x) {
            System.out.println("SQL Exception: " + x.getMessage());
        }
	}
	public static void createTable(){
		try{
			stmt = conn.createStatement();
			//Create table category
			stmt.executeUpdate("CREATE TABLE category " + 
							   "(cID INTEGER(2) PRIMARY KEY, " +
							   "cName VARCHAR(20) NOT NULL )");

			stmt.executeUpdate("CREATE TABLE manufacturer" +
							   "(mID INTEGER(2) PRIMARY KEY, " +
							   	"mName VARCHAR(20) NOT NULL, " +
							   	"mAdress VARCHAR(50), " +
							   	"mPhoneNumber INTEGER(8) NOT NULL )");

			System.err.println("Create Table success");
			stmt.close();
		}catch (SQLException x) {
            System.out.println("SQL Exception: " + x.getMessage());
        }
	}
}	