﻿<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ page import="hellojsp.GuestBookDaoImpl"%>
<%@ page import="hellojsp.GuestBookVo"%>
<%@ page import="java.util.List"%>

<% 
	GuestBookDaoImpl dao = new GuestBookDaoImpl();
	List<GuestBookVo> list = dao.getList();
	/* System.out.println(list.toString()); */
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>방명록</title>
</head>
<body>
	
	<form action="add.jsp" method="post">
	<table border=1 width=500>
		<tr>
			<td>이름</td><td><input type="text" name="name"></td>
			<td>비밀번호</td><td><input type="password" name="pass"></td>
		</tr>
		<tr>
			<td colspan=4><textarea name="content" cols=60 rows=5></textarea></td>
		</tr>
		<tr>
			<td colspan=4 align=right><input type="submit" VALUE=" 확인 "></td>
		</tr>
	</table>
	</form>
	<br/>
	
	<%
	for(GuestBookVo v : list) { 
	%>
	<table width=510 border=1>
		<tr>
			<td><%=v.getNo() %></td>
			<td><%=v.getName() %></td>
			<td><%=v.getDate() %></td>
			<td><a href="deleteform.jsp?no=<%=v.getNo() %>&name=<%=v.getName() %>">삭제</a></td>
		</tr>
		<tr>
			<td colspan=4><%=v.getContent() %></td>
		</tr>
	</table>
        <br/>
    <% 
	}
    %>
</body>
</html>