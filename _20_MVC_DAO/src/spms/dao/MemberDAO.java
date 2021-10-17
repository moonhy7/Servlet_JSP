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
	
	//DAO객체는 servlet이 아니기 때문에 servletcontext에 있는 커넥션 직접 접근 불가능
	//memberlistServlet에서 커넥션을 객체를 DAO에 주입해줄 것
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
	
	// MemberAddServlet에서 입력 폼으로 입력받은 데이터를 member객체로 담아서
	//DAO로 전달할 예정
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
			//insert 성공하면 1 int 값 리턴
			//정상적으로 들어갔는지를 int 리턴 값을 가지고 서블릿에서 처리
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
