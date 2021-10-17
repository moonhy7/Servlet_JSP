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
			//ServletContext 데이터 보관소에 저장되어 있는 MemberDAO객체 꺼내옴
			//DB 커넥션 주입까지 완료된 상태의 MemberDAO
			MemberDAO memberDAO = (MemberDAO)sc.getAttribute("memberDAO");
			
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
