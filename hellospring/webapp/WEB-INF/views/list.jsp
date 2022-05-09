<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="/add" method="post">
		Last name( 성): <input type="text" name="last_name"> <br>
		First name(이름): <input type="text" name="first_name"> <br>
		Email address: <input type="text" name="email"> <br> <input
			type="submit" value="등록">
	</form>
	
	<table border=1 cellpadding=0 cellspacing=0>
		<tr>
			<th>no</th>
			<th>last_name</th>
			<th>first_name</th>
			<th>email</th>
		</tr>
		<c:forEach items="${emaillistList}" var="emaillist">
			<tr>
				<td>${emaillist.no}</td>
				<td>${emaillist.last_name}</td>
				<td>${emaillist.first_name}</td>
				<td>${emaillist.email}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>