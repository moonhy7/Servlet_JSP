package spms.controller;

import java.util.Map;

import spms.annotation.Component;
import spms.bind.DataBinding;
import spms.dao.MySqlMemberDAO;

@Component("/member/delete.do")
public class MemberDeleteController implements Controller, DataBinding {
	MySqlMemberDAO memberDAO = null;
	
	public MemberDeleteController setMemberDAO(MySqlMemberDAO memberDAO) {
		this.memberDAO = memberDAO;
		return this;
	}
	
	@Override
	public Object[] getDataBinders() {
		//key ���� �̸����� �����Ͽ� �ڵ����� �����ؾ� �Ǵ� Ŭ���� Ÿ�� ����
		return new Object[] {
				"no", Integer.class
		};
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {		
		Integer no = (Integer)model.get("no");
		memberDAO.delete(no);
		
		return "redirect:list.do";
	}
}
