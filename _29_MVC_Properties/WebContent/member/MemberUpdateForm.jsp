<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
</head>
<body>
	<!-- EL(Expression Language)
		 : 데이터 보관소들에 들어있는 객체나, 리스트 또는 맵 등에 접근할 수 있다.
		   actiontag를 사용하는 것보다 훨씬 간편하게 사용할 수 있어서 자주 사용됨
		   사용법1 : ${member.no} or ${member["no"]}
		   따로 scope를 지정하지 않기 때문에 jspContext -> ServletRequest -> HttpSession -> ServletContext순으로 데이터 보관소 검색
		   사용법 2 : ${requestScope.member.no} or ${requestScope.member["no"]}
		  scope 지정방식
	 -->
	<h1>회원 정보</h1>
	<form action="update.do" method="post">
		번호 : <input type="text" name="no" value="${member.no }" readonly><br>
		이름 : <input type="text" name="name" value="${member.name }"><br>
		이메일 : <input type="text" name="email" value="${member.email }"><br>
		가입일 : ${member.createDate }<br>
		<input type="submit" value="저장">
		<input type="button" value="삭제" onclick="location.href='delete.do?no=${member.no }';">
		<input type="button" value="취소" onclick="location.href='list.do';">
	</form> 
</body> 
</html>