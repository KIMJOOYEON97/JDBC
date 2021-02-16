package common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import member.model.vo.Member;

/**
 * Service, Dap 클래스의 공통부분을 static메소드 제공
 * 예외처리를 공통부분에서 작성하므로, 사용(호출)하는 쪽의 코드를 간결히 할 수 있다.
 *
 */
public class JDBCTemplate {
	
	//static(시작하자마자 생성됌) 안에서는 인스턴트(객체생성되야 생성됌)를 참조 못함(왜냐하면  생성시기가 다르기때문)
	static String driverClass = "oracle.jdbc.OracleDriver";
	static String url = "jdbc:oracle:thin:@localhost:1521:xe";
//	static String url = "jdbc:oracle:thin:@khmclass.iptime.org:1521:xe";
	static String user = "student";
	static String password = "student";
	
	//static 초기화 블럭
	 static { //프로그램 스타트할 때 한번 실행됌
		 
		 // 1. DriverClass등록(최초1회)
		 try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	 }
	
	 /**
	  * MemberService에서 
	  * @return
	  */
	public static Connection getConnection(){
		Connection conn = null;
		
		try {
			// 2. Connection객체 생성 url, user, password
			conn = DriverManager.getConnection(url,user,password);
			// 2.1 자동커밋 false설정
			conn.setAutoCommit(false); //DQL이기때문에 안해도 상관없음
			//AutoCommit을 하면 실행하는 족족 commit을 해버림. false를 하면 닫을때만???commit된다. 2번째 시간 다시 체크		
		} catch ( SQLException e) {
			e.printStackTrace();
		} 
		return conn;
	}
	/**
	  * MemberService에서 
	  * 
	  */
	public static void close(Connection conn) {
		// 7. 자원 반납(conn) 
		try {
			if(conn != null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * MemberDao에서 5. 자원반납 (생성역순 rset - pstmt) 
	 * 
	 */
	public static void close(ResultSet rset) {
		try {
			if(rset != null)
				rset.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement pstmt) {
		try {
			if(pstmt != null)
				pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void commit(Connection conn) {
		try {
			if(conn != null)
				conn.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback(Connection conn) {
		try {
			 if(conn != null)
				conn.rollback();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
