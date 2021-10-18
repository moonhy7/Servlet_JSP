package spms.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class AppInitServlet
 */
//@WebServlet("/AppInitServlet")
public class AppInitServlet extends HttpServlet {
	@Override
	public void init(ServletConfig config) throws ServletException {
		System.out.println("AppIninServlet 준비...");
		super.init(config);
				
		try {
			ServletContext sc = this.getServletContext();
			
			String driver = sc.getInitParameter("driver");
			String mySqlUrl = sc.getInitParameter("url");
			String id = sc.getInitParameter("username");
			String pwd = sc.getInitParameter("password");
			
			Class.forName(driver);
			Connection conn = DriverManager.getConnection(mySqlUrl, id, pwd);
			//servletcontext 데이터 보관소에 mysql 접속 객체를 저장
			sc.setAttribute("conn", conn);
		} catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	@Override
	public void destroy() {
		System.out.println("AppInitServlet 마무리..");
		super.destroy();
		
		//servletcontext 데이터 보관소에 저장되어 있는 mysql 접속 객체 꺼내옴
		Connection conn = (Connection)this.getServletContext().getAttribute("conn");
		
		try {
			if(conn != null && conn.isClosed() == false) {
				conn.close();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
