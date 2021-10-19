package spms.dao;

import java.util.List;

import spms.vo.Member;

/*
 * �������̽� ���� ����
 * 
 * Oracle, MySql, MSSql �� DBMS ����� 
 * �� �������̽��� ��ӹ޴� DAO Ŭ������ �����ϵ�
 * �� DBMS Ư���� �°� �����Ͽ� DBMS ��ü�� ���� ��
 * �޼����� ���Ұ� ���� ���� �����ϰ� ó��
 * 
 * */
public interface MemberDAO {
	public List<Member> selectlist() throws Exception;
	public int insert(Member member) throws Exception;
	public int delete(int no) throws Exception;
	public Member selectOne(int no) throws Exception;
	public int update(Member member) throws Exception;
	public Member exist(String email, String password) throws Exception;
}
