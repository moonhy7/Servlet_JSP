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
		//DB ����
		Connection conn = null;
		//sql�� 
		Statement stmt = null;
		//sql�� ���
		ResultSet rs = null;
		
		String sqlSelect = "SELECT * FROM MEMBERS ORDER BY MNO ASC";
		
		ServletContext sc = this.getServletContext();
		
		//mysql ���� ��������
//		String driver = sc.getInitParameter("driver");
//		String mySqlUrl = sc.getInitParameter("url");
//		String id = sc.getInitParameter("username");
//		String pwd = sc.getInitParameter("password");
		
		try {
			// 1. MySQL ���� ��ü�� �ε�
			//DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
//			Class.forName(driver);
			// 2. MySQL ����
			//conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			//servletcontext ������ �����ҿ� ����Ǿ� �ִ� Connection ��ü�� ���� ��
			conn = (Connection)sc.getAttribute("conn");
			// 3. sql�� ��ü ����
			stmt = conn.createStatement();
			// 4. sql ���� �� ��� �� ���Ϲޱ�
			rs = stmt.executeQuery(sqlSelect);
			// 5. ����� �������� ����
			response.setContentType("text/html;charset=UTF-8");
			ArrayList<Member> members = new ArrayList<Member>();
			
			//DB��� ���� VO�� ����
			while(rs.next()) {
				members.add(new Member()
										.setNo(rs.getInt("MNO"))
										.setName(rs.getString("MNAME"))
										.setEmail(rs.getString("EMAIL"))
										.setCreateDate(rs.getDate("CRE_DATE")));
			}
			
			//request�� ȸ�� ��� ������ ����
			request.setAttribute("memberList", members);
			
			//JSP�� ��� -> ȭ������� jsp�� ������ �� RequestDispatcher ��ü ���
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
