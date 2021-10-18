<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
</head>
<body>
	<!-- JSTL(JSP Standard Tag Library) : JSP 확장 라이브러리로 리스트나 배열 표출 시 자주 사용
										  JSP에서 기본적으로 제공해주지 않기 때문에 taglib 링크를 걸어서 사용
										  c:out : 출력문을 위한 태그
										  			c:out value="출력할 값"
										  c:if : 조건문을 위한 태그
										  		 c:if test="조건" var="변수명" scope="request|session|application"
										  c:choose : case-when 문법
										  			 c:choose
										  			 	c:when test="참/거짓"
										  			 	c:when test="참/거짓"
										  			 	....
										  c:foreach : 반복적인 작업을 위한 태그
										  			  c:foreach var="변수명" items="목록데이터" begin="시작인덱스" end="종료인덱스"-->
	<jsp:include page="/Header.jsp"/>
	<h1>회원 목록</h1>
	<p><a href="add.do">신규 회원</a></p>
	<!-- memberList라는 객체를 데이터 저장소에서 꺼냄
	     foreach문에서 memberList에 담겨있는 내용을 member라는 변수로 담아서 하나씩 사용하겠다 -->
	<c:forEach var="member" items="${memberList }">
	${member.no },
	<a href="update.do?no=${member.no }">${member.name }</a>,
	${member.email },
	${member.createDate }
	<a href="delete.do?no=${member.no }">삭제</a><br>
	</c:forEach>
	<jsp:include page="/Tail.jsp"/>
</body>
</html>