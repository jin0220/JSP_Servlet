<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<sql:setDataSource
	url="jdbc:oracle:thin:@localhost:1521:xe"
	driver="oracle.jdbc.driver.OracleDriver" user="webdb" password="1234" var="db"
	scope="application" />
<html>
<head>
<title>JSTL</title>
</head>
<body>
	<h2>SQL Tags1</h2>
	   <sql:query var="lists" dataSource="${db}">
        select id, name from tblMember
       </sql:query>

    <c:forEach var="member" items="${lists.rows}">
        ${member}<br/>
    </c:forEach>
</body>
</html>