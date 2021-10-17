package spms.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

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
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/member/update")
public class MemberUpdateServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		

		//서블릿 컨텍스트 객체 생성
		ServletContext sc = this.getServletContext();
		
		try {
			Connection conn = (Connection)sc.getAttribute("conn");
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.setConnection(conn);
			
			Member member = memberDAO.selectOne(Integer.parseInt(request.getParameter("no")));
			
			request.setAttribute("member", member);
			
			RequestDispatcher rd = request.getRequestDispatcher("/member/MemberUpdateForm.jsp");
			rd.forward(request, response);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//서블릿 컨텍스트 객체 생성
		ServletContext sc = this.getServletContext();
		
		try {
			Connection conn = (Connection) sc.getAttribute("conn");
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.setConnection(conn);

			//member객체 안에 있는 내용으로 업데이트 실행
			int result = memberDAO.update(new Member()
													  .setNo(Integer.parseInt(request.getParameter("no")))
													  .setName(request.getParameter("name"))
													  .setEmail(request.getParameter("email")));
			
			if(result == 1) {
				response.sendRedirect("list");
			} else { //실패했을 경우 Error페이지로
				RequestDispatcher rd = request.getRequestDispatcher("Error.jsp");
				rd.forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
