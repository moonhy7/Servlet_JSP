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

import spms.dao.MemberDAO;
import spms.vo.Member;

/**
 * Servlet implementation class MemberAddServlet
 */
@WebServlet("/member/add")
//HttpServlet은 GenericServlet을 상속받은 클래스
//service() 메소드를 호출방식에 따라 doGet(), doPost(), doPut(), doDelete()로 분리해놓음
public class MemberAddServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	//url에 주소 직접 입력 후 접속, 링크로 걸려있는 주소로 접속 -> get방식
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		RequestDispatcher rd = request.getRequestDispatcher("/member/MemberForm.jsp");
		rd.forward(request, response);
//		response.setContentType("text/html;charset=UTF-8");
//		PrintWriter out = response.getWriter();
//		
//		out.println("<html><head><title>회원등록</title></head>");
//		out.println("<body><h1>회원등록</h1>");
//		out.println("<form action='add' method='post'>");
//		out.println("이름 : <input type='text' name='name'><br>");
//		out.println("이메일 : <input type='text' name='email'><br>");
//		out.println("암호 : <input type='password' name='password'><br>");
//		out.println("<input type='submit' value='추가'>");
//		out.println("<input type='reset' value='취소'>");
//		out.println("</form></body></html>");
	}
	
	//입력폼에 입력된 정보를 submit할때 method 방식이 post이므로
	//doPost에서 데이터 입력 처리
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = this.getServletContext();
		
		try {
			Connection conn = (Connection)sc.getAttribute("conn");
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.setConnection(conn);
			
			//input폼에서 받은 파라미터들을 Member객체에 담음
			int result = memberDAO.insert(new Member()
										 			  .setEmail(request.getParameter("email"))
										 			  .setPassword(request.getParameter("password"))
										 			  .setName(request.getParameter("name")));
			
			//sendRedirect 밑의 결과 값 출력 안하고 바로 /member/list로 이동
			//쿼리가 성공했을 경우 list페이지로
			if(result == 1) {
				response.sendRedirect("list");
			}//실패했을 경우 Error페이지로
			else {
				RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
				rd.forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
