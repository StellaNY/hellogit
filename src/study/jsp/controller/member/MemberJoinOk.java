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
 * 회원가입 처리하는 Servlet
 * @author "StellaNY (stella10137@gmail.com)"
 *
 */
@WebServlet("/member/join_ok.do")
public class MemberJoinOk extends BaseController {
	private static final long serialVersionUID = -3202838770223056871L;
	
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

		/** 로그인 여부 검사 */
		if(web.getSession("loginInfo") != null) {
			web.redirect(web.getRootPath()+"/index.do", "이미 로그인 하셨습니다.");
			return null;
		}

		/** 파일이 포함된 post 파라미터 받기*/
		try {
			upload.multipartRequest(request);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, "multipart 데이터가 아닙니다.");
			
			log.error(e.getLocalizedMessage());
			return null;
		}

		Map<String, String> paramMap = upload.getParamMap();
		String userId = paramMap.get("user_id");
		String userPw = paramMap.get("user_pw");
		String userPwRe = paramMap.get("user_pw_re");
		String name = paramMap.get("name");
		String email = paramMap.get("email");
		String tel = paramMap.get("tel");
		String birthdate = paramMap.get("birthdate");
		String gender = paramMap.get("gender");
		String postcode = paramMap.get("postcode");
		String addr1 = paramMap.get("addr1");
		String addr2 = paramMap.get("addr2");
		String addr3 = paramMap.get("addr3");
		
		log.debug("userId = "+userId);
		log.debug("userPw = "+userPw);
		log.debug("userPwRe = "+userPwRe);
		log.debug("name = "+name);
		log.debug("email = "+email);
		log.debug("tel = "+tel);
		log.debug("birthdate = "+birthdate);
		log.debug("gender = "+gender);
		log.debug("postcode = "+postcode);
		log.debug("addr1 = "+addr1);
		log.debug("addr2 = "+addr2);
		log.debug("addr3 = "+addr3);
		
		/** 입력값 유효성 검사*/
		// 아이디 검사
		if(!regex.isValue(userId)) {
			sqlSession.close();
			web.redirect(null, "아이디를 입력하세요.");
			return null;
		}
		if(!regex.isEngNum(userId) || userId.length() > 20) {
			sqlSession.close();
			web.redirect(null, "아이디는 숫자와 영문조합으로 20자까지만 가능합니다.");
			return null;
		}
		// 비밀번호 검사
		if(!regex.isValue(userPw)) {
			sqlSession.close();
			web.redirect(null, "비밀번호를 입력하세요.");
			return null;
		}
		if(!regex.isEngNum(userPw) || userPw.length() > 20) {
			sqlSession.close();
			web.redirect(null, "비밀번호는 숫자와 영문조합으로 20자까지만 가능합니다.");
			return null;
		}
		// 비밀번호 확인
		if(!userPw.equals(userPwRe)) {
			sqlSession.close();
			web.redirect(null, "비밀번호가 맞지않습니다.");
			return null;
		}
		// 이름 검사
		if(!regex.isValue(name)) {
			sqlSession.close();
			web.redirect(null, "이름을 입력하세요.");
			return null;
		}
		if(!regex.isKor(name)) {
			sqlSession.close();
			web.redirect(null, "이름은 한글만 가능합니다.");
			return null;
		}
		if(name.length() < 2 || name.length() > 10) {
			sqlSession.close();
			web.redirect(null, "이름은 2~10글자 까지만 가능합니다.");
			return null;
		}
		// 이메일 검사
		if(!regex.isValue(email)) {
			sqlSession.close();
			web.redirect(null, "이메일을 입력하세요.");
			return null;
		}
		if(!regex.isEmail(email)) {
			sqlSession.close();
			web.redirect(null, "이메일 형식이 잘못되었습니다.");
			return null;
		}
		if(!regex.isValue(tel)) {
			sqlSession.close();
			web.redirect(null, "연락처를 입력하세요.");
			return null;
		}
		if(!regex.isTel(tel) && !regex.isCellPhone(tel)) {
			sqlSession.close();
			web.redirect(null, "연락처 형식이 잘못되었습니다.");
			return null;
		}
		// 생년월일 검사
		if(!regex.isValue(birthdate)) {
			sqlSession.close();
			web.redirect(null, "생년월일을 입력하세요.");
			return null;
		}
		// 성별 검사
		if(!regex.isValue(gender)) {
			sqlSession.close();
			web.redirect(null, "성별을 입력하세요.");
			return null;
		}
		if(!gender.equals("M") && !gender.equals("F")) {
			sqlSession.close();
			web.redirect(null, "성별이 잘못되었습니다.");
			return null;
		}
		
		/** 파일정보 추출*/
		List<FileInfo> fileList = upload.getFileList();
		String profileImg = null;
		
		if(fileList.size() > 0) {
			// 단일 업로드이므로 0번째 항목만 가져온다.
			FileInfo info = fileList.get(0);
			profileImg = info.getFileDir()+"/"+info.getFileName();
		}
		log.debug("profileImg = "+profileImg);
		
		/** 파라미터를 Beans에 넣기 */
		Member member = new Member();
		member.setUserId(userId);
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
		member.setProfileImg(profileImg);
		
		/** DB저장 처리 */
		try {
			memberService.insertJoinMember(member);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}
		
		/** 가입이 완료되어 메인페이지로 이동 */
		sqlSession.close();
		web.redirect(web.getRootPath()+"/index.do", "회원가입이 완료되었습니다.로그인해주세요.");
		return null;
	}

}
