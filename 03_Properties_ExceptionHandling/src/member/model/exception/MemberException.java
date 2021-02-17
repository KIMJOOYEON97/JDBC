package member.model.exception;
/**
 * checkedException : extends Exception
 * uncheckedException : extends RuntimeException
 * checked ⇒ 컴파일 중에 확인 가능 → 예외처리 안하면 오류 <강제화> RunTimeException 후손을 제외한.
 * unchecked ⇒ RunTimeException   후손
 *
 */

public class MemberException extends RuntimeException {

	public MemberException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public MemberException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public MemberException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
