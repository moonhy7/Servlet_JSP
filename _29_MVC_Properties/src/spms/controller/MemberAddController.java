package spms.controller;

import java.util.Map;

import spms.bind.DataBinding;
import spms.dao.MySqlMemberDAO;
import spms.vo.Member;

//HttpServlet은 GenericServlet을 상속받은 클래스
//service() 메소드를 호출방식에 따라 doGet(), doPost(), doPut(), doDelete()로 분리해놓음
public class MemberAddController implements Controller, DataBinding {
	MySqlMemberDAO memberDAO = null;
	
	public MemberAddController setMemberDAO(MySqlMemberDAO memberDAO) {
		this.memberDAO = memberDAO;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		//key 값과 이름으로 매핑하여 자동으로 생성해야 되는 클래스 타입 지정
		return new Object[] {
				"member", spms.vo.Member.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		Member member = (Member)model.get("member");
		
		if(member.getEmail() == null) {
			return "/member/MemberForm.jsp";
		} else {
			memberDAO.insert(member);
			
			return "redirect:list.do";
		}
	}
}
