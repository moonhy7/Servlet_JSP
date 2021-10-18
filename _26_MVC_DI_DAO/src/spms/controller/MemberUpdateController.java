package spms.controller;

import java.util.Map;

import spms.dao.MemberDAO;
import spms.vo.Member;

public class MemberUpdateController implements Controller {
	MemberDAO memberDAO = null;
	
	public MemberUpdateController setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		if(model.get("member") == null) {
			Integer no = (Integer)model.get("no");
			Member member = memberDAO.selectOne(no);
			model.put("member", member);
			return "/member/MemberUpdateForm.jsp";
		} else {
			Member member = (Member)model.get("member");
			memberDAO.update(member);
			return "redirect:list.do";
		}
	}

}
