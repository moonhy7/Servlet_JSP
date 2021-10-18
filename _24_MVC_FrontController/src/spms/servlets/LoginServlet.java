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
		//get 방식으로 요청이 오면 로그인 페이지로 이동
		request.setAttribute("viewUrl", "/auth/LoginForm.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			ServletContext sc = this.getServletContext();
			
			MemberDAO memberDAO = (MemberDAO)sc.getAttribute("memberDAO");
			
			Member member = memberDAO.exist(request.getParameter("email"), request.getParameter("password"));
						
			//로그인 성공 시
			if(member != null) {
				HttpSession session = request.getSession();
				session.setAttribute("Member", member);
				request.setAttribute("viewUrl", "redirect:../member/list.do");
			} //로그인 실패 시(member객체가 null일 떄)
			else {
				request.setAttribute("viewUrl", "/auth/LoginFail.jsp");
			}
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

}
