package spms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import spms.annotation.Component;
import spms.vo.Project;

@Component("projectDAO")
public class MySqlProjectDAO implements ProjectDAO{
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
	
	@Override
	public List<Project> selectlist() throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sqlSelect = "SELECT * FROM PROJECTS ORDER BY PNO DESC";
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelect);
			
			ArrayList<Project> projects = new ArrayList<Project>();
			while(rs.next()) {
				projects.add(new Project()
										  .setNo(rs.getInt("PNO"))
										  .setTitle(rs.getString("PNAME"))
										  .setStartDate(rs.getDate("STA_DATE"))
										  .setEndDate(rs.getDate("END_DATE"))
										  .setState(rs.getInt("STATE")));	
			}
			
			return projects;
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch(Exception e) {
				throw e;
			}
			
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				throw e;
			}
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				throw e;
			}
		}
	}
	
	@Override
	public int insert(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		String sqlInsert = "INSERT INTO PROJECTS(PNAME, CONTENT, STA_DATE, END_DATE, STATE, CRE_DATE ,TAGS)" +
									   "VALUES(?, ?, ?, ?, 0, NOW(), ?)";
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(sqlInsert);
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setString(5, project.getTags());
			
			return stmt.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				throw e;
			}
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				throw e;
			}
		}
	}
	
	@Override
	public Project selectOne(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		String sqlSelectOne = "SELECT * FROM PROJECTS WHERE PNO=" + no;
		
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sqlSelectOne);
			if(rs.next()) {
				return new Project()
									.setNo(rs.getInt("PNO"))
									.setTitle(rs.getString("PNAME"))
									.setContent(rs.getString("CONTENT"))
									.setStartDate(rs.getDate("STA_DATE"))
									.setEndDate(rs.getDate("END_DATE"))
									.setState(rs.getInt("STATE"))
									.setCreatedDate(rs.getDate("CRE_DATE"))
									.setTags(rs.getString("TAGS"));
			} else {
				throw new Exception("해당 번호의 프로젝트를 찾을 수 없습니다.");
			}
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(rs != null) {
					rs.close();
				}
			} catch(Exception e) {
				throw e;
			}
			
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				throw e;
			}
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				throw e;
			}
		}
	}
	
	public int update(Project project) throws Exception {
		Connection connection = null;
		PreparedStatement stmt = null;
		
		String sqlUpdate = "UPDATE PROJECTS SET PNAME=?, CONTENT=?, STA_DATE=?, END_DATE=?, STATE=?, TAGS=? WHERE PNO=?";
		
		try {
			connection = ds.getConnection();
			stmt = connection.prepareStatement(sqlUpdate);
			stmt.setString(1, project.getTitle());
			stmt.setString(2, project.getContent());
			stmt.setDate(3, new java.sql.Date(project.getStartDate().getTime()));
			stmt.setDate(4, new java.sql.Date(project.getEndDate().getTime()));
			stmt.setInt(5, project.getState());
			stmt.setString(6, project.getTags());
			stmt.setInt(7, project.getNo());
			
			return stmt.executeUpdate();
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				throw e;
			}
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				throw e;
			}
		}
	}
	
	@Override
	public int delete(int no) throws Exception {
		Connection connection = null;
		Statement stmt = null;
		
		String sqlDelete = "DELETE FROM PROJECTS WHERE PNO=" + no;
	
		try {
			connection = ds.getConnection();
			stmt = connection.createStatement();
			return stmt.executeUpdate(sqlDelete);
		} catch(Exception e) {
			throw e;
		} finally {
			try {
				if(stmt != null) {
					stmt.close();
				}
			} catch(Exception e) {
				throw e;
			}
			
			try {
				if(connection != null) {
					connection.close();
				}
			} catch(Exception e) {
				throw e;
			}
		}
	}
}
