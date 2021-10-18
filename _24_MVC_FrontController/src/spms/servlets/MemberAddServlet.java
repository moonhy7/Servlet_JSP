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
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");
	}
	
	//�Է����� �Էµ� ������ submit�Ҷ� method ����� post�̹Ƿ�
	//doPost���� ������ �Է� ó��
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = this.getServletContext();
		
		try {
			MemberDAO memberDAO = (MemberDAO)sc.getAttribute("memberDAO");
			
			int result = memberDAO.insert((Member)request.getAttribute("member"));
			
			//sendRedirect ���� ��� �� ��� ���ϰ� �ٷ� /member/list�� �̵�
			//������ �������� ��� list��������
			if(result == 1) {
				request.setAttribute("viewUrl", "redirect:list.do");
			}//�������� ��� Error��������
			else {
				RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
				rd.forward(request, response);
			}
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
}
