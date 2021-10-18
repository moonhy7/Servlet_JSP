package spms.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MySqlMemberDAO;
import spms.vo.Member;

/*
 * ������ ��Ʈ�ѷ��鿡�� Servlet�� ���ŵǰ� �Ϲ� Ŭ������ ���ϱ� ������
 * HttpServletRequest, HttpServletResponse �� ServletContext�� ����� �� ����
 * execute �޼ҵ��� �Ű������� String Key���� ������ ��ü���� �ʿ� ��Ƽ� ����
 * */
public class LoginController implements Controller {
	 MySqlMemberDAO memberDAO = null;
	 
	 public LoginController setMemberDAO(MySqlMemberDAO memberDAO) {
		 this.memberDAO = memberDAO;
		 return this;
	 }
	 
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("loginInfo") == null) {
			return "/auth/LoginForm.jsp";
		} else {
			Member loginInfo = (Member)model.get("loginInfo");
			Member member = memberDAO.exist(loginInfo.getEmail(), loginInfo.getPassword());
			
			if(member != null) {
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("Member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LoginFail.jsp";
			}
		}
	}
}
