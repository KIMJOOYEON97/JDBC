package member.view;

import java.util.List;
import java.util.Scanner;

import javax.swing.text.html.HTMLDocument.Iterator;

import member.controller.MemberController;
import member.model.vo.Member;

public class MemberMenu {
	
	private MemberController memberController = new MemberController();
	//필드로 선언해줌

	//스캐너 필드로 선언해서 다른 메소드하고도 공유
	private Scanner sc= new Scanner(System.in);
	
	public void mainMenu() {
		String menu ="======= 회원 관리 프로그램 =======\n"
				+ "1. 회원 전체 조회 \n"
				+ "2. 회원 아이디 조회\n"
				+ "3. 회원 이름 조회\n"
				+ "4. 회원 가입\n"
				+ "5. 회원 정보변경\n"
				+ "6. 회원탈퇴\n"
				+ "0. 프로그램 끝내기\n"
				+ "--------------------------\n"
				+ "선택 : ";
		
		
		while(true) {
			System.out.print(menu);
			int choice = sc.nextInt();
			Member member = null;
			int result = 0;
			String msg =null;
			List<Member> list = null;
			String memberId = null;
			String memberName = null;
			String memberUpdate = null;
			String memberDelete = null;
			
			switch(choice) {
				case 1:
					list = memberController.selectAll();
					displayMemberList(list);
					break;
				case 2: 
					//아이디를 잘 입력 -> 1행 입력X ->0행
					memberId = inputMemberId();
					member = memberController.selectOne(memberId);
					displayMember(member);
					break;
				case 3: 
					memberName =  inputMemberName();
					member = memberController.selectName(memberName);
					displayMemberName(member);
					break;
				case 4: 
					//1.신규회원정보 입력 ->Member객체
					member = inputMember();
					System.out.println("신규회원 확인 : "+member);
					//2.controller에 회원가입 요청(메소드호출) 이때 Member객체를 전달. 
					// ->int리턴(처리된 행의 개수)
					result = memberController.insertMember(member);
					//3.int에 따른 분기처리
					msg = result > 0 ? "회원 가입 성공!" :"회원 가입 실패!";
					displayMsg(msg);
					break;
				case 5: 
					memberUpdate = inputUpdateId();
					list = memberController.selectAll();
					member = searchMem(memberUpdate,list);
					//입력한 id에 해당하는 객체를 전달
					if(member != null) {
						member = inputUpdate(member);						
						result = memberController.selectUpdate(memberUpdate,member);
						msg = result > 0 ? "회원 가입 성공!" :"회원 가입 실패!";
						displayMsg(msg);
						displayMember(member);
					}
					break;
				case 6: 
					memberDelete = inputDelete();
					result = memberController.selectDelete(memberDelete);
					msg = result > 0 ? "회원 탈퇴 성공!" :"회원 탈퇴 실패!";
					displayMsg(msg);
					break;
				case 0:
					System.out.print("정말로 끝내시겠습니까?(y/n) : ");
					if(sc.next().toLowerCase().charAt(0)== 'y')
						return; //현재메소드(mainMenu)를 호출한 곳으로 돌아감
					break;
				default:
					System.out.println("---잘못입력하셨습니다---");
			}
		}
		
	}

	
	



	private String inputDelete() {
		System.out.print("삭제할 id 입력 : ");
		return sc.next();
	}

	//입력한 id에 해당하는 객체를 전달
	private Member searchMem(String memberUpdate, List<Member> list) {
		for(Member member :list) {
			if(memberUpdate.equals(member.getMemberId())) {
				return member;
			}	
		}
		return null;
	}
	

	/**
	 * 수정할 정보 입력
	 * @param memberUpdate 
	 * @return
	 */
	private Member inputUpdate(Member member) {
		System.out.println("수정할 회원정보를 입력하세요.");
			System.out.print("비밀번호: ");
			member.setPassword(sc.next());
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
	 * DB에서 정보를 수정할 id을 입력
	 * @return
	 */
	private String inputUpdateId() {
		System.out.print("수정할 id 입력 : ");
		return sc.next();
	}

	/**
	 * DB에서 조회한 이름 출력
	 * @param member
	 */
	private void displayMemberName(Member member) {
		if(member == null)
			System.out.println(">>>> 조회된 회원이 없습니다.");
		else {
			System.out.println("*******************************************************");
			System.out.println(member);
			System.out.println("*******************************************************");
		}
	}

	/**
	 * DB에서 이름 조회
	 * 
	 */
	private String inputMemberName() {
		System.out.print("조회할 이름 입력 : ");
		return sc.next();
	}
 
	/**
	 * DB에서 조회한 1명의 회원 출력
	 * @param member
	 */
	private void displayMember(Member member) {
		if(member == null)
			System.out.println(">>>> 조회된 회원이 없습니다.");
		else {
			System.out.println("*******************************************************");
			System.out.println(member);
			System.out.println("*******************************************************");
	
		}
	}
	
	/**
	 * 조회할 회원아이디 입력
	 * @return
	 */
	private String inputMemberId() {
		System.out.print("조회할 아이디 입력 : ");
		return sc.next();
	}

	/**
	 * DB에서 조회된 회원객체 n개를 출력
	 * @param list
	 */
	private void displayMemberList(List<Member> list) {
		if(list == null || list.isEmpty()) {
			System.out.println(">>>> 조회된 행이 없습니다.");
		}
		else {
			System.out.println("************************************************************************************");
			for(Member m :list) {
				System.out.println(m);
			}
			System.out.println("************************************************************************************");
		}
	}

	/**
	 * DML처리결과 통보용
	 */
	private void displayMsg(String msg) {
		System.out.println(">>> 처리결과 : " + msg);
	}

	/**
	 * 신규회원 정보 입력
	 * 
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

}
