package spms.controller;

import java.util.Map;

/*
 * 1) FrontController���� PageController ȣ�� ��� ����ȭ
 * 2) ��� PageController���� Servlet�� �����ϰ� Controller �������̽��� ��ӹ���
 * */
public interface Controller {
	public String execute(Map<String, Object> model) throws Exception;
}
