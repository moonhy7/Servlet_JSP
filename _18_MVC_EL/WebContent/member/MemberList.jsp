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
	<!-- jsp action tag
		jsp:useBean, jsp:setProperty, jsp:getProperty, jsp:forword, jsp:include.....
		actiontag를 이용하면 jsp의 자바 소스를 줄일 수 있다
	 -->
	<jsp:include page="/Header.jsp"/>
	<h1>회원 목록</h1>
	<p><a href="add">신규 회원</a></p>
	<!-- servletrequest 데이터 보관소에서 memberList라는 객체를 꺼내옴 -->
	<!-- id : 사용할 객체 이름 -->
	<!-- scope : 어느 데이터 보관소에서 객체를 꺼내올지 결정
	 			 request(servletrequest 데이터 보관소), 
	 			 session(HttpSession 데이터 보관소), 
	 			 application(Servletcontext 데이터 보관소) -->
	<jsp:useBean id="memberList" 
				 scope="request" 
				 class="java.util.ArrayList" 
				 type="java.util.ArrayList<spms.vo.Member>"></jsp:useBean>
	<%
		//MemberListServlet에서  request.setAttribute로 전달한 결과 값 꺼내쓰기
		//ArrayList<Member> members = (ArrayList<Member>)request.getAttribute("memberList");
		for(Member member : memberList) {
	%>
	<%=member.getNo() %>,
	<a href="update?no=<%=member.getNo()%>"><%=member.getName() %></a>,
	<%=member.getEmail() %>,
	<%=member.getCreateDate() %>
	<a href="delete?no=<%=member.getNo() %>">삭제</a><br>
	<%} %>
	<jsp:include page="/Tail.jsp"/>
</body>
</html>