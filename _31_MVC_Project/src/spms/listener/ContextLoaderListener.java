package spms.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import spms.context.ApplicationContext;

public class ContextLoaderListener implements ServletContextListener {
	//datasource의 장점  커넥션풀을 톰캣에서 지원해주기 때문에 
	//개발자가 직접  connection pool 객체를 만들어 줄 필요가 없다
	//BasicDataSource ds;
	static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//웹 어플리케이션이 실행되면 자동으로 DB커넥션 생성 및 MemeberDAO객체 생성
		try {
			System.out.println("contextInitialized");
			ServletContext sc = sce.getServletContext();
			
			String propertiesPath = sc.getRealPath(sc.getInitParameter("contextConfigLocation"));
			
			applicationContext = new ApplicationContext(propertiesPath);
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			System.out.println("contextDestroyed");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
