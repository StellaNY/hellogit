package study.jsp.controller.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import study.jsp.helper.BaseController;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.model.Member;

@WebServlet("/member/logout.do")
public class Logout extends BaseController {

	private static final long serialVersionUID = -5991646481751136881L;

	Logger log;
	WebHelper web;
	
	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		web = WebHelper.getInstance(request, response);
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if(loginInfo == null) {
			web.redirect(web.getRootPath()+"/index.do", "로그인 후에 이용 가능합니다.");
			return null;
		}
		
		// 로그아웃 처리 -> 모든 세션 제거
		web.removeAllSession();

		web.redirect(web.getRootPath()+"/index.do", "로그아웃 되었습니다.");
		
		return null;
	}
}
