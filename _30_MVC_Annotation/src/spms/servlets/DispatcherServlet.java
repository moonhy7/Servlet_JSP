package spms.servlets;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import spms.bind.DataBinding;
import spms.bind.ServletRequestDataBinders;
import spms.context.ApplicationContext;
import spms.controller.Controller;
import spms.listener.ContextLoaderListener;

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
			//ServletContext sc = this.getServletContext();
			ApplicationContext ctx = ContextLoaderListener.getApplicationContext();
			//���� ��ο� ���� ��������Ʈ�ѷ� ��� ���� ����
			//���� ���ؽ�Ʈ ������ ������ ������ ������ ��Ʈ�ѷ��� ���� ��(Ű �� : servletpath, value : �ش� ��Ʈ�ѷ�(DAO������ ���� ����))
			//Controller pageController = (Controller)sc.getAttribute(servletPath);
			Controller pageController = (Controller)ctx.getBean(servletPath);
			HashMap<String, Object> model = new HashMap<String, Object>();
			model.put("session", request.getSession());
			
			//pageController��ü�� DataBinding�������̽��� ��ӹ޾Ҵٸ�
			//������ �� ��ü�� �����ϴ�  pageController���
			if(pageController instanceof DataBinding) {
				//pageController�� ������ ��ü�� �ڵ� �����Ͽ�
				//�������� ������ �Ķ���͸� ��ü�� �����Ͽ� 
				//model��ü�� ����
				prepareRequestData(request, model, (DataBinding)pageController);
			}
			
			//POJO Page Controller
			String viewUrl = "";
			if(pageController != null) {
				viewUrl = pageController.execute(model);
				for(String key : model.keySet()) {
					request.setAttribute(key, model.get(key));
				}
			} else {
				System.out.println("�ּ� ���  Controller�� ã�� �� �����ϴ�.");
			}
			
			//��ȯ���� ���� redirect�� ���ԵǾ� ������ �ٷ� ������ �̵�
			if(viewUrl.startsWith("redirect:")) {
				response.sendRedirect(viewUrl.substring(9));
				return;
			} else {
				RequestDispatcher rd = request.getRequestDispatcher(viewUrl);
				rd.include(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
	
	private void prepareRequestData(HttpServletRequest request, 
									HashMap<String, Object> model, 
									DataBinding dataBinding) throws Exception {
		Object[] dataBinders = dataBinding.getDataBinders();
		String dataName = null;  //key��
		Class<?> dataType = null;//������ ��ü�� Ŭ���� ����
		Object dataObj = null;   //������ ��ü
		//dataBinder[0] : Ű��, dataBinders[1] : �����
		for(int i = 0; i < dataBinders.length; i+=2) {
			dataName = (String)dataBinders[i];
			dataType = (Class<?>)dataBinders[i+1];
			
			/*
			 * request : �Ű����� ������ ���� ���� ����
			 * dataType : ������ ��ü�� Ŭ���� ���� ����
			 * dataName : �Ű�����(Ű ��)�� �̸�
			 * */
			
			//��ü ����
			dataObj = ServletRequestDataBinders.bind(request, dataType, dataName);
			
			//������� ��ü�� model�� ����
			//model("member", MemberVO);
			model.put(dataName, dataObj);
		}
	}
}
