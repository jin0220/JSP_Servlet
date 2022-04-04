package ch11;

import java.sql.*;

public class DriverTest{
	public static void main(String args[]){
		Connection con;

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			con = DriverManager.getConnection(url, "webdb", "1234");
      //con=DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "root", "1234");
			System.out.println("Success");
		}
		catch(SQLException ex){ System.out.println("SQLException" + ex);}
		catch(Exception ex){ System.out.println("Exception:" + ex);}
	}
}