<%@ page import="spms.vo.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//HttpSession 데이터 보관소에 저장된 member 객체 꺼내서 사용
	Member member = (Member)session.getAttribute("Member");
%>
<div style="background-color:#00008b; color:#ffffff; height:20px; padding:5px">
SPMS(Simple Project Management System)
<span style="float:right;">
	<%=member.getName() %>
	<a style="color:white;" href="<%=request.getContextPath() %>/auth/logout">로그아웃</a>
</span>
</div>