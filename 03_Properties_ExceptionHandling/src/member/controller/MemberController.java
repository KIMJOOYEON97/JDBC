package member.controller;

import java.util.List;

import member.model.exception.MemberDeleteException;
import member.model.exception.MemberException;
import member.model.exception.MemberIdException;
import member.model.exception.MemberInsertException;
import member.model.exception.MemberNameException;
import member.model.exception.MemberPwException;
import member.model.exception.MemberUpdateException;
import member.model.service.MemberService;
import member.model.vo.Member;
import member.view.MemberMenu;

public class MemberController {
	
	private MemberService memberService = new MemberService();

	public List<Member> selectAll() {
		List<Member> list = null;
		try {
			list = memberService.selectAll_();
		}catch(MemberException e){
			//서버로깅
			//관리자 이메일 알림
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");//사용자에게 에러사항을 알림
			
		}
		return list;
	}

	public int insertMember(Member member) {
		int result = 0;
		try {
			result = memberService.insertMember(member);
		}catch(MemberInsertException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");
		}
		return result;
	}

	public Member selectId(String memberId) {
		Member member = null;
		try {
			member = memberService.selectId(memberId);
		}catch(MemberIdException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");			
		}
		return member;
	}
	
	public Member selectPw(String memberPw) {
		Member member = null;
		try {
			member = memberService.selectPw(memberPw);
		}catch(MemberPwException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");			
		}
		return member;
	}

	public Member selectName(String memberName) {
		Member member = null;
		try {
			member = memberService.selectName(memberName);
		}catch(MemberNameException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");			
		}
		return member;
	}

	public int deleteMember(Member member) {
		int result = 0;
		try {
			result = memberService.deleteMember(member);
		}catch(MemberDeleteException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");
		}
		return result;
	}
	
//	public int deleteMemberInsert(Member member) {
//		int result = 0;
//		try {
//			result = memberService.deleteMemberInsert(member);
//		}catch(MemberDeleteException e) {
//			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");
//		}
//		return result;
//	}
	
	public List<Member> deleteMemberAll() {
		List<Member> list = null;
		try {
			list = memberService.deleteMemberAll();
		}catch(MemberDeleteException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");
		}
		return list;
	}

	public int updateMemberPw(Member m) {
		int result = 0;
		try {
			result = memberService.updateMemberPw(m);
		}catch(MemberUpdateException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");
		}
		return result;
	}
	
	public int updateMemberEmail(Member m) {
		int result = 0;
		try {
			result = memberService.updateMemberEmail(m);
		}catch(MemberUpdateException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");
		}
		return result;
	}

	public int updateMemberPhone(Member m) {
		int result = 0;
		try {
			result = memberService.updateMemberPhone(m);
		}catch(MemberUpdateException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");
		}
		return result;
	}

	public int updateMemberAdd(Member m) {
		int result = 0;
		try {
			result = memberService.updateMemberAdd(m);
		}catch(MemberUpdateException e) {
			new MemberMenu().displayError(e.getMessage()+" : 관리자에게 문의하세요.");
		}
		return result;
	}

}
