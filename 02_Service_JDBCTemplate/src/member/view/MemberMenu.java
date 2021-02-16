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
					+ "0.프로그램 끝내기\n"
					+ "----------------------------------\n"
					+ "선택 : "; 
		
		do { //무조건 한번은 실행되는 구조
			System.out.print(menu);
			String choice = sc.next();
			List<Member> list = null;
			Member member = null;
			String memberId = null;
			String memberPw = null;
			String memberName = null;
			
			switch(choice) {
			case "1": 
				list = memberController.selectAll();
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
				subMenu();
				mainMenu();
			case "6": 
				memberId = inputMemberId("삭제");
				memberPw = inputMemberPassword("삭제");
				memberController.deleteMember(memberId,memberPw);
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
	
	public void subMenu() {
		do {
			String submenu ="****** 회원 정보 변경 메뉴******\r\n" + 
					"1. 암호변경\r\n" + 
					"2. 이메일변경\r\n" + 
					"3. 전화번호변경\r\n" + 
					"4. 주소 변경\r\n" + 
					"9. 메인메뉴 돌아가기\n"
					+ "선택 : ";
			
			Member m = null;
			System.out.print(submenu);
			
			switch(sc.next()) {
			case "1" : 
				memberId = inputMemberId("변경");
				memberPw = inputMemberPassword("변경");
				m = updateMemberPw();
				memberController.updateMemberPw(m,memberId,memberPw);
				break;
			case "2" : 
				memberId = inputMemberId("변경");
				memberPw = inputMemberPassword("변경");
				m = updateMemberEmail();
				memberController.updateMemberEmail(m,memberId,memberPw);
				break;
			case "3" : 
				memberId = inputMemberId("변경");
				memberPw = inputMemberPassword("변경");
				m = updateMemberPhone();
				memberController.updateMemberPhone(m,memberId,memberPw);
				break;
			case "4" : 
				memberId = inputMemberId("변경");
				memberPw = inputMemberPassword("변경");
				m = updateMemberAdd();
				memberController.updateMemberAdd(m,memberId,memberPw);
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
}
