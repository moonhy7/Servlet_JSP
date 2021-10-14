package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet("/member/list")
public class MemberListServlet extends GenericServlet {
	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//DB 연결
		Connection conn = null;
		//sql문 
		Statement stmt = null;
		//sql문 결과
		ResultSet rs = null;
		
		String sqlSelect = "SELECT * FROM MEMBERS ORDER BY MNO ASC";
		
		ServletContext sc = this.getServletContext();
		
		//mysql 서버 접속정보
		String driver = sc.getInitParameter("driver");
		String mySqlUrl = sc.getInitParameter("url");
		String id = sc.getInitParameter("username");
		String pwd = sc.getInitParameter("password");
		
		try {
			// 1. MySQL 제어 객체를 로딩
			//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			Class.forName(driver);
			// 2. MySQL 연결
			conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			// 3. sql문 객체 생성
			stmt = conn.createStatement();
			// 4. sql 전송 후 결과 값 리턴받기
			rs = stmt.executeQuery(sqlSelect);
			// 5. 결과를 브라우저로 전송
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>회원목록</title></head>");
			out.println("<body><h1>회원 목록</h1>");
			/*
			 *     /add : http://localhost:9999/add 
			 * 	   add  : http://localhost:9999/member/add 
			 * 
			 * */
			out.println("<p><a href='add'>신규 회원</a></p>");
			while(rs.next()) {
				out.println(rs.getInt(1) + ", " +
							"<a href='update?no=" + rs.getInt("MNO") + "'>" +
							rs.getString(2) + "</a>, " +
							rs.getString(3) + ", " +
							rs.getString(4) + "<br>");
			}
			out.println("</body></html>");
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
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
