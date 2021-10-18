package spms.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import spms.dao.MemberDAO;
import spms.vo.Member;

/*
 * ������ ��Ʈ�ѷ����� Servlet�� ���ŵǰ� �Ϲ� Ŭ������ ���ϱ� ������
 * HttpServletRequest, HttpServletResponse �� ServletContext�� ����� �� ����
 * execute �޼ҵ��� �Ű������� String Key���� ������ ��ü���� �ʿ� ��Ƽ� ����
 */
public class LoginController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("loginInfo") == null) { //model �Ű������� �����
			return "/auth/LoginForm.jsp";
		} else {
			MemberDAO memberDAO = (MemberDAO) model.get("memberDAO");
			Member loginInfo = (Member)model.get("loginInfo");
			
			Member member = memberDAO.exist(loginInfo.getEmail(), loginInfo.getPassword());
		
			if(member != null) {
				//�����̶�� Ű���� ������ HttpSession �� ��ü�� �ʿ� ��Ƽ� ��������
				HttpSession session = (HttpSession)model.get("session");
				session.setAttribute("Member", member);
				return "redirect:../member/list.do";
			} else {
				return "/auth/LoginFail.jsp";
			}
		}
	}
}
