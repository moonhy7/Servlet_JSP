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
			//ServletContext ������ �����ҿ� ����Ǿ� �ִ� MemberDAO��ü ������
			//DB Ŀ�ؼ� ���Ա��� �Ϸ�� ������ MemberDAO
			MemberDAO memberDAO = (MemberDAO)sc.getAttribute("memberDAO");
			
			//request�� ȸ�� ��� ������ ����
			request.setAttribute("memberList", memberDAO.selectlist());
			
			request.setAttribute("viewUrl", "/member/MemberList.jsp");
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}

}
