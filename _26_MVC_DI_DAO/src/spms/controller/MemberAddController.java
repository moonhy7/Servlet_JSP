package spms.controller;

import java.util.Map;

import spms.dao.MemberDAO;
import spms.vo.Member;

//HttpServlet�� GenericServlet�� ��ӹ��� Ŭ����
//service() �޼ҵ带 ȣ���Ŀ� ���� doGet(), doPost(), doPut(), doDelete()�� �и��س���
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
