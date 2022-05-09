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
<script type="text/javascript"
	src="${pageContext.request.contextPath }/assets/js/jquery/jquery-1.12.4.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#btn-checkid').on('click', function() {
			console.log("check");
			if ($('#id').val() != '') {
				$.ajax({
					type : 'POST',
					url : '/jblog/user/idcheck',
					data : {
						"id" : $("#id").val()
					},
					dataType : 'json',
					success : function(isExist) {
						if (isExist == false) {
							console.log("t");
							$("#checkid-msg").text("사용할 수 있는 아이디 입니다.");
							$("#checkid-msg").css("color", "green");
						} else {
							console.log("f");
							$("#checkid-msg").text("다른 아이디로 가입해 주세요.");
							$("#checkid-msg").css("color", "red");
						}
					},
					error : function(a, b, c) {
						console.log(a, b, c);
					}
				});
			}
		});
	});
</script>

</head>
<body>
	<div class="center-content">

		<!-- 메인해더 -->
		<a href=""> <img class="logo"
			src="${pageContext.request.contextPath}/assets/images/logo.jpg">
		</a>
		<ul class="menu">
			<!-- 로그인 전 메뉴 -->
			<li><a href="">로그인</a></li>
			<li><a href="">회원가입</a></li>

			<!-- 로그인 후 메뉴 -->
			<!-- 
				<li><a href="">로그아웃</a></li>
				<li><a href="">내블로그</a></li> 
				-->
		</ul>

		<form class="join-form" id="join-form" method="POST"
			action="/jblog/user/join">
			<label class="block-label" for="name">이름</label> <input type="text"
				name="userName" value="" /> <label class="block-label" for="id">아이디</label>
			<input type="text" id="id" name="id" value="" /> <input
				id="btn-checkid" type="button" value="id 중복체크">
			<p id="checkid-msg" class="form-error">&nbsp;</p>

			<label class="block-label" for="password">패스워드</label> <input
				type="password" name="password" value="" />

			<fieldset>
				<legend>약관동의</legend>
				<input id="agree-prov" type="checkbox" name="agreeProv" value="y">
				<label class="l-float">서비스 약관에 동의합니다.</label>
			</fieldset>

			<input type="submit" value="가입하기">

		</form>
	</div>

</body>



</html>