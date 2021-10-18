package spms.controller;

import java.util.Map;

import spms.dao.MemberDAO;
import spms.vo.Member;

//HttpServlet은 GenericServlet을 상속받은 클래스
//service() 메소드를 호출방식에 따라 doGet(), doPost(), doPut(), doDelete()로 분리해놓음
public class MemberAddController implements Controller {
	MemberDAO memberDAO = null;
	
	public MemberAddController setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("member") == null) {
			return "/member/MemberForm.jsp";
		} else {
			Member member = (Member)model.get("member");
			memberDAO.insert(member);
			
			return "redirect:list.do";
		}
	}
}
