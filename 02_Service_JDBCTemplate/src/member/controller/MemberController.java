package member.controller;

import java.util.List;

import member.model.service.MemberService;
import member.model.vo.Member;

public class MemberController {
	
	private MemberService memberService = new MemberService();

	public List<Member> selectAll() {
		return memberService.selectAll_();
	}

	public int insertMember(Member member) {
		return memberService.insertMember(member);
	}

	public Member selectId(String memberId) {
		return memberService.selectId(memberId);
	}

	public Member selectName(String memberName) {
		return memberService.selectName(memberName);
	}

	public int deleteMember(String memberId, String memberPw) {
		return memberService.deleteMember(memberId,memberPw);
	}
	

	public int updateMemberPw(Member m, String memberId, String memberPw) {
		return memberService.updateMemberPw(m,memberId,memberPw);
	}
	
	public int updateMemberEmail(Member m, String memberId, String memberPw) {
		return memberService.updateMemberEmail(m,memberId,memberPw);
	}

	public int updateMemberPhone(Member m, String memberId, String memberPw) {
		return memberService.updateMemberPhone(m,memberId,memberPw);
		
	}

	public int updateMemberAdd(Member m, String memberId, String memberPw) {
		return memberService.updateMemberAdd(m,memberId,memberPw);
		
	}

}
