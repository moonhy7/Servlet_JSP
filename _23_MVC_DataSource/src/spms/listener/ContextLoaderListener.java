package spms.listener;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;

import spms.dao.MemberDAO;

public class ContextLoaderListener implements ServletContextListener {
	//datasource의 장점  커넥션풀을 톰캣에서 지원해주기 때문에 
	//개발자가 직접  connection pool 객체를 만들어 줄 필요가 없다
	//BasicDataSource ds;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		//웹 어플리케이션이 실행되면 자동으로 DB커넥션 생성 및 MemeberDAO객체 생성
		try {
			System.out.println("contextInitialized");
			ServletContext sc = sce.getServletContext();
			
			//톰캣 서버에서 자원(커넥션)을 찾기 위한 InitailContext 객체 생성
			InitialContext initialcontext = new InitialContext();
			/*
			 * JNDI 사용
			 * WAS의 자원에 대한 고유 이름 정의
			 * 어플리케이션에서 서버 리소스를 접근할 때 사용하는 명명 규칙(톰캣)
			 * 서버마다 명명 규칙은 다름 (제우스는 또다른 규칙이 있음)
			 * 
			 * 1) java:comp/env         	 - 응용 프로그램 환경 항목
			 * 2) java:comp/env/jdbc    	 - JDBC 데이터 소스
			 * 3) java:comp/ejb         	 - EJB 컴포넌트
			 * 4) java:comp/UserTransaction  - UserTransaction 객첵
			 * 5) java:comp/env/mail    	 - JavaMail 연결 객체
			 * 6) java:comp/env/url     	 - URL 정보
			 * 7) java:comp/env/jms     	 - JMS 연결 객체
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
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
