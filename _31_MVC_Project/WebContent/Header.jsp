<%@ page import="spms.vo.Member" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	//HttpSession 데이터 보관소에 저장된 member 객체 꺼내서 사용
	//Member member = (Member)session.getAttribute("Member");
%>
<div style="background-color:#00008b; color:#ffffff; height:20px; padding:5px">
SPMS(Simple Project Management System)
<span style="float:right;">
	<a style="color:white;" href="<%=request.getContextPath() %>/project/list.do">프로젝트</a>
	<a style="color:white;" href="<%=request.getContextPath() %>/member/list.do">회원</a>
	
	<c:if test="${empty Member or empty Member.email }">
		<a style="color:white;" href="<%=request.getContextPath() %>/auth/login.do">로그인</a>
	</c:if>
	
	<c:if test="${!empty Member and !empty Member.email }">
		${Member.name }
		<a style="color:white;" href="<%=request.getContextPath() %>/auth/logout.do">로그아웃</a>	
	</c:if>
</span>
</div>