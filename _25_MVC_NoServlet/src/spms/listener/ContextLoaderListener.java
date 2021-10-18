package spms.listener;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import spms.dao.MemberDAO;

public class ContextLoaderListener implements ServletContextListener {
	//datasource�� ����  Ŀ�ؼ�Ǯ�� ��Ĺ���� �������ֱ� ������ 
	//�����ڰ� ����  connection pool ��ü�� ����� �� �ʿ䰡 ����
	//BasicDataSource ds;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//�� ���ø����̼��� ����Ǹ� �ڵ����� DBĿ�ؼ� ���� �� MemeberDAO��ü ����
		try {
			System.out.println("contextInitialized");
			ServletContext sc = sce.getServletContext();
			
			//��Ĺ �������� �ڿ�(Ŀ�ؼ�)�� ã�� ���� InitailContext ��ü ����
			InitialContext initialcontext = new InitialContext();
			/*
			 * JNDI ���
			 * WAS�� �ڿ��� ���� ���� �̸� ����
			 * ���ø����̼ǿ��� ���� ���ҽ��� ������ �� ����ϴ� ��� ��Ģ(��Ĺ)
			 * �������� ��� ��Ģ�� �ٸ� (���콺�� �Ǵٸ� ��Ģ�� ����)
			 * 
			 * 1) java:comp/env         	 - ���� ���α׷� ȯ�� �׸�
			 * 2) java:comp/env/jdbc    	 - JDBC ������ �ҽ�
			 * 3) java:comp/ejb         	 - EJB ������Ʈ
			 * 4) java:comp/UserTransaction  - UserTransaction ��ý
			 * 5) java:comp/env/mail    	 - JavaMail ���� ��ü
			 * 6) java:comp/env/url     	 - URL ����
			 * 7) java:comp/env/jms     	 - JMS ���� ��ü
			 * 
			 * */
			DataSource ds = (DataSource)initialcontext.lookup("java:comp/env/jdbc/studydb");
			
			/*
			 * ds = new BasicDataSource();
			 * ds.setDriverClassName(sc.getInitParameter("driver"));
			 * ds.setUrl(sc.getInitParameter("url"));
			 * ds.setUsername(sc.getInitParameter("username"));
			 * ds.setPassword(sc.getInitParameter("password"));
			 */
			
			MemberDAO memberDAO = new MemberDAO();
			memberDAO.setDataSource(ds);
			
			//������ MemberDAO��ü�� ServletContext ������ �����Ҹ� ���� �������� ����
			sc.setAttribute("memberDAO", memberDAO);
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
