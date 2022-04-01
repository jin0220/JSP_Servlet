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
   	String user = "hr";
   	String pass = "hr";
   	
   	String sql = " select e.employee_id, e.first_name, e.hire_date, d.department_name, e2.first_name manager_name, e.salary "
   			+ " from employees e, employees e2, departments d "
   			+ " where e.manager_id = e2.employee_id and e.department_id = d.department_id"
   			+ " order by e.employee_id ";
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
			<th>사번</th>
			<th>이름</th>
			<th>입사일</th>
			<th>근무부서</th>
			<th>매니저</th>
			<th>급여</th>
			<%
				try{
					Class.forName("oracle.jdbc.driver.OracleDriver");
					conn = DriverManager.getConnection(url, user, pass);
					System.out.println("접속성공");
					
					pstmt = conn.prepareStatement(sql);
					rs = pstmt.executeQuery();
					
					while(rs.next()){
						  int employee_id = rs.getInt("employee_id");
				          String firstName = rs.getString("first_name");
				          String hireDate = rs.getString("hire_date");
				          String jobId = rs.getString("department_name");
				          String managerId = rs.getString("manager_name");
				          String salary = rs.getString("salary");
				          
				          System.out.print(employee_id + "\t" + firstName + "\t" + hireDate + "\t");
				          System.out.println(jobId + "\t" + managerId + "\t" + salary + "\t");
				          
				          out.print("<tr>");
				          out.print("<td>"+employee_id+"</td>");
				          out.print("<td>"+firstName+"</td>");
				          out.print("<td>"+hireDate+"</td>");
				          out.print("<td>"+jobId+"</td>");
				          out.print("<td>"+managerId+"</td>");
				          out.print("<td>$"+salary+"</td>");
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