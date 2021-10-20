package spms.listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import spms.context.ApplicationContext;

public class ContextLoaderListener implements ServletContextListener {
	//datasource�� ����  Ŀ�ؼ�Ǯ�� ��Ĺ���� �������ֱ� ������ 
	//�����ڰ� ����  connection pool ��ü�� ����� �� �ʿ䰡 ����
	//BasicDataSource ds;
	static ApplicationContext applicationContext;
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//�� ���ø����̼��� ����Ǹ� �ڵ����� DBĿ�ؼ� ���� �� MemeberDAO��ü ����
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
