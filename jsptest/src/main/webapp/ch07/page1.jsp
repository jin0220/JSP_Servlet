<%@ page info = "12345252345"
    contentType="text/html;charset=UTF-8"%>
<% 
       String pageInfo = this.getServletInfo();
%>
<h1>Page Example1</h1>
현재 페이지의 info값 : <%=pageInfo%>