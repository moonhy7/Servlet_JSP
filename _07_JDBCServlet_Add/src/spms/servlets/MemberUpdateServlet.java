package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class MemberUpdateServlet
 */
//@WebServlet("/MemberUpdateServlet")
public class MemberUpdateServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String no = request.getParameter("no");
		String sqlSelect = "SELECT * FROM MEMBERS WHERE MNO = " + no;
		
		//���� �ʱ�ȭ �Ű����� ���� ���� -> getIninParameter�޼ҵ�� ����
		String driver = this.getInitParameter("driver");
		String url = this.getInitParameter("url");
		String id = this.getInitParameter("username");
		String pwd = this.getInitParameter("password");
		
		try {
			//mysql ���� ��ü �ε�
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pwd);
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			rs.next();
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>ȸ������</title></head>");
			out.println("<body><h1>ȸ������</h1>");
			out.println("<form action='update' method='post'>");
			out.println("��ȣ : <input type='text' name='no' value='" + no + "' readonly> <br>");
			out.println("�̸� : <input type='text' name='name' value='" + rs.getNString("mname") + "'> <br>");
			out.println("�̸��� : <input type='text' name='email' value='" + rs.getNString("email") + "'> <br>");
			out.println("������ : " + rs.getDate("cre_date") + "<br>");
			out.println("<input type='submit' value='����'>");
			out.println("<input type='button' value='���' onclick='location.href=\"list\"'>");
			out.println("</form></body></html>");
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		
		Connection conn = null;
		PreparedStatement stmt = null;
		String sqlUpdate = "UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=NOW() WHERE MNO=?";
		
		String driver = this.getInitParameter("driver");
		String url = this.getInitParameter("url");
		String id = this.getInitParameter("username");
		String pwd = this.getInitParameter("password");
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, id, pwd);
			stmt = conn.prepareStatement(sqlUpdate);
			stmt.setString(1, request.getParameter("email"));
			stmt.setString(2, request.getParameter("name"));
			stmt.setInt(3, Integer.parseInt(request.getParameter("no")));
			stmt.executeUpdate();
			
			response.sendRedirect("list");
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
