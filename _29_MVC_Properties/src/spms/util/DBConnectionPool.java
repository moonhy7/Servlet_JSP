package spms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnectionPool {
	//�̸� Ŀ�ؼ��� ����� ���� ����
	final int PRE_POOL_SIZE = 10;
	String url;
	String username;
	String password;
	//Ŀ�ؼ��� ���� ����Ʈ 
	ArrayList<Connection> connList = new ArrayList<Connection>();
	
	public DBConnectionPool(String driver, String url, String username, String password) throws Exception {
		this.url = url;
		this.username = username;
		this.password = password;
		
		Class.forName(driver);
		
		//�̸� PRE_POOL_SIZE��ŭ Ŀ�ؼ� ����
		for(int i = 0; i < PRE_POOL_SIZE; i++) {
			connList.add(DriverManager.getConnection(url, username, password));
		}
	}
	
	//Connection ��ü ��û �� Connection �뿩
	public Connection getConnection() throws Exception {
		//���� ������� Ŀ�ؼ� Ǯ�� �������� �����ϸ�
		if(connList.size() > 0) {
			Connection conn = connList.remove(0);
			//DB Ŀ�ؼ��� ��ȿ�ϸ� ���� Ŀ�ؼ� ����
			if(conn.isValid(10)) {
				return conn;
			}
		}
		
		//Ŀ�ؼ� Ǯ�� �������� ���� ��� ���� Ŀ�ؼ��� ���� ����
		return DriverManager.getConnection(url, username, password);
	}
	
	//������ Ŀ�ؼ��� ��ȯ
	public void returnConnection(Connection conn) throws Exception {
		if(conn != null && conn.isClosed() == false) {
			connList.add(conn);
		}
	}
	
	//���ø����̼� ���� �� ��� Connection ����
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
