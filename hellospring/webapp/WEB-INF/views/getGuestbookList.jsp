<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
  
  <table border=1 cellpadding=0 cellspacing=0>
    <tr>
      <th>no</th>
      <th>name</th>
      <th>password</th>
      <th>content</th>
      <th>reg_date</th>
    </tr>
    
    <c:forEach items="${guestbookList}" var="guestbook">
    <tr>
      <td>${guestbook.no}</td>
      <td>${guestbook.name}</td>
      <td>${guestbook.password}</td>
      <td>${guestbook.content}</td>
      <td>${guestbook.regDate}</td>
    </tr>
    </c:forEach>
      
  </table>
</body>
</html>