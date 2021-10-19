package spms.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

public class LogoutController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session = (HttpSession)model.get("session");
		//���� ��ȿȭ ó��
		session.invalidate();
		
		return "redirect:login.do";
	}

}
