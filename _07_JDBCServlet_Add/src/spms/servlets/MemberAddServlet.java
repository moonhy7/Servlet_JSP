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
//HttpServlet�� GenericServlet�� ��ӹ��� Ŭ����
//service() �޼ҵ带 ȣ���Ŀ� ���� doGet(), doPost(), doPut(), doDelete()�� �и��س���
public class MemberAddServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//url�� �ּ� ���� �Է� �� ����, ��ũ�� �ɷ��ִ� �ּҷ� ���� -> get���
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		out.println("<html><head><title>ȸ�����</title></head>");
		out.println("<body><h1>ȸ�����</h1>");
		out.println("<form action='add' method='post'>");
		out.println("�̸� : <input type='text' name='name'><br>");
		out.println("�̸��� : <input type='text' name='email'><br>");
		out.println("��ȣ : <input type='password' name='password'><br>");
		out.println("<input type='submit' value='�߰�'>");
		out.println("<input type='reset' value='���'>");
		out.println("</form></body></html>");
	}
	
	//�Է����� �Էµ� ������ submit�Ҷ� method ����� post�̹Ƿ�
	//doPost���� ������ �Է� ó��
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
			
			//sendRedirect ���� ��� �� ��� ���ϰ� �ٷ� /member/list�� �̵�
			response.sendRedirect("list");
			
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>ȸ����ϰ��</title>");
			out.println("<meta http-equiv='Refresh' content='1; url=list'></head>");
			out.println("<body><p>��ϼ����Դϴ�!</p></body></html>");
			
			//��������� �������� ������ �߰�
			//1�� �Ŀ� url=list�� ������
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
