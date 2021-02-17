package member.view;

import java.util.List;
import java.util.Scanner;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {
	
	private Scanner sc = new Scanner(System.in);
	private  MemberController memberController = new MemberController();
	private String memberId;
	private String memberPw;
	
	public void mainMenu() {
		String menu = "========== 회원 관리 프로그램 ==========\n"
					+ "1.회원 전체조회\n"
					+ "2.회원 아이디조회\n" 
					+ "3.회원 이름조회\n" 
					+ "4.회원 가입\n" 
					+ "5.회원 정보변경\n" 
					+ "6.회원 탈퇴\n" 
					+ "7.탈퇴한 회원조회\n"
					+ "0.프로그램 끝내기\n"
					+ "----------------------------------\n"
					+ "선택 : "; 
		
		do { //무조건 한번은 실행되는 구조
			System.out.print(menu);
			String choice = sc.next();
			List<Member> list =memberController.selectAll();
			Member member = null;
			String memberId = null;
			String memberPw = null;
			String memberName = null;
			
			switch(choice) {
			case "1": 
				displayMemberList(list);
				break;
			case "2": 
				memberId = inputMemberId("조회");
				member = memberController.selectId(memberId);
				displayMember(member);
				break;
			case "3": 
				memberName = inputMemberName();
				member  = memberController.selectName(memberName);
				displayMember(member);
				break;
			case "4": 
				member=inputMember();
				memberController.insertMember(member);
				break;
			case "5": 
				memberId = inputMemberId("변경");
				memberPw = inputMemberPassword("변경");
				boolean exist = userExist(memberId, memberPw,list);
				if(exist) {
					System.out.println("회원정보가 있습니다");
					subMenu(memberId,memberPw);
				}
				else {
					System.out.println("회원정보가 없습니다.");
				}
				break;
			case "6": 
				memberId = inputMemberId("삭제");
				memberPw = inputMemberPassword("삭제");
				member = deleteMemberSearch(memberId,memberPw,list);
				if(userExist(memberId, memberPw,list)&& member != null) {
					System.out.print("존재하는 회원입니다 삭제하시겠습니까? (y/n) : ");
					if(sc.next().toLowerCase().charAt(0)=='y') {
						memberController.deleteMember(member);
//						memberController.deleteMemberInsert(member);
					}
				}
				else {
					System.out.println("존재하지 않는 회원입니다.");
				}
				break;
			case "7":
				//7.탈퇴회원조회 기능을 추가하세요.
//				(탈퇴회원 조회시에는 탈퇴일이 출력되야 함.)
				list = memberController.deleteMemberAll();
				displayMemberList(list);
				break;
			case "0": 
				System.out.print("정말로 끝내시겠습니까? (y/n) : ");
				if(sc.next().toLowerCase().charAt(0) =='y')
					return;
			default:
				System.out.println("잘못 입력하셨습니다.");
			}
		} while(true);
		
	}

	public void subMenu(String memberId, String memberPw) {
		do {
			String submenu ="****** 회원 정보 변경 메뉴******\r\n" + 
					"1. 암호변경\r\n" + 
					"2. 이메일변경\r\n" + 
					"3. 전화번호변경\r\n" + 
					"4. 주소 변경\r\n" + 
					"9. 메인메뉴 돌아가기\n"
					+ "선택 : ";
			
			Member m = null;
			List<Member> list = null;
			System.out.print(submenu);
			
			switch(sc.next()) {
			case "1" : 
				m = updateMemberPw();
				memberController.updateMemberPw(m);
				break;
			case "2" : 
				m = updateMemberEmail();
				memberController.updateMemberEmail(m);
				break;
			case "3" : 
				m = updateMemberPhone();
				memberController.updateMemberPhone(m);
				break;
			case "4" : 
				m = updateMemberAdd();
				memberController.updateMemberAdd(m);
				break;
			case "9" : 
				System.out.print("메인화면으로 돌아가시겠습니까?(y/n) : ");
				if(sc.next().toLowerCase().charAt(0)=='y')
					return;
				break;
			default :
				System.out.println("잘못입력하셨습니다.");
				
			}
		}while(true);
		
	}
	
	/**
	 * 삭제할 객체 찾기
	 */
	
	private Member deleteMemberSearch(String memberId, String memberPw,List<Member> list) {
		for(Member member : list) {
			if(member.getMemberId().equals(memberId) && member.getPassword().equals(memberPw)) {
				return member;
			}
		}
		return null;
	}
	/**
	 * 회원 유무 조회
	 * @param list 
	 */
	private boolean userExist(String memberId, String memberPw, List<Member> list) {
		for(Member m : list) {
			if(m.getMemberId().equals(memberId) && m.getPassword().equals(memberPw)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 5.회원정보수정
	 * @return
	 */
	private Member updateMemberPw() {
		Member m = new Member();
		System.out.print("변경할 회원 암호 : ");
		m.setPassword(sc.next());
		return m;
	}
	private Member updateMemberEmail() {
		Member m = new Member();
		System.out.print("변경할 회원 이메일 : ");
		m.setEmail(sc.next());
		return m;
	}
	private Member updateMemberPhone() {	
		Member m = new Member();
		System.out.print("변경할 회원 전화번호(-빼고입력): ");
		m.setPhone(sc.next());
		return m;
	}
	private Member updateMemberAdd() {
		Member m = new Member();
		System.out.print("변경할 회원 주소 : ");
		sc.nextLine();
		m.setAddress(sc.nextLine());
		return m;
	}
	/**
	 * 4.회원가입
	 * @return
	 */
	private Member inputMember() {
		System.out.println("새로운 회원정보를 입력하세요.");
		Member member = new Member();
		System.out.print("아이디 : ");
		member.setMemberId(sc.next());
		System.out.print("이름 : ");
		member.setMemberName(sc.next());
		System.out.print("비밀번호: ");
		member.setPassword(sc.next());
		System.out.print("나이: ");
		member.setAge(sc.nextInt());
		System.out.print("성별(M/F) : "); //m, f 안됌
		member.setGender(String.valueOf(sc.next().toUpperCase().charAt(0)));//String.valueOf ->String으로 변화
		System.out.print("이메일 : ");
		member.setEmail(sc.next());
		System.out.print("전화번호(-빼고 입력) : ");
		member.setPhone(sc.next());
		sc.nextLine(); //버퍼에 남은 개행문자 날리기 용(next계열 - nextLine) next와 nextLine 사이 한번 
		System.out.print("주소: ");
		member.setAddress(sc.nextLine());
		System.out.print("취미 : ");
		member.setHobby(sc.next());
		return member;
	}
	/**
	 * 3. 이름으로 조회
	 */
	private String inputMemberName() {
		System.out.print("조회할 이름을 입력 : ");
		return sc.next();
	}
	
	/**
	 * 2-1. 회원 비밀번호 확인
	 * @return
	 */
	private String inputMemberPassword(String select) {
		System.out.print(select+"할 아이디의 비밀번호 입력 :");
		return sc.next();
	}
	
	/**
	 * 2. 회원 아이디 확인
	 * @return
	 */
	private String inputMemberId(String select) {
		System.out.print(select+"할 아이디 입력 :");
		return sc.next();
	}
	/**
	 * 1행의 회원정보 출력
	 * @param member
	 */
	private void displayMember(Member member) {
		if(member != null) System.out.println(member);
		else System.out.println("조회된 회원정보가 없습니다.");
	}
	/**
	 * n행의 회원정보 출력
	 * 
	 */
	private void displayMemberList(List<Member> list) {
		if(list != null && !list.isEmpty()) {
			System.out.println("===================================");
			for(int i = 0; i < list.size(); i++)
				System.out.println((i+1)+":"+list.get(i));
			System.out.println("===================================");
		}
		else {
			System.out.println("조회된 회원정보가 없습니다.");
		}
	}

	/**
	 * 사용자에게 오류메세지 출력하기
	 * @param errorMsg
	 */
	public void displayError(String errorMsg) {
		System.err.println(errorMsg);
		
	}
}
