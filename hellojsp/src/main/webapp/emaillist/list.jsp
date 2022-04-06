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
   	
   	String sql = " select no, last_name, first_name, email "
   					+ " from emaillist " ;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>메일 리스트에 가입되었습니다.</h1>
	<p>입력한 정보 내역입니다.</p>
	<!-- 메일정보 리스트 -->
	<%-- list에서 하나씩 빼서 테이블를 채운다--%>
	<% try{
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url, user, pass);
		System.out.println("접속성공");
		
		pstmt = conn.prepareStatement(sql);
		rs = pstmt.executeQuery();
		
		while(rs.next()){ 
			String no = rs.getString("no");
			String last_name = rs.getString("last_name");
	        String first_name = rs.getString("first_name");
	        String email = rs.getString("email");
    %>
	<table border="1" cellpadding="5" cellspacing="2">
		<tr>
			<td align=right width="110">Last name: </td>
			<td width="170"><%=last_name%></td>
		</tr>
		<tr>
			<td align=right >First name: </td>
			<td><%=first_name %></td>
		</tr>
		<tr>
			<td align=right>Email address: </td>
			<td><%=email %></td>
		</tr>
	</table>
	<form name="a" method="post" action="delete.jsp">
		<input type="hidden" name="no" value="<%=no%>">
		<input type="button" value="삭제" onclick="submit()" />
	</form>
	<br>
	<% 	 }
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
	}%>
	<p>
		<a href="form.jsp">추가메일 등록</a>
	</p>
	<br>
</body>
</html>