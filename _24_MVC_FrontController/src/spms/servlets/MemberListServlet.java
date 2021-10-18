package spms.servlets;

import java.io.IOException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.dao.MemberDAO;

/**
 * Servlet implementation class MemberListServlet
 */
@WebServlet("/member/list")
public class MemberListServlet extends HttpServlet {
	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		ServletContext sc = this.getServletContext();
		
		try {
			//ServletContext 데이터 보관소에 저장되어 있는 MemberDAO객체 꺼내옴
			//DB 커넥션 주입까지 완료된 상태의 MemberDAO
			MemberDAO memberDAO = (MemberDAO)sc.getAttribute("memberDAO");
			
			//request에 회원 목록 데이터 보관
			request.setAttribute("memberList", memberDAO.selectlist());
			
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

}
