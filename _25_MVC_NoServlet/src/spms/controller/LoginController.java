package spms.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MemberDAO;
import spms.vo.Member;

/*
 * 페이지 컨트롤러들이 Servlet이 제거되고 일반 클래스로 변하기 때문에
 * HttpServletRequest, HttpServletResponse 및 ServletContext를 사용할 수 없다
 * execute 메소드의 매개변수로 String Key값을 가지는 객체들을 맵에 담아서 전달
 */
public class LoginController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("loginInfo") == null) { //model 매개변수에 담아줌
			return "/auth/LoginForm.jsp";
		} else {
			MemberDAO memberDAO = (MemberDAO) model.get("memberDAO");
			Member loginInfo = (Member)model.get("loginInfo");
			
			Member member = memberDAO.exist(loginInfo.getEmail(), loginInfo.getPassword());
		
			if(member != null) {
				//세션이라는 키값을 가지는 HttpSession 모델 객체를 맵에 담아서 전달해줌
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("Member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LoginFail.jsp";
			}
		}
	}
}
