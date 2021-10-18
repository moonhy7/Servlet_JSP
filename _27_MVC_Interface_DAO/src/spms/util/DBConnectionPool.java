package spms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	//미리 커넥션을 만들어 놓을 개수
	final int PRE_POOL_SIZE = 10;
	String url;
	String username;
	String password;
	//커넥션을 담을 리스트 
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public DBConnectionPool(String driver, String url, String username, String password) throws Exception {
		this.url = url;
		this.username = username;
		this.password = password;
		
		Class.forName(driver);
		
		//미리 PRE_POOL_SIZE만큼 커넥션 생성
		for(int i = 0; i < PRE_POOL_SIZE; i++) {
			connList.add(DriverManager.getConnection(url, username, password));
		}
	}
	
	//Connection 객체 요청 시 Connection 대여
	public Connection getConnection() throws Exception {
		//현재 만들어진 커넥션 풀에 여유분이 존재하면
		if(connList.size() > 0) {
			Connection conn = connList.remove(0);
			//DB 커넥션이 유효하면 꺼낸 커넥션 리턴
			if(conn.isValid(10)) {
				return conn;
			}
		}
		
		//커넥션 풀에 여유분이 없을 경우 새로 커넥션을 만들어서 리턴
		return DriverManager.getConnection(url, username, password);
	}
	
	//빌려준 커넥션을 반환
	public void returnConnection(Connection conn) throws Exception {
		if(conn != null && conn.isClosed() == false) {
			connList.add(conn);
		}
	}
	
	//어플리케이션 종료 시 모든 Connection 종료
	public void closeAll() {
		System.out.println("connList.size()============" + connList.size());
		for(Connection conn : connList) {
			try {
				conn.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
}
