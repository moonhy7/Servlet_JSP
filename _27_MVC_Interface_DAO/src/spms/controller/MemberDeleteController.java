package spms.controller;

import java.util.Map;

import spms.dao.MySqlMemberDAO;

public class MemberDeleteController implements Controller {
	MySqlMemberDAO memberDAO = null;
	
	public MemberDeleteController setMemberDAO(MySqlMemberDAO memberDAO) {
		this.memberDAO = memberDAO;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {		
		Integer no = (Integer)model.get("no");
		memberDAO.delete(no);
		
		return "redirect:list.do";
	}
}
