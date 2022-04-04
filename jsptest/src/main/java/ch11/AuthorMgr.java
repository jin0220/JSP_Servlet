package ch11;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
 
 public class AuthorMgr {
    
 	private final String JDBC_DRIVER = "oracle.jdbc.driver.OracleDriver";
 	private final String JDBC_URL = "jdbc:oracle:thin:@localhost:1521:xe";
 	private final String USER = "webdb";
 	private final String PASS = "1234";
 
 	public AuthorMgr() {
 	 try{
 	   Class.forName(JDBC_DRIVER);
 	   }catch(Exception e){
 	      System.out.println("Error : JDBC 드라이버 로딩 실패");
 	   }
  }
 
    public Vector<AuthorBean> getAuthorList() {
  	   Connection conn = null;
  	   Statement stmt = null;
       ResultSet rs = null;
       Vector<AuthorBean> vlist = new Vector<AuthorBean>();
       try {
          conn = DriverManager.getConnection(JDBC_URL, USER, PASS);
          String strQuery = "select * from author";
          stmt = conn.createStatement();
          rs = stmt.executeQuery(strQuery);
    		  
          while (rs.next()) {
    		    AuthorBean bean = new AuthorBean();
    		    bean.setAuthorId(rs.getInt("author_id"));
    		    bean.setAuthorName(rs.getString("author_name"));
    		    bean.setAuthorDesc(rs.getString("author_desc"));
    		    vlist.addElement(bean);
    		  }
       } catch (Exception ex) {
          System.out.println("Exception" + ex);
       } finally {
        if(rs!=null)   try{rs.close();}  catch(SQLException e){}
        if(stmt!=null) try{stmt.close();}catch(SQLException e){}
        if(conn!=null) try{conn.close();}catch(SQLException e){}
       }
       return vlist;
   }
 }