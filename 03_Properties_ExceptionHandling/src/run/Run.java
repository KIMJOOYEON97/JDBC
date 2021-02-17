package run;

import member.view.MemberMenu;

public class Run {

	public static void main(String[] args) {
		new MemberMenu().mainMenu(); //객체만들어주고mainMenu메서드 실행
		// 사용자에게 보여질 메뉴
		System.out.println("---- 프로그램 종료 -----");
	}

}
