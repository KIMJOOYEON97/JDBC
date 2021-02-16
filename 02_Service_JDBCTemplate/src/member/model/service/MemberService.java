package member.model.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

import common.JDBCTemplate;
import member.model.dao.MemberDao;
import member.model.vo.Member;
import static common.JDBCTemplate.*; //static 임포트를 하게되면 클래스 명 없이 호출가능. 임포트할 때 메소드까지 해줄것

public class MemberService {
	
	private MemberDao memberDao = new MemberDao();
	
	/**
	 * Service
	 * 1. DriverClass등록(최초1회)
	 * 2. Connection객체 생성 url, user, password
	 * 2.1 자동커밋 false설정
	 * ------ Dao 요청 -----------
	 * 6. 트랜잭션처리(DML) commit/rollback
	 * 7. 자원 반납(conn) 
	 * *생성역순 ( DML : pstmt - conn) (DQL : rest - pstmt - conn)
	 * 
	 * 
	 * Dao
	 * 3. PreparedStatement 객체 생성 (미완성쿼리)
	 * 3.1 ? 값 대입
	 * 4. 실행 DML(excuteUpdate) -> int, DQL(excuteQuery) -> ResultSet
	 * 4.1 ResultSet -> Java 객체 옮겨담기
	 *
	 * 5. 자원반납 (생성역순 rset - pstmt) 
	 */
	
	/*
	 * 1. 전체회원조회
	 */
	public List<Member> selectAll_() {
		Connection conn = getConnection();
		List<Member>list =memberDao.selectAll(conn);
		close(conn);
		return list;
	}

	/*
	 * 2. 아이디로 조회
	 */
	public Member selectId(String memberId) {
		Connection conn = getConnection();
		Member member = memberDao.selectId(conn,memberId);
		close(conn);
		return member;
	}
	/*
	 * 3.이름으로 조회
	 */
	public Member selectName(String memberName) {
		Connection conn = getConnection();
		Member member = memberDao.selectName(conn,memberName);
		close(conn);
		return member;
	}
	/*
	 * 4. 회원가입
	 */
	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = memberDao.insertMember(conn,member);
		if (result>0) {
			commit(conn);
			System.out.println("회원가입 완료");
		}
		
		else {
			rollback(conn);
			System.out.println("회원가입 실패");			
		}
		close(conn);
		return result;
	}

	/*
	 * 5.회원정보 변경
	 */

	public int updateMemberPw(Member m, String memberId, String memberPw) {
		 Connection conn = getConnection();
		 int result = memberDao.updateMemberPw(conn,m,memberId,memberPw);
		 if(result > 0) {
			 commit(conn);
			 System.out.println("회원정보 변경 완료");
		 }
		 else {
			 rollback(conn);
			 System.out.println("회원정보 변경 실패");
		 }
		 close(conn);
		 return result;
	}
	
	public int updateMemberEmail(Member m, String memberId, String memberPw) {
		Connection conn = getConnection();
		int result = memberDao.updateMemberEmail(conn,m,memberId,memberPw);
		if(result > 0) {
			commit(conn);
			System.out.println("회원정보 변경 완료");
		}
		else {
			rollback(conn);
			System.out.println("회원정보 변경 실패");
		}
		close(conn);
		return result;
	}
	
	public int updateMemberPhone(Member m, String memberId, String memberPw) {
		 Connection conn = getConnection();
		 int result = memberDao.updateMemberPhone(conn,m,memberId,memberPw);
		 if(result > 0) {
			 commit(conn);
			 System.out.println("회원정보 변경 완료");
		 }
		 else {
			 rollback(conn);
			 System.out.println("회원정보 변경 실패");
		 }
		 close(conn);
		 return result;
	}
	
	public int updateMemberAdd(Member m, String memberId, String memberPw) {
		 Connection conn = getConnection();
		 int result = memberDao.updateMemberAdd(conn,m,memberId,memberPw);
		 if(result > 0) {
			 commit(conn);
			 System.out.println("회원정보 변경 완료");
		 }
		 else {
			 rollback(conn);
			 System.out.println("회원정보 변경 실패");
		 }
		 close(conn);
		 return result;
	}
	
	public int deleteMember(String memberId, String memberPw) {
		Connection conn = getConnection();
		int result = memberDao.deleteMember(conn,memberId,memberPw);
		 if(result > 0) {
			 commit(conn);
			 System.out.println("회원삭제 완료");
		 }
		 else {
			 rollback(conn);
			 System.out.println("회원삭제 실패");
		 }
		 close(conn);
		 return result;
		
	}





}
