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
import javax.servlet.http.HttpSession;

import spms.dao.MemberDAO;
import spms.vo.Member;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/auth/login")
public class LoginServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//get ������� ��û�� ���� �α��� �������� �̵�
		RequestDispatcher rd = request.getRequestDispatcher("/auth/LoginForm.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String sqlLogin = "SELECT MNAME, EMAIL FROM MEMBERS WHERE EMAIL=? AND PWD=?";
		
		try {
			ServletContext sc = this.getServletContext();
			
			MemberDAO memberDAO = (MemberDAO)sc.getAttribute("memberDAO");
			
			Member member = memberDAO.exist(request.getParameter("email"), request.getParameter("password"));
						
			//�α��� ���� ��
			if(member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("Member", member);
				response.sendRedirect("../member/list");
			} //�α��� ���� ��(member��ü�� null�� ��)
			else {
				RequestDispatcher rd = request.getRequestDispatcher("/auth/LoginFail.jsp");
				rd.forward(request, response);
			}
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

}
