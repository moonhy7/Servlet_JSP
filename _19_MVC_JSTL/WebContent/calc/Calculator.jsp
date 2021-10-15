<!-- jsp 지시자 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!-- 자바소스 (스크립트릿)
	 _jspServcie 내부에서  html보다 먼저 처리
-->
<%
	String v1 = "";
	String v2 = "";
	String result = "";
	String[] selected = {"", "", "", ""};
	
	if(request.getParameter("v1") != null) {
		v1 = request.getParameter("v1");
		v2 = request.getParameter("v2");
		String op = request.getParameter("op");
		
		result = calculate(Integer.parseInt(v1), Integer.parseInt(v2), op);
		
		switch(op) {
		case "+" : selected[0] = "selected"; break;
		case "-" : selected[1] = "selected"; break;
		case "*" : selected[2] = "selected"; break;
		case "/" : selected[3] = "selected"; break;
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>계산기</title>
</head>
<body>
	<h2>jsp 계산기</h2>
	<form action="Calculator.jsp" method="post">
		<input type="text" name="v1" size="4" value="<%=v1%>">
		<select name="op">
			<option value="+ <%=selected[0] %>">+</option>
			<option value="- <%=selected[1] %>">-</option>
			<option value="* <%=selected[2] %>">*</option>
			<option value="/ <%=selected[3] %>">/</option>
		</select>
		<input type="text" name="v2" size="4" value="<%=v2 %>">
		<input type="submit" value="=">
		<input type="text" size="8" value="<%=result %>">
	</form>
</body>
</html>

<!-- 선언문, _jspService 바깥에 메서드, 필드를 선언하는 곳(가장 먼저 처리) -->
<!-- 선언문 : _jspService외부에 필드/메소드를 등록하기 위한 java코드 -->
<%!
	private String calculate(int v1, int v2, String op) {
		int result = 0;
		switch(op) {
		case "+" : result = v1 + v2; break;
		case "-" : result = v1 - v2; break;
		case "*" : result = v1 * v2; break;
		case "/" : result = v1 / v2; break;
		}
		return Integer.toString(result);
	}
%>