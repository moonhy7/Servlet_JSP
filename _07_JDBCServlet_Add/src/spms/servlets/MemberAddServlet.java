package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MemberAddServlet
 */
@WebServlet("/member/add")
//HttpServlet은 GenericServlet을 상속받은 클래스
//service() 메소드를 호출방식에 따라 doGet(), doPost(), doPut(), doDelete()로 분리해놓음
public class MemberAddServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//url에 주소 직접 입력 후 접속, 링크로 걸려있는 주소로 접속 -> get방식
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head><title>회원등록</title></head>");
		out.println("<body><h1>회원등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름 : <input type='text' name='name'><br>");
		out.println("이메일 : <input type='text' name='email'><br>");
		out.println("암호 : <input type='password' name='password'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form></body></html>");
	}
	
	//입력폼에 입력된 정보를 submit할때 method 방식이 post이므로
	//doPost에서 데이터 입력 처리
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sqlInsert = "INSERT INTO MEMBERS(EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE)" + 
						   "VALUES(?, ?, ?, NOW(), NOW())";
		String url = "jdbc:mysql://localhost/studydb?serverTimezone=UTC";
		String id = "study";
		String pw = "study";
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			conn = DriverManager.getConnection(url, id, pw);
			stmt = conn.prepareStatement(sqlInsert);
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("password"));
			stmt.setString(3, request.getParameter("name"));
			stmt.executeUpdate();
			
			//sendRedirect 밑의 결과 값 출력 안하고 바로 /member/list로 이동
			response.sendRedirect("list");
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원등록결과</title>");
			out.println("<meta http-equiv='Refresh' content='1; url=list'></head>");
			out.println("<body><p>등록성공입니다!</p></body></html>");
			
			//응답헤더에 리프레쉬 정보를 추가
			//1초 후에 url=list로 보내라
			//response.addHeader("Refresh", "1;url=list");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(conn != null) {
					conn.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
