package spms.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.vo.Member;

/**
 * Servlet implementation class DispatcherServlet
 */
//��� .do url ȣ�⿡ ���� ó���� ����Ʈ��Ʈ�ѷ��� Dispatcher���� ó��
/*
 * 1) �ǹ� : tomcat���κ��� �ٷ� ���� ��Ʈ�ѷ��� �����ϴ� ���
 * 			-> tomcat�� FrontController�� ��û�� ����
 * 			   FrontController���� �� PageController�б��ؼ� ó���ϵ���
 * 			   FrontController : DispatcherServlet
 * 			   BackController(PageController) : �� ������ �� ����
 * 
 * 2) ���� : ����� �ߺ��ڵ带 �� ���� ��Ƽ� �����ϱ� ���ϰ� �ϱ� ���� -> �����ӿ�ũ�� ���� ����
 *          FrontController�� HttpServlet�� ��ӹް�
 *          PageController���� �Ϲ� �ڹ� Ŭ������ ��ȯ
 *          
 * 3) FrontController�� ����
 *    3-1) ��û�� ���� �������� �б� ó��
 *    3-2) ������ ��Ʈ�ѷ��� ������ VO ��ü ����
 *    3-3) ����/JSP ������ �̵� ó��
 *    3-4) PageController���� �߻��ϴ� ��� ���� ó��
 *    3-5) �ű� �������� ���� ������ ����
 *    
 * 4) ����
 * 	  4-1) FrontController�� HttpServlet�� ��ӹޱ� ������ �ٸ� WAS�� �̽��� �� FrontController�� �����ϸ� ��
 *    4-2) �������� �����ϰ� ���� ���� ������ ����
 *    4-3) ���뿪���� FrontController�� ��Ƴ��� ������ �ڵ�ȭ(Frameworkȭ)�ϱ� ����
 * 
 * */
@WebServlet("*.do")
public class DispatcherServlet extends HttpServlet {
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		
		//�б� ó���� ���� servlet(��ûurl) ��� ����
		String servletPath = request.getServletPath();
		try {
			//���� ��ο� ���� ��������Ʈ�ѷ� ��� ���� ����
			String pageControllerPath = null;
			
			if("/member/list.do".equals(servletPath)) {
				pageControllerPath = "/member/list";
			} else if("/member/add.do".equals(servletPath)) {
				pageControllerPath = "/member/add";
				
				//���ο� �Է°��� ������ doPost�� ������ doGet ó��
				if(request.getParameter("email") != null) {
					request.setAttribute("member", new Member()
															   .setEmail(request.getParameter("email"))
															   .setPassword(request.getParameter("password"))
															   .setName(request.getParameter("name")));
				}
			} else if("/member/update.do".equals(servletPath)) {
				pageControllerPath = "/member/update";
				
				//���ο� �Է°��� ������ doPost�� ������ doGet ó��
				if(request.getParameter("email") != null) {
					request.setAttribute("member", new Member()
																.setNo(Integer.parseInt(request.getParameter("no")))
							   									.setEmail(request.getParameter("email"))
							   									.setName(request.getParameter("name")));
				}
			} else if("/member/delete.do".equals(servletPath)) {
				pageControllerPath = "/member/delete";
			} else if("/auth/login.do".equals(servletPath)) {
				pageControllerPath = "/auth/login";
			} else if("/auth/logout.do".equals(servletPath)) {
				pageControllerPath = "/auth/logout";
			}
			
			//��������Ʈ�ѷ��� ����
			RequestDispatcher rd = request.getRequestDispatcher(pageControllerPath);
			rd.include(request, response);
			
			//��������Ʈ�ѷ����� ��ȯ���� JSP�������� ����
			String viewUrl = (String) request.getAttribute("viewUrl");
			
			//��ȯ���� ���� redirect�� ���ԵǾ� ������ �ٷ� ������ �̵�
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
