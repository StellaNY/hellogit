package study.jsp.controller.member;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import study.jsp.helper.BaseController;
import study.jsp.helper.FileHelper;
import study.jsp.helper.MailHelper;
import study.jsp.helper.RegexHelper;
import study.jsp.helper.UploadHelper;
import study.jsp.helper.UtilHelper;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.MemberService;
import study.jsp.mysite.service.impl.MemberServiceImpl;

/**
 * 임시번호를 생성을 하고 이메일 발송을 위한 Controller
 * @author "StellaNY (stella10137@gmail.com)"
 * @since 2019. 03. 25.
 *
 */
@WebServlet("/member/find_pw_ok.do")
public class FindPwOk extends BaseController {
	private static final long serialVersionUID = -7355869973332684120L;
	
	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	RegexHelper regex;
	UploadHelper upload;
	UtilHelper util;
	MailHelper mail;
	FileHelper file;
	
	MemberService memberService;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		regex = RegexHelper.getInstance();
		upload = UploadHelper.getInstance();
		util = UtilHelper.getInstance();
		mail = MailHelper.getInstance();
		file = FileHelper.getInstance();
		
		memberService = new MemberServiceImpl(log, sqlSession);
		
		if(web.getSession("loginInfo") != null) {
			sqlSession.close();
			web.redirect(web.getRootPath()+"/index.do", "이미 로그인 중입니다.");
			return null;
		}
		
		String email = web.getString("email");
		
		log.debug("email = "+ email);

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
		
		/** 비밀번호 재발급 */
		String newPw = util.getRandomPassword(10);
		
		Member member = new Member();
		member.setEmail(email);
		member.setUserPw(newPw);
		
		try {
			memberService.updateMemberPwByEmail(member);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}finally {
			sqlSession.close();
		}
		
		/** 이메일 전송 */
		String sender = "rlasksdud53@gmail.com";
		String subject = "STELLA 비밀번호 변경 안내입니다.";
		String content = "";
		
//		content = "회원님의 새로운 비밀번호는 <strong>"+newPw+"</strong> 입니다.";

		String template = "http://localhost:8080/mysite/emailTemplate"; 

		content = file.readUrl(template, "UTF-8");	// html 파일을 읽어서 String 객체로 만듬
		content = content.replace("#{newPassword}", newPw);	// 비밀번호 넣기 
		
		try {
			mail.sendMail(sender, email, subject, content);
		} catch (MessagingException e) {
			web.redirect(null, "메일 발송에 실패했습니다. 관리자에게 문의 바랍니다.");
			return null;
		}
		
		web.redirect(null, "새로운 비밀번호가 메일로 발송되었습니다.");
		return null;
	}

}
