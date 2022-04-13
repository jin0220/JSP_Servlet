<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="/mysite/assets/css/board.css" rel="stylesheet" type="text/css">
<title>Mysite</title>
</head>
<body>
	<div id="container">
		
		<c:import url="/WEB-INF/views/includes/header.jsp"></c:import>
		<c:import url="/WEB-INF/views/includes/navigation.jsp"></c:import>
		
		<div id="content">
			<div id="board">
				<form id="search_form" action="/mysite/board" method="post">
					<input type="hidden" name="a" value="search" />
					<input type="text" id="kwd" name="kwd" value="">
					<input type="submit" value="찾기">
				</form>
				<table class="tbl-ex">
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>글쓴이</th>
						<th>조회수</th>
						<th>작성일</th>
						<th>&nbsp;</th>
					</tr>				
					<c:forEach items="${list }" var="vo">
						<tr>
							<td>${vo.no }</td>
							<td><a href="/mysite/board?a=read&no=${vo.no }&hit=${vo.hit}">${vo.title }</a></td>
							<td>${vo.userName }</td>
							<td>${vo.hit }</td>
							<fmt:parseDate value="${vo.regDate}" var="dateValue" pattern="yyyy-MM-dd HH:mm:ss"/>
							<td><fmt:formatDate value="${dateValue}" pattern="yy-MM-dd HH:mm"/></td>
							<td>
								<c:if test="${authUser.no == vo.userNo }">
									<a href="/mysite/board?a=delete&no=${vo.no }" class="del">삭제</a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</table>
				<div class="pager">
					<ul>
						<li><a href="/mysite/board?a=list&pageNum=${currentPage-1}">◀</a></li>
						
						<c:forEach var="i" begin="1" end="${totalPage }">
							<c:choose>
								<c:when test = "${i == currentPage}">
					            	<li class="selected">${i}</li>
					         	</c:when>
					         	
					         	<c:otherwise>
					            	<li><a href="/mysite/board?a=list&pageNum=${i}">${i}</a></li>
					         	</c:otherwise>
							</c:choose>
						</c:forEach>
						
						<li><a href="/mysite/board?a=list&pageNum=${currentPage+1}">▶</a></li>
					</ul>
				</div>				
				<c:if test="${authUser != null }">
					<div class="bottom">
						<a href="/mysite/board?a=writeform" id="new-book">글쓰기</a>
					</div>
				</c:if>				
			</div>
		</div>
		
		<c:import url="/WEB-INF/views/includes/footer.jsp"></c:import>
		
	</div><!-- /container -->
</body>
</html>		
		
