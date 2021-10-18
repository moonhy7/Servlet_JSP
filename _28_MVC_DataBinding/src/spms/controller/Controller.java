package spms.controller;

import java.util.Map;

/*
 * 1) FrontController에서 PageController 호출 방식 동일화
 * 2) 모든 PageController에서 Servlet을 제거하고 Controller 인터페이스를 상속받음
 * */
public interface Controller {
	public String execute(Map<String, Object> model) throws Exception;
}
