package spms.vo;

import java.util.Date;
/*
 * VO(Value Object) = DTO(Data Transfer Object)
 * ���� ��� �����ϴ� ��ü
 * 
 * ���̺��̳� ��� 1:1 ���� ����
 * or
 * �������� ������ ������� 1:1 ���� ����
 * 
 */

/*
 * 1. setter void ��
 * 		member.setNo(10);
 * 		member.setName("ȫ�浿");
 * 		member.setEmail("s1@test.com");
 * 
 * 2. setter ��ü ���� -> chain������ setter ȣ���� ��������
 * 		member.setNo(10)
 * 			  .setName("ȫ�浿");
 * 			  .setEmail("s1@test.com");
 * 		
 */

public class Member {
	protected int no;
	protected String name;
	protected String email;
	protected String password;
	protected Date CreateDate;
	protected Date ModifiedDate;
	public int getNo() {
		return no;
	}
	public Member setNo(int no) {
		this.no = no;
		return this;
	}
	public String getName() {
		return name;
	}
	public Member setName(String name) {
		this.name = name;
		return this;
	}
	public String getEmail() {
		return email;
	}
	public Member setEmail(String email) {
		this.email = email;
		return this;
	}
	public String getPassword() {
		return password;
	}
	public Member setPassword(String password) {
		this.password = password;
		return this;
	}
	public Date getCreateDate() {
		return CreateDate;
	}
	public Member setCreateDate(Date createDate) {
		CreateDate = createDate;
		return this;
	}
	public Date getModifiedDate() {
		return ModifiedDate;
	}
	public Member setModifiedDate(Date modifiedDate) {
		ModifiedDate = modifiedDate;
		return this;
	}
	
}
