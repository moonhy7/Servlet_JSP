package spms.controller;

import java.util.Map;

import spms.dao.MySqlMemberDAO;

public class MemberListController implements Controller {
	/*
	 * DI(Dependency Injection)
	 * 1) 클래스간 결합도 낮춤
	 * 2) MemberDAO 인터페이스를 선언하고 상속구현하므로써 다른 DBMS로의 전환 용이
	 * 3) 나중에 변경할 자동화 작업에 사용
	 * */
	
	MySqlMemberDAO memberDAO = null;
	
	public MemberListController setMemberDAO(MySqlMemberDAO memberDAO) {
		this.memberDAO = memberDAO;
		return this;
	}
	
	@Override
	public String execute(Map<String, Object> model) throws Exception {
		//리턴될 조회 결과 맵에 담음
		model.put("memberList", memberDAO.selectlist());
		
		return "/member/MemberList.jsp";
	}
}