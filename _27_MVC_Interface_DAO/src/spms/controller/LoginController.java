package spms.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MySqlMemberDAO;
import spms.vo.Member;

/*
 * 페이지 컨트롤러들에서 Servlet이 제거되고 일반 클래스로 변하기 때문에
 * HttpServletRequest, HttpServletResponse 및 ServletContext를 사용할 수 없다
 * execute 메소드의 매개변수로 String Key값을 가지는 객체들을 맵에 담아서 전달
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
