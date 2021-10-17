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

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/delete")
public class MemberDeleteServlet extends HttpServlet {
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub		
		try {
			ServletContext sc = this.getServletContext();
			
			Connection conn = (Connection)sc.getAttribute("conn");
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.setConnection(conn);
			
			int result = memberDAO.delete(Integer.parseInt(request.getParameter("no")));
			
			if(result == 1) {
				response.sendRedirect("list");
			} else {
				RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
				rd.forward(request, response);
			}
		} catch(Exception e) {
			e.printStackTrace();
			request.setAttribute("error", e);
			RequestDispatcher rd = request.getRequestDispatcher("/Error.jsp");
			rd.forward(request, response);
		}
	}
}
