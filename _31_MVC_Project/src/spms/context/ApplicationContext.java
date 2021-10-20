package spms.context;

import java.io.FileReader;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.reflections.Reflections;

import spms.annotation.Component;

public class ApplicationContext {
	//application-context.properties ������ �о key���� value�� ����� Hashtable ����
	// key : jndi.dataSource, memberDAO, /auth/login.do .....
	// value : java:comp/env/jdbc/studydb, spms.dao.MemberDAO, spms.controller.LoginCotroller...
	Hashtable<String, Object> objTable = new Hashtable<String, Object>();
	
	// key���� �ش��ϴ� ��ü ��ȯ�ϴ� �޼ҵ�
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	//�����ڴ� ���ϰ��(application-context.properties)�� �о ������Ƽ�� �������ִ� ����
	public ApplicationContext(String propertiesPath) throws Exception {
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		//������Ƽ ������ �о ��ü�� ����� �ִ� �޼ҵ�
		prepareObject(props);
		//������̼����� ����� ��ü ����
		prepareAnnotationObject();
		//������ ���� �޼ҵ�
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
				//key ���� dataSource�� lookup
				objTable.put(key, ctx.lookup(value));
			} else {
				//dataSource �ܿ� ��ü���� ���� ��ü ����
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	public void prepareAnnotationObject() throws Exception {
		//���÷��� ���̺귯�� : ���÷���API���� �� ���� Ŭ������ �޼ҵ带 ã�� �� �ִ�.
		//new Reflections("��Ű����") : �ش� ��Ű���� ������ ��� ã�´�.
		//new Reflections("spms") : spms ��Ű���� ������ ��� ã�´�.
		//new Reflections("spms.controller") : spms.comtroller ��Ű���� ������ ��� ã�´�.
		//new Reflections("") : classpath ������ ��� ã�´�(�����ϵ� Ŭ������ ��� �˻�)
		Reflections reflector = new Reflections("");
		
		//@Component ������̼��� ���� type �����ϵ��� ����
		Set<Class<?>> list = reflector.getTypesAnnotatedWith(Component.class);
		String key = null;
		for(Class<?> clazz : list) {
			//@Component�� ������ ������̼��� value �� ����
			//memberDAO, /auth/login.do, /member/list:do
			key = clazz.getAnnotation(Component.class).value();
			objTable.put(key, clazz.newInstance());
		}
	}
	
	public void injectDependency() throws Exception {
		for(String key : objTable.keySet()) {
			//dataSource�� �ƴϸ� setter�޼ҵ带 ã�Ƽ� DAO�� ��ü�� ����
			if(!key.startsWith("jndi.")) {
				//�Ű������δ� key���� �ش��ϴ� ��ü�� ������
				callSetter(objTable.get(key));
			}
		}
	}
	
	//setter�޼ҵ带 ã�Ƽ� ��������
	private void callSetter(Object obj) throws Exception {
		Object dependency = null;
		//��ü�� �ִ� ��� �޼ҵ���� ȣ���ؼ� �� �߿� setter�޼ҵ带 ã��
		for(Method m : obj.getClass().getMethods()) {
			if(m.getName().startsWith("set")) {
				//setter�޼ҵ带 ã�Ƽ� setter �޼ҵ��� �Ű����� Ÿ������ ��ü�� ã��
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
