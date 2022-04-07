<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ page import="hellojsp.GuestBookDaoImpl"%>
<%@ page import="hellojsp.GuestBookVo"%>
<%@ page import="java.time.LocalDate"%>
<%
	request.setCharacterEncoding("UTF-8");
	String name = request.getParameter("name");
	String password = request.getParameter("pass");
	String content = request.getParameter("content");
	String date = LocalDate.now().toString();
	
	GuestBookVo vo = new GuestBookVo(name, password, content, date);
	
	GuestBookDaoImpl dao = new GuestBookDaoImpl();
	dao.insert(vo);
	
	response.sendRedirect("list.jsp");
%>
