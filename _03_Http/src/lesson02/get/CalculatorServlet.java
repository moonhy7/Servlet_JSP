package lesson02.get;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class CalculatoreServlet
 */
@WebServlet("/CalculatorServlet")
public class CalculatorServlet extends HttpServlet {
	private Hashtable<String, Operator> opTable = new Hashtable<String, Operator>();
	
	public CalculatorServlet() {
		opTable.put("+", new PlusOperator());
		opTable.put("-", new MinusOperator());
		opTable.put("*", new MultipleOperator());
		opTable.put("/", new DivOperator());
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("get 요청");
		process(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("post 요청");
		process(request, response);
	}
	
	public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 클라이언트가 보내온 변수값 추출
		String op = request.getParameter("op");
		double v1 = Double.parseDouble(request.getParameter("v1"));
		double v2 = Double.parseDouble(request.getParameter("v2"));
		
		response.setContentType("text/html;charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		
		out.println("<html><body>");
		out.println("<h1>계산결과</h1>");
		out.println("결과 : ");
		
		try {
			Operator operator = opTable.get(op);
			if(operator == null) {
				out.println("존재하지 않는 연산자");
			} else {
				double result = operator.execute(v1, v2);
				out.println(String.format("%.3f", result));
			}
		} catch(Exception e) {
			out.println("연산 오류 발생");
			out.println(e.getMessage());
		}
		
		out.println("</body></html>");
	}
}
