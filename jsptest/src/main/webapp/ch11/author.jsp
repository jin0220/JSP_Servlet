<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*, ch11.*"%>
<jsp:useBean id="authorMgr" class="ch11.AuthorMgr"/>
<html>
<head>
<title>JSP에서 데이터베이스 연동</title>
<link href="style.css" rel="stylesheet" type="text/css">
</head>
<body bgcolor="#FFFFCC">
<h2>Bean를 사용한 데이터베이스 연동 예제</h2><br/>
<h3>저자정보</h3>
<table bordercolor="#0000ff" border="1">
<tr>
   <td><strong>저자번호</strong></td>
   <td><strong>저자명</strong></td>
   <td><strong>설명</strong></td>
</tr>
 <%
   Vector<AuthorBean> vlist = authorMgr.getAuthorList();
	int counter = vlist.size();
	for(int i=0; i<vlist.size(); i++){
	  AuthorBean authorBean = vlist.get(i);
 %>
<tr>
	<td><%=authorBean.getAuthorId()%></td>
	<td><%=authorBean.getAuthorName()%></td>
	<td><%=authorBean.getAuthorDesc()%></td>
	
</tr>
<% } %>
</table>
<br/><br/>
total records : <%= counter %> 
</body>
</html>