package study.jsp.controller.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import study.jsp.helper.BaseController;
import study.jsp.helper.UploadHelper;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.MemberService;
import study.jsp.mysite.service.impl.MemberServiceImpl;

/**
 * Servlet implementation class MemberLoginOk
 */
@WebServlet("/member/login_ok.do")
public class MemberLoginOk extends BaseController {
	private static final long serialVersionUID = -8900706267230711582L;
	
	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	UploadHelper upload;
	MemberService memberService;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		upload = UploadHelper.getInstance();

		memberService = new MemberServiceImpl(log, sqlSession);

		/** 로그인 여부 검사 */
		if(web.getSession("loginInfo") != null) {
			web.redirect(web.getRootPath()+"/index.do", "이미 로그인 하셨습니다.");
			return null;
		}

		String userId = web.getString("user_id");
		String userPw = web.getString("user_pw");

		log.debug("userId = "+userId);
		log.debug("userPw = "+userPw);
		
		if(userId == null || userPw == null) {
			sqlSession.close();
			web.redirect(null, "아이디나 비밀번호가 없습니다.");
			return null;
		}

		/** 로그인 정보 조회 - 로그인 인증*/
		Member member = new Member();
		member.setUserId(userId);
		member.setUserPw(userPw);
		
		Member loginInfo = null;
		
		try {
			loginInfo = memberService.selectLoginInfo(member);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}
		/** 프로필 이미지 처리*/
		String profileImg = loginInfo.getProfileImg();
		if(profileImg != null) {
			try {
				String profileThumbnail = upload.createThumbnail(profileImg, 40, 40, true);

				web.setCookie("profileThumbnail", profileThumbnail, -1);
			}catch (Exception e) {
				web.redirect(null, e.getLocalizedMessage());
				return null;
			}
		}else {
			web.removeCookie("profileThumbnail");
		}
		/** 조회된 정보 세션에 저장*/
		web.setSession("loginInfo", loginInfo);
		
		/** 페이지 이동 */
		// 이전페이지 구하기
		//javascript로 이동된 경우 조회가 안된다.
		String movePage = request.getHeader("referer");
		if(movePage == null) {
			movePage = web.getRootPath()+"/index.do";
		}
		
		sqlSession.close();
		web.redirect(movePage, null);
		return null;
	}


}
