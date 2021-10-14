<%@ page import="spms.vo.Member"%>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 목록</title>
</head>
<body>
	<h1>회원 목록</h1>
	<p><a href="add">신규 회원</a></p>
	<%
		//MemberListServlet에서 request.setAttribute로 전달한 결과 값 꺼내쓰기
		ArrayList<Member> members = (ArrayList<Member>)request.getAttribute("memberList");
		for(Member member : members) {
	%>
	<%=member.getNo() %>,
	<a href="update?no=<%=member.getNo()%>"><%=member.getName() %></a>,
	<%=member.getEmail() %>,
	<%=member.getCreateDate() %>
	<a href="delete?no=<%=member.getNo()%>">삭제</a><br>
	<%} %>
	
</body>
</html>