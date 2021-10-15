<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <jsp:useBean id="member" scope="request" class="spms.vo.Member"></jsp:useBean>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보</title>
</head>
<body>
	<h1>회원 정보</h1>
	<form action="update" method="post">
		번호 : <input type="text" name="no" value="<%=member.getNo() %>" readonly><br>
		이름 : <input type="text" name="name" value="<%=member.getName() %>"><br>
		이메일 : <input type="text" name="email" value="<%=member.getEmail() %>"><br>
		가입일 : <%=member.getCreateDate() %><br>
		<input type="submit" value="저장">
		<input type="button" value="삭제" onclick="location.href='delete?no<%=member.getNo() %>';">
		<input type="button" value="취소" onclick="location.href='list';">

</body>
</html>