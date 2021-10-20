package spms.bind;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

//Ŭ���̾�Ʈ�� ���� �Ű����� ���� ��ü�� ����ִ� Ŭ����
public class ServletRequestDataBinders {
	public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception {
		//�����ؾ� �� ����� PrimitiveType�� ���
		if(isPrimitiveType(dataType)) {
			return createValueObject(dataType, request.getParameter(dataName));
		} //dataType�� �Ϲ� VO��ü�� ���
		else {
			//�������� ���� �Ű��������� �̸��� set�� ����
			//email, password, no, name....�� Set�� StringŸ������ ���� ��
			Set<String> paramNames = request.getParameterMap().keySet();
			//Ŭ���� Ÿ�Դ�� ��ü ����
			//���÷��� API newInstance() : �ش� Ÿ���� Ŭ���� �ν��Ͻ� ����
			//dataType == spms.vo.Member => dataObject = spms.vo.Member;
			Object dataObject = dataType.newInstance();
			//�������� ���� �Ű������� ��ü�� �ʵ带 ã�Ƽ� ����
			//setter�� ã�Ƽ� ����� Method ���� 
			Method m = null; //VO�� setter ���� ����
			for(String paramName : paramNames) {
				//�Ű������� �ش��ϴ� setter �޼ҵ� ã��
				//ex) no�� ���� m = setNo()
				//	name : m = setName()
				//	password : m = setPassword()
				m = findSetter(dataType, paramName);
				if(m != null) {
					//dataObject��ü�� m�޼ҵ带 ȣ��
					//ù ��° �Ű������� Ŭ���̾�Ʈ�� �Ű����� ���� ����
					//���÷��� API invoke() : �ش� �޼ҵ� ����
					m.invoke(dataObject, 
							createValueObject(m.getParameterTypes()[0], //setter �޼ҵ��� �Ű����� Ÿ�� 
											 request.getParameter(paramName) /*��û �Ű����� ��*/ ));
				}
			}
			//�������� ���� �Ű����� ������ ��ü�� ������ �� ������ ��ü ����
			return dataObject;
		}
	}
	
	private static Method findSetter(Class<?> type, String name) {
		//�ش� Ŭ���� Ÿ���� ���� ��� �޼ҵ� ����
		//spms.vo.Member
		Method[] methods = type.getMethods();
		
		String propName = null;
		for(Method m : methods) {
			//�޼ҵ� �����̸��� set �ƴϸ� ���ư�
			if(!m.getName().startsWith("set")) {
				continue;
			}
			
			//set�� ������ �̸� ==> ������Ƽ
			//setName() -> propName = Name;
			propName = m.getName().substring(3);
			//propName = name();
			propName = propName.toLowerCase();
			//name���� �Ѱ��� �̸��� ������ �ش� ������Ʈ��  setter�� ã��
			if(propName.equals(name.toLowerCase())) {
				return m;
			}
		} 
		return null;
	}
	
	private static boolean isPrimitiveType(Class<?> type) {
		if(type.getName().equals("int") || type == Integer.class ||
		   type.getName().equals("long") || type == Long.class ||
		   type.getName().equals("float") || type == Float.class ||
		   type.getName().equals("double") || type == Double.class ||
		   type.getName().equals("boolean") || type == Boolean.class ||
		   type == Date.class || type == String.class) {
			return true;
		}
		return false;
	}
	
	private static Object createValueObject(Class<?> type, String value) {
		if(type.getName().equals("int") || type == Integer.class) {
			return new Integer(value);
		} else if(type.getName().equals("float") || type == Float.class) {
			return new Float(value);
		} else if(type.getName().equals("double") || type == Double.class) {
			return new Double(value);
		} else if(type.getName().equals("long") || type == Long.class) {
			return new Long(value);
		} else if(type.getName().equals("boolean") || type == Boolean.class) {
			return new Boolean(value);
		} else if(type == Date.class) {
			return java.sql.Date.valueOf(value);
		} else {
			return value;
		}
	}
}
