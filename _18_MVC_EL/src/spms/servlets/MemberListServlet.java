package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import spms.vo.Member;

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
//		String driver = sc.getInitParameter("driver");
//		String mySqlUrl = sc.getInitParameter("url");
//		String id = sc.getInitParameter("username");
//		String pwd = sc.getInitParameter("password");
		
		try {
			// 1. MySQL 제어 객체를 로딩
			//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//			Class.forName(driver);
			// 2. MySQL 연결
			//conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			//servletcontext 데이터 보관소에 저장되어 있는 Connection 객체를 꺼내 씀
			conn = (Connection)sc.getAttribute("conn");
			// 3. sql문 객체 생성
			stmt = conn.createStatement();
			// 4. sql 전송 후 결과 값 리턴받기
			rs = stmt.executeQuery(sqlSelect);
			// 5. 결과를 브라우저로 전송
			response.setContentType("text/html;charset=UTF-8");
			ArrayList<Member> members = new ArrayList<Member>();
			
			//DB결과 값을 VO로 담음
			while(rs.next()) {
				members.add(new Member()
										.setNo(rs.getInt("MNO"))
										.setName(rs.getString("MNAME"))
										.setEmail(rs.getString("EMAIL"))
										.setCreateDate(rs.getDate("CRE_DATE")));
			}
			
			//request에 회원 목록 데이터 보관
			request.setAttribute("memberList", members);
			
			//JSP로 출력 -> 화면생성을 jsp로 위임할 때 RequestDispatcher 객체 사용
			RequestDispatcher rd = request.getRequestDispatcher(
					"/member/MemberList.jsp"
			);
			//rd.include(request, response);
			rd.forward(request, response);
		} catch(Exception e) {
			//throw new ServletException(e);
			
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
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
		}
	}

}
