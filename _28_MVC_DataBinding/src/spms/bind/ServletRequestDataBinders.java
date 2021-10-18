package spms.bind;

import java.lang.reflect.Method;
import java.sql.Date;
import java.util.Set;

import javax.servlet.ServletRequest;

//클라이언트가 보낸 매개변수 값을 객체에 담아주는 클래스
public class ServletRequestDataBinders {
	public static Object bind(ServletRequest request, Class<?> dataType, String dataName) throws Exception {
		//생성해야 될 대상이 PrimitiveType일 경우
		if(isPrimitiveType(dataType)) {
			return createValueObject(dataType, request.getParameter(dataName));
		} //dataType이 일반 VO객체일 경우
		else {
			//브라우저가 보낸 매개변수들의 이름을 set에 담음
			//email, password, no, name....이 Set에 String타입으로 담기게 됨
			Set<String> paramNames = request.getParameterMap().keySet();
			//클래스 타입대로 객체 생성
			//리플렉션 API newInstance() : 해당 타입의 클래스 인스턴스 생성
			//dataType == spms.vo.Member => dataObject = spms.vo.Member;
			Object dataObject = dataType.newInstance();
			//브라우저가 보낸 매개변수를 객체의 필드를 찾아서 저장
			//setter를 찾아서 담아줄 Method 변수 
			Method m = null; //VO의 setter 담을 변수
			for(String paramName : paramNames) {
				//매개변수에 해당하는 setter 메소드 찾기
				//ex) no이 오면 m = setNo()
				//	name : m = setName()
				//	password : m = setPassword()
				m = findSetter(dataType, paramName);
				if(m != null) {
					//dataObject객체의 m메소드를 호출
					//첫 번째 매개변수에 클라이언트의 매개변수 값을 대입
					//리플렉션 API invoke() : 해당 메소드 실행
					m.invoke(dataObject, 
							createValueObject(m.getParameterTypes()[0], //setter 메소드의 매개변수 타입 
											 request.getParameter(paramName) /*요청 매개변수 값*/ ));
				}
			}
			//브라우저가 보낸 매개변수 값까지 객체에 저장한 후 생성된 객체 리턴
			return dataObject;
		}
	}
	
	private static Method findSetter(Class<?> type, String name) {
		//해당 클래스 타입이 가진 모든 메소드 추출
		//spms.vo.Member
		Method[] methods = type.getMethods();
		
		String propName = null;
		for(Method m : methods) {
			//메소드 시작이름이 set 아니면 돌아감
			if(!m.getName().startsWith("set")) {
				continue;
			}
			
			//set을 포함한 이름 ==> 프로퍼티
			//setName() -> propName = Name;
			propName = m.getName().substring(3);
			//propName = name();
			propName = propName.toLowerCase();
			//name으로 넘겨준 이름과 같으면 해당 프로퍼트의  setter를 찾음
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
