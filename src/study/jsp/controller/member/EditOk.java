package study.jsp.controller.member;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import study.jsp.helper.BaseController;
import study.jsp.helper.FileInfo;
import study.jsp.helper.RegexHelper;
import study.jsp.helper.UploadHelper;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.MemberService;
import study.jsp.mysite.service.impl.MemberServiceImpl;

/**
 * Servlet implementation class EditOk
 */
@WebServlet("/member/edit_ok.do")
public class EditOk extends BaseController {
	private static final long serialVersionUID = 6184137696172840042L;

	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	RegexHelper regex;
	UploadHelper upload;
	MemberService memberService;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		regex = RegexHelper.getInstance();
		upload = UploadHelper.getInstance();

		memberService = new MemberServiceImpl(log, sqlSession);
		Member loginInfo = (Member) web.getSession("loginInfo");
		/** 로그인 여부 검사 */
		if (web.getSession("loginInfo") == null) {
			web.redirect(web.getRootPath() + "/index.do", "로그인 후에 이용가능합니다.");
			return null;
		}
		/** 파일이 포함된 POST 파라미터 받기 */
		
		try {
			upload.multipartRequest(request);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, "회원정보 데이터가 잘못되었습니다.");
			return null;
		}
		
		Map<String, String> paramMap = upload.getParamMap();
		String userPw = paramMap.get("user_pw");
		String newUserPw = paramMap.get("new_user_pw");
		String newUserPwRe = paramMap.get("new_user_pw_re");
		String name = paramMap.get("name");
		String email = paramMap.get("email");
		String tel = paramMap.get("tel");
		String birthdate = paramMap.get("birthdate");
		String gender = paramMap.get("gender");
		String postcode = paramMap.get("postcode");
		String addr1 = paramMap.get("addr1");
		String addr2 = paramMap.get("addr2");
		String addr3 = paramMap.get("addr3");
		String imgDel = paramMap.get("img_del");

		log.debug("userPw = " + userPw);
		log.debug("newUserPw = " + newUserPw);
		log.debug("newUserPwRe = " + newUserPwRe);
		log.debug("name = " + name);
		log.debug("email = " + email);
		log.debug("tel = " + tel);
		log.debug("birthdate = " + birthdate);
		log.debug("gender = " + gender);
		log.debug("postcode = " + postcode);
		log.debug("addr1 = " + addr1);
		log.debug("addr2 = " + addr2);
		log.debug("addr3 = " + addr3);
		log.debug("imgDel = " + imgDel);

		/** 입력값의 유효성 검사 */

		// 현재비밀번호 검사
		if (!regex.isValue(userPw)) {
			sqlSession.close();
			web.redirect(null, "비밀번호를 입력하세요.");
			return null;
		}
		// 변경할 비밀번호 검사

		// 비밀번호 검사
		if (regex.isValue(newUserPw)) {
			if (!regex.isEngNum(newUserPw) || newUserPw.length() > 20) {
				sqlSession.close();
				web.redirect(null, "비밀번호는 숫자와 영문조합으로 20자까지만 가능합니다.");
				return null;
			}
			// 비밀번호 확인
			if (!newUserPw.equals(newUserPwRe)) {
				sqlSession.close();
				web.redirect(null, "비밀번호가 맞지않습니다.");
				return null;
			}
		}
		// 이름 검사
		if (!regex.isValue(name)) {
			sqlSession.close();
			web.redirect(null, "이름을 입력하세요.");
			return null;
		}
		if (!regex.isKor(name)) {
			sqlSession.close();
			web.redirect(null, "이름은 한글만 가능합니다.");
			return null;
		}
		if (name.length() < 2 || name.length() > 10) {
			sqlSession.close();
			web.redirect(null, "이름은 2~10글자 까지만 가능합니다.");
			return null;
		}
		// 이메일 검사
		if (!regex.isValue(email)) {
			sqlSession.close();
			web.redirect(null, "이메일을 입력하세요.");
			return null;
		}
		if (!regex.isEmail(email)) {
			sqlSession.close();
			web.redirect(null, "이메일 형식이 잘못되었습니다.");
			return null;
		}
		if (!regex.isValue(tel)) {
			sqlSession.close();
			web.redirect(null, "연락처를 입력하세요.");
			return null;
		}
		if (!regex.isTel(tel) && !regex.isCellPhone(tel)) {
			sqlSession.close();
			web.redirect(null, "연락처 형식이 잘못되었습니다.");
			return null;
		}
		// 생년월일 검사
		if (!regex.isValue(birthdate)) {
			sqlSession.close();
			web.redirect(null, "생년월일을 입력하세요.");
			return null;
		}
		// 성별 검사
		if (!regex.isValue(gender)) {
			sqlSession.close();
			web.redirect(null, "성별을 입력하세요.");
			return null;
		}
		if (!gender.equals("M") && !gender.equals("F")) {
			sqlSession.close();
			web.redirect(null, "성별이 잘못되었습니다.");
			return null;
		}
		
		/** 프로필 사진의 삭제가 요청된 경우 */
		if(regex.isValue(imgDel) && imgDel.equals("Y")) {
			upload.removeFile(loginInfo.getProfileImg());
		}
		
		/** 업로드 된 파일 정보 추출 */
		List<FileInfo> fileList = upload.getFileList();
		String profileImg = null;
		
		if(fileList.size() > 0) {
			// 단일 업로드이므로 0번째 항목만 가져온다.
			FileInfo info = fileList.get(0);
			profileImg = info.getFileDir()+"/"+info.getFileName();
		}
		log.debug("profileImg = "+profileImg);
		
		/** 전달받은 파라미터 beans에 넣기 */
		Member member = new Member();
		member.setId(loginInfo.getId());
		member.setUserPw(userPw);
		member.setName(name);
		member.setEmail(email);
		member.setTel(tel);
		member.setBirthdate(birthdate);
		member.setPostcode(postcode);
		member.setGender(gender);
		member.setAddr1(addr1);
		member.setAddr2(addr2);
		member.setAddr3(addr3);
		member.setNewUserPw(newUserPw);
		
		// 이미지 처리
		if(profileImg != null) {
			// 이미지가 업로드 되었다면 beans에 정보를 넣기
			member.setProfileImg(profileImg);
		}else if (profileImg == null) {
			// 이미지가 업로드 되지 않았다면
			// 삭제만 체크했을경우
			if(imgDel != null && imgDel.equals("Y")) {
				// sql에서 null로 넣기 위한 분기 설정
				member.setProfileImg("");
			}
		}
		
		/** Service를 통한 DB저장 처리 */
		Member editInfo = null;
		
		try {
			memberService.selectMemberPasswordCnt(member);
			memberService.updateMember(member);
			editInfo = memberService.selectMember(member);
		}catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
		} finally {
			sqlSession.close();
		}
		
		/** 세션, 쿠키 갱신 */
		// 프로필 이미지 썸네일 정보 삭제 
		if(imgDel != null && imgDel.equals("Y")) {
			web.removeCookie("profileThumbnail");
		}
		String newProfileImg = editInfo.getProfileImg();
		if(profileImg != null) {
			try {
				String profileThumbnail = upload.createThumbnail(newProfileImg, 40, 40, true);

				web.setCookie("profileThumbnail", profileThumbnail, -1);
			}catch (Exception e) {
				web.redirect(null, e.getLocalizedMessage());
				return null;
			}
		}
		/** 조회된 정보 세션에 저장*/
		web.removeSession("loginInfo");
		web.setSession("loginInfo", editInfo);
		
		/** 수정 완료 -> 수정 페이지로 이동 */
		web.redirect(web.getRootPath() + "/member/edit.do", "회원정보가 수정되었습니다.");

		return null;
	}

}
