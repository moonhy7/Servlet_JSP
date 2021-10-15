package spms.servlets;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

import spms.dao.MemberDAO;

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
		
		ServletContext sc = this.getServletContext();
		
		try {
			Connection conn = (Connection)sc.getAttribute("conn");
			
			MemberDAO memberDAO = new MemberDAO();
			//DAO객체 connection 주입
			memberDAO.setConnection(conn);
			
			//request에 회원 목록 데이터 보관
			request.setAttribute("memberList", memberDAO.selectlist());
			
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
		}
	}

}
