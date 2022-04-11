<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
  String no = request.getParameter("no");
  String dbpass = request.getParameter("dbpass");
%>
<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>방명록</title>
</head>
<body>
<form method="post" action="/hellojsp/views" name="deleteform">
    <input type='hidden' name="no" value="<%= no %>">
    <input type='hidden' name="dbpass" value="<%= dbpass %>">
    <input type='hidden' name="a" value="delete">
    
    <table>
        <tr>
            <td>비밀번호</td>
            <td><input type="password" name="password"></td>
            <td><input type="submit" value="확인"></td>
        </tr>
    </table>
</form>
<a href="/hellojsp/views">메인으로 돌아가기</a>
</body>
</html>