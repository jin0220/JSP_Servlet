<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.DriverManager"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	
	String url = "jdbc:oracle:thin:@localhost:1521:XE";
	String user = "webdb";
	String pass = "1234";
	
	String sql = "insert into emaillist values (seq_no.nextval, ?, ?, ?)";
	
	request.setCharacterEncoding("utf-8");
	
	String last_name = request.getParameter("ln");
	String first_name = request.getParameter("fn");
	String email = request.getParameter("email");
	
	try {
		Class.forName("oracle.jdbc.driver.OracleDriver");
		conn = DriverManager.getConnection(url, user, pass);
	
		//미리 미완성의 sql을 등록시켜 놓겠다.
		pstmt = conn.prepareStatement(sql);
	
		//바인딩 시키기
		pstmt.setString(1, last_name); //첫번째 물음표에 대입!
		pstmt.setString(2, first_name);
		pstmt.setString(3, email);
	
		pstmt.executeUpdate(); // insert update delete (반영 건수 리턴)
	} catch (Exception e) {
		e.printStackTrace();
	} finally {
		try {
			if (pstmt != null)
		pstmt.close();
			if (conn != null)
		conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	response.sendRedirect("list.jsp");
%>