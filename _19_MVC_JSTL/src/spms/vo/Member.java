package spms.vo;

import java.util.Date;
/*
 * VO(Value Object) = DTO(Data Transfer Object)
 * 값을 묶어서 저장하는 객체
 * 
 * 테이블이나 뷰랑 1:1 매핑 관계
 * or
 * 페이지에 보여줄 정보들과 1:1 매핑 관계
 * 
 */

/*
 * 1. setter void 형
 * 		member.setNo(10);
 * 		member.setName("홍길동");
 * 		member.setEmail("s1@test.com");
 * 
 * 2. setter 객체 리턴 -> chain식으로 setter 호출이 가능해짐
 * 		member.setNo(10)
 * 			  .setName("홍길동");
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
