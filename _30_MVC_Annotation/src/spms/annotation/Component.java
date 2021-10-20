package spms.annotation;

import java.lang.annotation.*;

//Retention : 어노테이션의 유지 정책을 설정
//RetentionPolicy.RUNTIME : 웹 어플리케이션이 실행중이면 언제든지 어노테이션을 참조할 수 있도록 설정
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
	//객체 이름을 저장하는 용도로 사용
	//@Component("memberDAO")
	//memberDAO -> value값으로 담김
	String value() default "";
}
