package study.jsp.helper;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * 메일 발송시 계정 정보를 리턴해주는 역할을 해주는 클래스
 * 
 * @author StellaNY
 */
/**
 * @author Administrator
 *
 */
public class STMTPAuthenticator extends Authenticator {
	private String senderMail;
	private String senderPw;

	/**
	 * 메일 발송시 계정 정보를 리턴해 주는 역할 
	 * 인증을 할 계정정보를 입력하기
	 * 보내는 사람의 이메일이 아닌 서버에서 사용할 인증 정보
	 */
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(senderMail, senderPw);
	}
	
	/* 값을 넣고 받기 위한 getter, setter 시작*/
	protected String getSenderMail() {
		return senderMail;
	}

	protected void setSenderMail(String senderMail) {
		this.senderMail = senderMail;
	}

	protected String getSenderPw() {
		return senderPw;
	}

	protected void setSenderPw(String senderPw) {
		this.senderPw = senderPw;
	}
	/* 값을 넣고 받기 위한 getter, setter 끝*/

	/**
	 * Default 값을 설정한 생성자로 
	 * 실행시 자동으로 입력
	 */
	public STMTPAuthenticator() {
		super();
		// default 값 설정
		this.senderMail = "rlasksdud53@gmail.com";
		this.senderPw = "zrszmcbpxocujkjv";
	}
	
	/**
	 * sender의 이메일과 비밀번호 입력받기 위한 constructor 
	 * @param senderMail
	 * @param senderPw
	 */
	public STMTPAuthenticator(String senderMail, String senderPw) {
		super();
		this.senderMail = senderMail;
		this.senderPw = senderPw;
	}

	/**
	 * 값 확인용 메서드
	 */
	@Override
	public String toString() {
		return "STMTPAuthenticator [senderMail=" + senderMail + ", senderPw=" + senderPw + "]";
	}

}
