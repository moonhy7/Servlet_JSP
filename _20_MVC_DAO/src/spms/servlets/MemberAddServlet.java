package spms.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDAO;
import spms.vo.Member;

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
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
		rd.forward(request, response);
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		
//		out.println("<html><head><title>ȸ�����</title></head>");
//		out.println("<body><h1>ȸ�����</h1>");
//		out.println("<form action='add' method='post'>");
//		out.println("�̸� : <input type='text' name='name'><br>");
//		out.println("�̸��� : <input type='text' name='email'><br>");
//		out.println("��ȣ : <input type='password' name='password'><br>");
//		out.println("<input type='submit' value='�߰�'>");
//		out.println("<input type='reset' value='���'>");
//		out.println("</form></body></html>");
	}
	
	//�Է����� �Էµ� ������ submit�Ҷ� method ����� post�̹Ƿ�
	//doPost���� ������ �Է� ó��
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = this.getServletContext();
		
		try {
			Connection conn = (Connection)sc.getAttribute("conn");
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.setConnection(conn);
			
			//input������ ���� �Ķ���͵��� Member��ü�� ����
			int result = memberDAO.insert(new Member()
										 			  .setEmail(request.getParameter("email"))
										 			  .setPassword(request.getParameter("password"))
										 			  .setName(request.getParameter("name")));
			
			//sendRedirect ���� ��� �� ��� ���ϰ� �ٷ� /member/list�� �̵�
			//������ �������� ��� list��������
			if(result == 1) {
				response.sendRedirect("list");
			}//�������� ��� Error��������
			else {
				RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
				rd.forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
