package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.vo.Member;

public class MySqlMemberDAO implements MemberDAO {
	/*
	 * Connection connection;
	 * 
	 * //DAO객체는 servlet이 아니기 때문에 servletcontext에 있는 커넥션 직접 접근 불가능
	 * //memberlistServlet에서 커넥션을 객체를 DAO에 주입해줄 것 public void
	 * setConnection(Connection connection) { this.connection = connection; }
	 */
	
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public List<Member> selectlist() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlSelect = "SELECT * FROM MEMBERS ORDER BY MNO ASC";
		
		try {
			connection = ds.getConnection();
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
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	//MemberAddServlet에서 입력 폼으로 입력받은 데이터를 member객체로 담아서 
	//DAO로 전달할 예정
	@Override
	public int insert(Member member) throws Exception {
		int result = 0;
		Connection connection = null;
		PreparedStatement stmt = null;
		final String sqlInsert = "INSERT INTO MEMBERS(EMAIL, PWD, MNAME, CRE_DATE, MOD_DATE)" +
												"VALUES(?, ?, ?, NOW(), NOW())";
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(sqlInsert);
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getPassword());
			stmt.setString(3, member.getName());
			//insert 성공하면 1 int 값 리턴
			result = stmt.executeUpdate();
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
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	@Override
	public int delete(int no) throws Exception {
		int result = 0;
		Connection connection = null;
		Statement stmt = null;
		final String sqlDelete = "DELETE FROM MEMBERS WHERE MNO=" + no;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			result = stmt.executeUpdate(sqlDelete);
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
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	//해당 멤버 데이터 조회
	@Override
	public Member selectOne(int no) throws Exception {
		Member member = null;
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		final String sqlSelectOne = "SELECT * FROM MEMBERS WHERE MNO=" + no;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelectOne);
			if(rs.next()) {
				member = new Member()
									 .setNo(rs.getInt("MNO"))
									 .setEmail(rs.getString("EMAIL"))
									 .setName(rs.getString("MNAME"))
									 .setCreateDate(rs.getDate("CRE_DATE"));
			} else {
				throw new Exception("해당 번호의 회원을 찾을 수 없습니다.");
			}
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
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return member;
	}
	
	//해당 멤버 데이터 수정
	@Override
	public int update(Member member) throws Exception {
		int result = 0;
		Connection connection = null;
		PreparedStatement stmt = null;
		
		final String sqlUpdate = "UPDATE MEMBERS SET EMAIL=?, MNAME=?, MOD_DATE=NOW() WHERE MNO=?";
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(sqlUpdate);
			stmt.setString(1, member.getEmail());
			stmt.setString(2, member.getName());
			stmt.setInt(3, member.getNo());
			result = stmt.executeUpdate();
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
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	@Override
	public Member exist(String email, String password) throws Exception {
		System.out.println(email);
		System.out.println(password);
		Member member = null;
		Connection connection = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		final String sqlExist = "SELECT * FROM MEMBERS WHERE EMAIL=? AND PWD=?";
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(sqlExist);
			stmt.setString(1, email);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if(rs.next()) {
				member = new Member()
									 .setName(rs.getString("MNAME"))
									 .setEmail(rs.getString("EMAIL"));
			} else {
				return null;
			}
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
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		return member;
	}
}
