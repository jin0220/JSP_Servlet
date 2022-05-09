<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/assets/css/jblog.css">
</head>
<body>
	<div class="center-content">

		<!-- 메인해더 -->
		<a href=""> <img class="logo"
			src="${pageContext.request.contextPath}/assets/images/logo.jpg">
		</a>
		<ul class="menu">
			
			<c:choose>
				<c:when test="${empty authUser }">
					<li><a href="user/login">로그인</a></li>
					<li><a href="">회원가입</a></li>
				</c:when>

				<c:otherwise>
					<li><a href="">로그아웃</a></li>
					<li><a href="">내블로그</a></li>
				</c:otherwise>
			</c:choose>

		</ul>

		<form class="login-form" method="POST" action="/jblog/user/login">
			<label>아이디</label> <input type="text" name="id"> <label>패스워드</label>
			<input type="text" name="password">

			<c:if test="${param.result == 'fail'}">
				<p class="form-error">
					로그인 실패<br> 아이디/패스워드를 확인해 주세요
				</p>
			</c:if>

			<input type="submit" value="로그인">
		</form>

	</div>
</body>

</html>