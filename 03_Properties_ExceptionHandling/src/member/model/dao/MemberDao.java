package member.model.dao;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import member.model.exception.MemberDeleteException;
import member.model.exception.MemberException;
import member.model.exception.MemberIdException;
import member.model.exception.MemberInsertException;
import member.model.exception.MemberNameException;
import member.model.exception.MemberPwException;
import member.model.exception.MemberUpdateException;
import member.model.vo.Member;

import static common.JDBCTemplate.close;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	
	/**
	 * 1. MemberDao객체 생성시(최초1회) ->//기본생성자
	 * member-query.properties의 내용을 읽어다 prop에 저장한다.
	 * 
	 * 2. dao메소드 호출시마다 prop으로부터 query를 가져다 사용한다.
	 * 
	 */
	
	//기본생성자
	public MemberDao() {
		//member-query.properties -> prop
		String fileName = "resources/member-query.properties";
		try {
			prop.load(new FileReader(fileName));
//			System.out.println(prop);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	  
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
		String sql =prop.getProperty("selectAll");
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
		} catch (SQLException e) { //SQLException e checked exception=>예외처리 강제화
//			e.printStackTrace();
			//예외를 전환 : RuntimeException, 의미분명한 커스텀 예외객체로 전환 => 예외처리를 강제화하지 않음
			throw new MemberException("회원 전체 조회", e); //최초 발생한 예외(e)도 같이 던진다.
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
		String sql = prop.getProperty("selectId");
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
//			e.printStackTrace();
			throw new MemberIdException("회원 아이디 조회",e);
		}finally {
			close(rset);
			close(pstmt);
		}
		return member;
	}
	/*
	 * 2.1 비밀번호로 조회
	 */
	public Member selectPw(Connection conn, String password) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectPw");
		ResultSet rset = null;
		Member member = null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.setString(1, password);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				password = rset.getString("password");
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
//			e.printStackTrace();
			throw new MemberPwException("회원 비밀번호 조회",e);
		}finally {
			close(rset);
			close(pstmt);
		}
		
		
		return member;
	}
	/*
	 * 3.이름으로 조회
	 */
	public Member selectName(Connection conn, String memberName) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("selectName");
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
//			e.printStackTrace();
			throw new MemberNameException("회원 이름 조회",e);
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return member;
	}

	/*
	 * 4. 회원가입
	 */
	public int insertMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("insertMember");
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
//			e.printStackTrace();
			throw new MemberInsertException("회원가입",e);
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	/*
	 * 5.회원 정보 변경
	 */
	public int updateMemberPw(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMemberPw");
		Member member = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getPassword());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new MemberUpdateException("회원정보수정",e);
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	public int updateMemberEmail(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("updateMemberEmail");
		Member member = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getEmail());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new MemberUpdateException("회원정보수정",e);
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	
	public int updateMemberPhone(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		String sql =prop.getProperty("updateMemberPhone");
		Member member = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getPhone());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new MemberUpdateException("회원정보수정",e);
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	public int updateMemberAdd(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		String sql =prop.getProperty("updateMemberAdd");
		Member member = null;
		int result = 0;
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getAddress());
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new MemberUpdateException("회원정보수정",e);
		}finally {
			close(pstmt);
		}
		
		return result;
	}
	
	/*
	 * 회원 삭제
	 */
	public int deleteMember(Connection conn, Member member) {
		PreparedStatement pstmt = null;
		String sql = prop.getProperty("deleteMember");
		int result = 0;
		
		
		try {
			pstmt = conn.prepareStatement(sql);	
			pstmt.setString(1, member.getMemberId());
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new MemberDeleteException("회원정보삭제",e);
		}finally {
			close(pstmt);
			
		}
		return result;
	}
//	/*
//	 * 삭제한 회원 삭제테이블에 추가
//	 */
//
//	public int deleteMemberInsert(Connection conn, Member member){
//		PreparedStatement pstmt = null;
//		ResultSet rset = null;
//		String sql = prop.getProperty("deleteMemberInsert");
//		int result = 0;
//		
//		try {
//			pstmt = conn.prepareStatement(sql);
//			
//			pstmt.setString(1, member.getMemberId()); 
//			pstmt.setString(2, member.getPassword());
//			pstmt.setString(3, member.getMemberName());
//			pstmt.setString(4, member.getGender());
//			pstmt.setInt(5, member.getAge());
//			pstmt.setString(6, member.getEmail());
//			pstmt.setString(7, member.getPhone());
//			pstmt.setString(8, member.getAddress());
//			pstmt.setString(9, member.getHobby());
//			pstmt.setDate(10, member.getEnrollDate());
//			
//			result = pstmt.executeUpdate();
//			
//		} catch (SQLException e) {
////			e.printStackTrace();
//			throw new MemberDeleteException("회원정보삭제 테이블 추가 오류",e);
//		}finally {
//			close(pstmt);
//			
//		}
//		return result;
//	}


	/*
	 * 탈퇴한 회원 조회
	 */
	public List<Member> deleteMemberAll(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String sql = prop.getProperty("deleteMemberAll");
		List<Member> list = null;
		
		try {
			pstmt=conn.prepareStatement(sql);
			rset=pstmt.executeQuery();
			list = new ArrayList<Member>();
			
			while(rset.next()) {
				String memberId = rset.getString("member_id");
				String password = rset.getString("password");
				String memberName = rset.getString("member_name");
				String gender = rset.getString("gender");
				int age = rset.getInt("age");
				String email= rset.getString("email");
				String phone= rset.getString("phone");
				String address= rset.getString("address");
				String hobby= rset.getString("hobby");
				Date del_date= rset.getDate("del_date");
				Member member = new Member(memberId, password, memberName, gender, age, email, phone, address, hobby, del_date);
				list.add(member);
				
			}
			
		} catch (SQLException e) {
//			e.printStackTrace();
			throw new MemberDeleteException("회원정보삭제테이블 조회 오류",e);
		}finally{
			close(rset);
			close(pstmt);
		}
		return list;
		
	}


	
	
	
}
