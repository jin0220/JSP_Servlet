 <%@ page contentType="text/html;charset=UTF-8"%>
 <h1>Scriptlet Example1</h1>
<%!
   int one;
   String msgOne;
%>
<%
  int two = 31;
  String msgTwo = "Scriptlet Example";
%>
<%= two + " : " + msgTwo%><br/>
<%=application.getRealPath("/")%>