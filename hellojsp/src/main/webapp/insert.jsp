<%
	request.setCharacterEncoding("utf-8");
	String str = request.getParameter("id");
	out.println("<span>" + str + "</span>");
	
	String pw = request.getParameter("password");
	out.println("<span>" + pw + "</span>");
	
	String authorId = request.getParameter("authorId");
	out.println("<span>" + authorId + "</span>");
	
	String button = request.getParameter("button");
	out.println("<span>" + button + "</span>");
%>