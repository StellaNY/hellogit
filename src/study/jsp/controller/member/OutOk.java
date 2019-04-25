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
import study.jsp.helper.RegexHelper;
import study.jsp.helper.UploadHelper;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.MemberService;
import study.jsp.mysite.service.impl.MemberServiceImpl;

@WebServlet("/member/out_ok.do")
public class OutOk extends BaseController {
	
	private static final long serialVersionUID = -5262784492029822045L;
	
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

		// 로그인한 세션 정보 (회원 정보) 
		Member loginInfo = (Member) web.getSession("loginInfo");
		if(loginInfo == null) {
			web.redirect(web.getRootPath()+"/index.do", "로그인 후에 이용 가능합니다.");
			return null;
		}
		
		String userPw = web.getString("user_pw");
		log.debug("userPw = " + userPw);

		// 비밀번호 검사
		if(userPw == null) {
			sqlSession.close();
			web.redirect(null, "비밀번호를 입력하세요.");
			return null;
		}
		
		Member member = new Member();
		member.setId(loginInfo.getId());
		member.setUserPw(userPw);
		
		try {
			//비밀번호 검사
			memberService.selectMemberPasswordCnt(member);
			
			//탈퇴
			memberService.deleteMember(member);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}finally {
			sqlSession.close();
		}
		
		//탈퇴가 되었다면 프로필 이미지 삭제
		upload.removeFile(loginInfo.getProfileImg());
		
		//정상탈퇴가 되면 로그아웃 및 페이지 이동
		web.removeAllSession();
		web.redirect(web.getRootPath()+"/index.do", "탈퇴되었습니다.");
		
		return null;
	}

}
