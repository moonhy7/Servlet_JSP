package spms.controller;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDAO;
import spms.vo.Member;

//HttpServlet�� GenericServlet�� ��ӹ��� Ŭ����
//service() �޼ҵ带 ȣ���Ŀ� ���� doGet(), doPost(), doPut(), doDelete()�� �и��س���
@Component("/member/add.do")
public class MemberAddController implements Controller, DataBinding {
	MySqlMemberDAO memberDAO = null;
	
	public MemberAddController setMemberDAO(MySqlMemberDAO memberDAO) {
		this.memberDAO = memberDAO;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		//key ���� �̸����� �����Ͽ� �ڵ����� �����ؾ� �Ǵ� Ŭ���� Ÿ�� ����
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
