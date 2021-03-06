import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;

public class Functions {

    public static void printMenu(String[] menu){
        String s = "\n";
        for(int i=0; i<menu.length; ++i){
            s += menu[i] + "\n";
        }
        System.out.print(s);
    }


    public static int prompt(){
        System.out.print("Enter Your Choice: ");

        return SalesSystem.sc.nextInt();
    }

    public static boolean loadDatafiles(String folderPath, String filename){
        try{
			/*
			//For debugging
            System.out.println(folderPath + "/" + filename + ".txt");
			*/

            BufferedReader br = new BufferedReader(new FileReader(folderPath + "/" + filename + ".txt"));
            String line;

            //System.out.println("Reading " + "sample_data/" + filename + ".txt");

            while ((line = br.readLine()) != null) {
                try {
                    if(filename == "transaction"){
                        String[] tmpArr = line.split("\t");
                        SqlOp.stmt = SqlOp.conn.createStatement();
                        String q = "INSERT INTO " + filename + " VALUES (";
                        for(int i=0; i<tmpArr.length-1; ++i){
                            q += tmpArr[i] + ",";
                        }

                        q += "STR_TO_DATE('" + tmpArr[tmpArr.length-1] + "','%d/%m/%Y'))";
                        //System.out.println(q);
                        SqlOp.stmt.executeUpdate(q);
                        SqlOp.stmt.close();

                        //SqlOp.stmt.executeUpdate("INSERT INTO " + filename + " VALUES (\"" + line.replaceAll("\t", "\",\"") + "\")");
                        /*for(int i=0; i<tmpArr.length; ++i){
                            System.out.println(tmpArr[i]);
                        }*/
                    } else {
                        SqlOp.stmt = SqlOp.conn.createStatement();
                        String q = "INSERT INTO " + filename + " VALUES (\"" + line.replaceAll("\t", "\",\"") + "\")";
                        SqlOp.stmt.executeUpdate("INSERT INTO " + filename + " VALUES (\"" + line.replaceAll("\t", "\",\"") + "\")");

						/*
						//For debugging
                        System.out.println(q);
						*/


                        SqlOp.stmt.close();
                    }

                } catch (SQLException x){
                    System.err.println("SQL Exception: " + x.getMessage());
                    return false;
                }

            }
            return true;

        } catch (Exception e){
            System.out.println("Not found: " + filename);
            return false;
        }
    }
}
