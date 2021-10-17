package spms.listener;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import spms.dao.MemberDAO;
import spms.util.DBConnectionPool;

public class ContextLoaderListener implements ServletContextListener {
	// Connection conn; 
	// ���� �������� Ŀ�ؼ��� Ŀ�ؼ�Ǯ�� ������ֱ�
	DBConnectionPool connPool;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//�� ���ø����̼��� ����Ǹ� �ڵ����� DBĿ�ؼ� ���� �� MemeberDAO��ü ����
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
			
			//������ MemberDAO��ü�� ServletContext ������ �����Ҹ� ���� �������� ����
			sc.setAttribute("memberDAO", memberDAO);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		try {
			//�� ���ø����̼��� ����� �� DBĿ�ؼ��� ��������� ������
			if(conn != null) {
				conn.close();
			}
			System.out.println("contextDestroyed");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
