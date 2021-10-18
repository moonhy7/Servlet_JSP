package spms.listener;

import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import spms.dao.MemberDAO;
import spms.util.DBConnectionPool;

public class ContextLoaderListener implements ServletContextListener {
	// Connection conn; 
	// 원래 만들어놓은 커넥션을 커넥션풀로 만들어주기
	DBConnectionPool connPool;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//웹 어플리케이션이 실행되면 자동으로 DB커넥션 생성 및 MemeberDAO객체 생성
		try {
			System.out.println("contextInitialized");
			ServletContext sc = sce.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
//			conn = DriverManager.getConnection(sc.getInitParameter("url"), 
//											   sc.getInitParameter("username"),
//											   sc.getInitParameter("password"));
			
			connPool = new DBConnectionPool(
											sc.getInitParameter("driver"),
											sc.getInitParameter("url"),
											sc.getInitParameter("username"),
											sc.getInitParameter("password"));
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.setDBConnectionPool(connPool);
			
			//생성된 MemberDAO객체를 ServletContext 데이터 보관소를 통해 서블릿끼리 공유
			sc.setAttribute("memberDAO", memberDAO);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			System.out.println("contextDestroyed");
			connPool.closeAll();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
