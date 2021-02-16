package member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import member.model.vo.Member;

import static common.JDBCTemplate.close;

public class MemberDao {
	  
	 // Dao
	 // 3. PreparedStatement 객체 생성 (미완성쿼리)
	 // 3.1 ? 값 대입
	 // 4. 실행 DML(excuteUpdate) -> int, DQL(excuteQuery) -> ResultSet
	 // 4.1 ResultSet -> Java 객체 옮겨담기
	 // 5. 자원반납 (생성역순 rset - pstmt) 
	 
	/*
	 * 1. 전체회원조회
	 */
	public List<Member> selectAll(Connection conn){
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		//가입일 역순으로 조회
		String sql ="select * from member order by enroll_date desc ";
		List<Member> list = null;
		
		try {
			// 3. PreparedStatement 객체 생성 (미완성쿼리)
			pstmt = conn.prepareStatement(sql);
			// 3.1 ? 값 대입 --?가 없음으로 실행할 필요가 없음
			
			// 4. 실행 DML(excuteUpdate) -> int, DQL(excuteQuery) -> ResultSet
			rset = pstmt.executeQuery();
			
			// 4.1 ResultSet -> Java 객체 옮겨담기
			list = new ArrayList<Member>();
			while(rset.next()) {
				//멤버객체를 만들어서 하나씩 담음 /한컬럼씩 가져와서 자바 변수에 담아둔다
				//한 행이 멤버객체 하나라는 것 이해하기.
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email= rset.getString("email");
				String phone= rset.getString("phone");
				String address= rset.getString("address");
				String hobby= rset.getString("hobby");
				Date enrollDate= rset.getDate("enroll_date");
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
				list.add(member);
				
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 5. 자원반납 (생성역순 rset - pstmt) 
			close(rset);
			close(pstmt);
			/*
			 * try { if(rset != null) rset.close(); } catch (SQLException e1) { // TODO
			 * Auto-generated catch block e1.printStackTrace(); } try { if(pstmt != null)
			 * pstmt.close(); } catch (SQLException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			
		}
		 return list;
	}
	
	/*
	 * 2.아이디로 조회
	 */
	public Member selectId(Connection conn,String memberId) {
		PreparedStatement pstmt = null;
		String sql = "select * from member where member_id = ?";
		ResultSet rset = null;
		Member member = null;
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
			
				member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, enrollDate);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return member;
	}
	
	/*
	 * 3.이름으로 조회
	 */
	public Member selectName(Connection conn, String memberName) {
		PreparedStatement pstmt = null;
		String sql = "select * from member where member_name like ?";
		ResultSet rset = null;
		Member member = null;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%"+memberName+"%");
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email = rset.getString("email");
				String phone = rset.getString("phone");
				String address = rset.getString("address");
				String hobby = rset.getString("hobby");
				Date enrollDate = rset.getDate("enroll_date");
				
				member = new Member(memberId, password, memberName, 
									  gender, age, email, phone, 
									  address, hobby, enrollDate);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return member;
	}

	/*
	 * 4. 회원가입
	 */
	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = "insert into member values(?,?,?,?,?,?,?,?,?,default)";
		int result = 0;
		
		try {
			//3.PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);

			//3.1 값대입
			pstmt.setString(1, member.getMemberId()); //첫번째 ?는 id이다
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getEmail());
			pstmt.setString(7, member.getPhone());
			pstmt.setString(8, member.getAddress());
			pstmt.setString(9, member.getHobby());
			
			//실행 DML executeUpdate
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/*
	 * 5.회원 정보 변경
	 */
	public int updateMemberPw(Connection conn, Member m, String memberId, String memberPw) {
		PreparedStatement pstmt = null;
		String sql = "update member set"
				+ " password=?"//띄어쓰기 주의
				+ " where member_id=? and password=?";
		Member member = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getPassword());
			pstmt.setString(2, memberId);
			pstmt.setString(3, memberPw);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public int updateMemberEmail(Connection conn, Member m, String memberId, String memberPw) {
		PreparedStatement pstmt = null;
		String sql = "update member set"
				+ " email=?"
				+ " where member_id=? and password=?";
		Member member = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getEmail());
			pstmt.setString(2, memberId);
			pstmt.setString(3, memberPw);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return result;
	}
	
	
	public int updateMemberPhone(Connection conn, Member m, String memberId, String memberPw) {
		PreparedStatement pstmt = null;
		String sql = "update member set"
				+ " phone=?"
				+ " where member_id=? and password=?";
		Member member = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getPhone());
			pstmt.setString(2, memberId);
			pstmt.setString(3, memberPw);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return result;
	}
	
	public int updateMemberAdd(Connection conn, Member m, String memberId, String memberPw) {
		PreparedStatement pstmt = null;
		String sql = "update member set"
				+ " address=?"
				+ " where member_id=? and password=?";
		Member member = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getAddress());
			pstmt.setString(2, memberId);
			pstmt.setString(3, memberPw);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
		return result;
	}
	
	/*
	 * 회원 삭제
	 */
	public int deleteMember(Connection conn, String memberId, String memberPw) {
		PreparedStatement pstmt = null;
		String sql = "delete from member where member_id=? and password=?";
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}





	
	
	
}
