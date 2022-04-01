<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%!
   	Connection conn = null;
   	PreparedStatement pstmt = null;
   	ResultSet rs = null;
   	
   	String url = "jdbc:oracle:thin:@localhost:1521:XE";
   	String user = "webdb";
   	String pass = "1234";
   	
   	String sql = " select author_id, author_name, author_desc from author"
            + " order by author_id " ;
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>hello jsp</title>
</head>
<body>
	<table width="800" border="1">
		<tr>
			<th>저자번호</th>
			<th>저자</th>
			<th>설명</th>
			<%
				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					conn = DriverManager.getConnection(url, user, pass);
					System.out.println("접속성공");
					
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						int authorId = rs.getInt("author_id");
				          String authorName = rs.getString("author_name");
				          String authorDesc = rs.getString(3);
				          
				          System.out.println(authorId + "\t" + authorName + "\t" + authorDesc + "\t");
				          
				          out.print("<tr>");
				          out.print("<td>"+authorId+"</td>");
				          out.print("<td>"+rs.getString("author_name")+"</td>");
				          out.print("<td>"+rs.getString(3)+"</td>");
				          out.print("</tr>");
					}
				}catch(Exception e){
					e.printStackTrace();
				}finally{
					try{
						if(rs != null) rs.close();
						if(pstmt != null) pstmt.close();
						if(conn != null) conn.close();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			%>
		</tr>
	</table>
</body>
</html>