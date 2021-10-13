package lesson03.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.GenericServlet;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;

/**
 * Servlet implementation class Calculator
 */
@WebServlet("/calc") // 우선 anotation은 주석처리할 것
public class Calculator extends GenericServlet {
	/**
	 * @see Servlet#service(ServletRequest request, ServletResponse response)
	 */
	@Override
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int a = Integer.parseInt(request.getParameter("a"));
		int b = Integer.parseInt(request.getParameter("b"));
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("a = " + a + ", b = " + b + "의 계산결과");
		out.println("a + b = " + (a + b));
		out.println("a - b = " + (a - b));
		out.println("a * b = " + (a * b));
		out.println("a / b = " + (a / b));
		out.println("a % b = " + (a % b));
	}

}
