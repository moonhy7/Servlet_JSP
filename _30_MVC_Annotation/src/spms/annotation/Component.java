package spms.annotation;

import java.lang.annotation.*;

//Retention : ������̼��� ���� ��å�� ����
//RetentionPolicy.RUNTIME : �� ���ø����̼��� �������̸� �������� ������̼��� ������ �� �ֵ��� ����
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {
	//��ü �̸��� �����ϴ� �뵵�� ���
	//@Component("memberDAO")
	//memberDAO -> value������ ���
	String value() default "";
}
