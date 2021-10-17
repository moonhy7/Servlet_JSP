package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import spms.vo.Member;

public class MemberDAO {
	Connection connection;
	
	//DAO��ü�� servlet�� �ƴϱ� ������ servletcontext�� �ִ� Ŀ�ؼ� ���� ���� �Ұ���
	//memberlistServlet���� Ŀ�ؼ��� ��ü�� DAO�� �������� ��
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	public List<Member> selectlist() throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlSelect = "SELECT * FROM MEMBERS ORDER BY MNO ASC";
		
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			
			ArrayList<Member> members = new ArrayList<Member>();
			
			while(rs.next()) {
				members.add(new Member()
										.setNo(rs.getInt("MNO"))
										.setName(rs.getString("MNAME"))
										.setEmail(rs.getString("EMAIL"))
										.setCreateDate(rs.getDate("CRE_DATE")));	
			}
			return members;
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	// MemberAddServlet���� �Է� ������ �Է¹��� �����͸� member��ü�� ��Ƽ�
	//DAO�� ������ ����
	public int insert(Member member) throws Exception {
		int result = 0;
		PreparedStatement stmt = null;
		final String sqlInsert = "INSERT INTO MEMBERS(EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE)" + 
									"VALUES(?, ?, ?, NOW(), NOW())";
		
		try {
			stmt = connection.prepareStatement(sqlInsert);
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			//insert �����ϸ� 1 int �� ����
			//���������� �������� int ���� ���� ������ �������� ó��
			stmt.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
}
