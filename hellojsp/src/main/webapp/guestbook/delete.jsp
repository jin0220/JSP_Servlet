<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="hellojsp.GuestBookVo"%>
<%@page import="hellojsp.GuestBookDaoImpl"%>
<%
	request.setCharacterEncoding("utf-8");

	int no = Integer.parseInt(request.getParameter("no"));
	String name = request.getParameter("id");
	String password = request.getParameter("password");

	GuestBookVo vo = new GuestBookVo(no, name, password);
	
	GuestBookDaoImpl dao = new GuestBookDaoImpl();
	dao.delete(vo);
	
	response.sendRedirect("list.jsp");
%>