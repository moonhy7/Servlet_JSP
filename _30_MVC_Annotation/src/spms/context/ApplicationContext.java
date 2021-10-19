package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

public class ApplicationContext {
	//application-context.properties 파일을 읽어서 key값과 value을 담아줄 Hashtable 생성
	// key : jndi.dataSource, memberDAO, /auth/login.do .....
	// value : java:comp/env/jdbc/studydb, spms.dao.MemberDAO, spms.controller.LoginCotroller...
	Hashtable<String, Object> objTable = new Hashtable<String, Object>();
	
	// key값에 해당하는 객체 반환하는 메소드
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	//생성자는 파일경로(application-context.properties)를 읽어서 프로퍼티를 생성해주는 역할
	public ApplicationContext(String propertiesPath) throws Exception {
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		//프로퍼티 파일을 읽어서 객체를 만들어 주는 메소드
		prepareObject(props);
		//의존성 주입 메소드
		injectDependency();
	}
	
	public void prepareObject(Properties props) throws Exception {
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		
		//props.keySet : jndi.dataSource, memberDAO, /auth/login.do .....
		for(Object item : props.keySet()) {
			key = (String)item;
			//props.getProperty : java:comp/env/jdbc/studydb, spms.dao.MemberDAO, spms.controller.LoginCotroller...
			value = props.getProperty(key);
			
			if(key.startsWith("jndi.")) {
				//key 값이 dataSource면 lookup
				objTable.put(key, ctx.lookup(value));
			} else {
				//dataSource 외에 객체들은 직접 객체 생성
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	public void injectDependency() throws Exception {
		for(String key : objTable.keySet()) {
			//dataSource가 아니면 setter메소드를 찾아서 DAO를 객체를 주입
			if(!key.startsWith("jndi.")) {
				//매개변수로는 key값에 해당하는 객체를 보내줌
				callSetter(objTable.get(key));
			}
		}
	}
	
	//setter메소드를 찾아서 실행해줌
	private void callSetter(Object obj) throws Exception {
		Object dependency = null;
		//객체에 있는 모든 메소드들을 호출해서 그 중에 setter메소드를 찾음
		for(Method m : obj.getClass().getMethods()) {
			if(m.getName().startsWith("set")) {
				//setter메소드를 찾아서 setter 메소드의 매개변수 타입으로 객체를 찾음
				//dependeny = MySqlMemberDAO
				dependency = findObjectByType(m.getParameterTypes()[0]);
				if(dependency != null) {
					m.invoke(obj, dependency);
				}
			}
		}
	}
	
	private Object findObjectByType(Class<?> type) {
		for(Object obj : objTable.values()) {
			if(type.isInstance(obj)) {
				return obj;
			}
		}
		return null;
	}
}
