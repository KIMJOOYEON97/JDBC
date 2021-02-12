package member.controller;

import java.util.List;

import member.model.dao.MemberDao;
import member.model.vo.Member;

/**
 * 
 * MVC패턴의 시작점이자 전체흐름을 제어.
 * 
 * view단으로부터 요청을 받아서 dao에 다시 요청.
 * 그 결과를 view단에 다시 전달.
 *
 */

public class MemberController {

	//dao에 보내기위해 dao객체가 필요하다
	private MemberDao memberDao = new MemberDao();

	public int insertMember(Member member) {
		return memberDao.insertMember(member);
	}

	public List<Member> selectAll() {
		return memberDao.selectAll();
	}

	public Member selectOne(String memberId) {
		return memberDao.selectOne(memberId);
	}

	public Member selectName(String memberName) {
		return memberDao.selectName(memberName);
	}

	public int selectUpdate(String memberUpdate, Member member) {
		return memberDao.selectUpdate(memberUpdate,member);
	}

	public int selectDelete(String memberDelete) {
		return memberDao.selectDelete(memberDelete);
	}


}
