package spms.controller;

import java.util.Map;

import spms.dao.MemberDAO;

public class MemberListController implements Controller {
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		MemberDAO memberDAO = (MemberDAO)model.get("memberDAO");
		//���ϵ� ��ȸ ��� �ʿ� ����
		model.put("memberList", memberDAO.selectlist());
		
		return "/member/MemberList.jsp";
	}
}