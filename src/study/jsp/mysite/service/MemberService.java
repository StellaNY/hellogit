package study.jsp.mysite.service;

import study.jsp.mysite.model.Member;

/**
 * 
 * <pre>
 * @Project : mysite
 * @PackageNm : study.jsp.mysite.service
 * </pre>
 * 
 * @FileName : MemberService.java
 * @author : "StellaNY (stella10137@gmail.com)"
 * @Description : Member Service 계층
 * @Date : 2019-04-26
 *
 */
public interface MemberService {
	/**
	 * 아이디 중복검사
	 * 
	 * @param member - 아이디
	 * @throws Exception - 중복된 데이터인 경우 예외 발생
	 */
	public void selectChkMemberId(Member member) throws Exception;

	/**
	 * 이메일 중복검사
	 * 
	 * @param member - 이메일
	 * @throws Exception - 중복된 데이터인 경우 예외 발생
	 */
	public void selectChkMemberEmail(Member member) throws Exception;

	/**
	 * 회원가입(아이디, 이메일 중복검사 후 가입)
	 * 
	 * @param member - 일련번호를 제외한 모든 회원의 정보
	 * @throws Exception
	 */
	public void insertJoinMember(Member member) throws Exception;

	/**
	 * 로그인
	 * 
	 * @param member
	 * @return 회원정보
	 * @throws Exception
	 */
	public Member selectLoginInfo(Member member) throws Exception;

	/**
	 * 비밀번호 재설정
	 * 
	 * @param member - 이메일주소, 비밀번호
	 * @throws Exception
	 */
	public void updateMemberPwByEmail(Member member) throws Exception;

	/**
	 * 탈퇴 - 비밀번호 검사
	 * 
	 * @param member - 이메일주소, 비밀번호
	 * @throws Exception
	 */
	public void selectMemberPasswordCnt(Member member) throws Exception;

	/**
	 * 회원 탈퇴
	 * 
	 * @param member - 회원번호
	 * @throws Exception
	 */
	public void deleteMember(Member member) throws Exception;

	/**
	 * 회원 정보 수정
	 * 
	 * @param member - 회원정보
	 * @throws Exception
	 */
	public void updateMember(Member member) throws Exception;

	/**
	 * 회원 정보 조회
	 * 
	 * @param member
	 * @return 회원정보
	 * @throws Exception
	 */
	public Member selectMember(Member member) throws Exception;
}