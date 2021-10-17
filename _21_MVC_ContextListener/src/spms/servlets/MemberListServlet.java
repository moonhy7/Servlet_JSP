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
			//ServletContext ������ �����ҿ� ����Ǿ� �ִ� MemberDAO��ü ������
			//DB Ŀ�ؼ� ���Ա��� �Ϸ�� ������ MemberDAO
			MemberDAO memberDAO = (MemberDAO)sc.getAttribute("memberDAO");

			//request�� ȸ�� ��� ������ ����
			request.setAttribute("memberList", memberDAO.selectlist());
			
			//JSP�� ��� -> ȭ������� jsp�� ������ �� RequestDispatcher ��ü ���
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
