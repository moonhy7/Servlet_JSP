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
		request.setAttribute("viewUrl", "/member/MemberForm.jsp");
	}
	
	//입력폼에 입력된 정보를 submit할때 method 방식이 post이므로
	//doPost에서 데이터 입력 처리
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ServletContext sc = this.getServletContext();
		
		try {
			MemberDAO memberDAO = (MemberDAO)sc.getAttribute("memberDAO");
			
			int result = memberDAO.insert((Member)request.getAttribute("member"));
			
			//sendRedirect 밑의 결과 값 출력 안하고 바로 /member/list로 이동
			//쿼리가 성공했을 경우 list페이지로
			if(result == 1) {
				request.setAttribute("viewUrl", "redirect:list.do");
			}//실패했을 경우 Error페이지로
			else {
				RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
				rd.forward(request, response);
			}
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
}
