package spms.controller;

import java.util.Map;

import spms.dao.MemberDAO;

public class MemberDeleteController implements Controller {
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDAO memberDAO = (MemberDAO)model.get("memberDAO");
		
		Integer no = (Integer)model.get("no");
		memberDAO.delete(no);
		
		return "redirect:list.do";
	}
}
