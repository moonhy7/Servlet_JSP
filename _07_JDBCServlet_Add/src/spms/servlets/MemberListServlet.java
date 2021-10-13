package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
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
		// DB ����
		Connection conn = null;
		//sql���� ���� ��ü
		Statement stmt = null;
		//sql�� �����
		ResultSet rs = null;
		
		String sqlSelect = "SELECT * FROM MEMBERS ORDER BY MNO ASC";
		
		//mysql ���� ��������
		String mySqlUrl = "jdbc:mysql://localhost/studydb?serverTimezone=UTC";
		String id = "study";
		String pwd = "study";
		
		try {
			// 1. MySQL ���� ��ü�� �ε�
			DriverManager.deregisterDriver(new com.mysql.cj.jdbc.Driver()); // � ����̹��� ����
			// 2. MySQL ����
			conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			// 3. sql�� ��ü ����
			stmt = conn.createStatement();
			// 4. sql ���� �� ��� �� ���Ϲޱ�
			rs = stmt.executeQuery(sqlSelect);
			// 5. ����� �������� ����
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>ȸ�����</title></head>");
			out.println("<body><h1>ȸ�� ���</h1>");
			/*
			 *	/add : http://localhost:9999/add
			 * 	add : http://localhost:9999/member/add (������ ������ ��ηκ��� �̵�)
			 * 
			 * */
			out.println("<p><a href='add'>�ű� ȸ��</a></p>");
			out.println("<p><a href='delete'>ȸ�� ����</a></p>");
			while(rs.next()) { // ������� ���� ���� �ϳ��� �޾ƿ�
				//�̸��� ������ �� ������Ʈ �������� �̵�
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
