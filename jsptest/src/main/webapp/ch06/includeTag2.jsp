<%@page contentType="text/html;charset=UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
	String siteName = request.getParameter("siteName");
%>
<html>
<body>
<h1>Include Tag Example2</h1>
<jsp:include page="includeTagTop2.jsp">
<jsp:param name="siteName" value="JSPStudy.co.kr" />
</jsp:include>
include ActionTag의 Body입니다.<p/>
<b><%=siteName%></b>
<hr/>
</body>
</html>