package spms.listener;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import spms.dao.MemberDAO;

public class ContextLoaderListener implements ServletContextListener {
	Connection conn;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//웹 애플리케이션이 실행되면 자동으로 DB커넥션 생성 및 MemberDAO객체 생성
		try {
			System.out.println("contextInitialized");
			ServletContext sc = sce.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(sc.getInitParameter("url"),
											   sc.getInitParameter("username"),
											   sc.getInitParameter("password"));
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.setConnection(conn);
			
			//생성된 MemberDAO객체를 ServletContext 데이터 보관소를 통해 서블릿끼리 공유
			//MemberDAO를 만들어서 ServletContext 데이터 보관소를 통해서 저장하고 
			//저장된  MemberDAO를 사용해서 더이상 서블릿에서 MemberDAO를 생성하는 일이 없도록 만듬
			sc.setAttribute("memberDAO", memberDAO);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override // 커넥션 끊어주기
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			//웹 애플리케이션이 종료될 때 DB커넥션이 살아있으면 끊어줌
			if(conn != null) {
				conn.close();
			}
			System.out.println("contextDestroyed");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
