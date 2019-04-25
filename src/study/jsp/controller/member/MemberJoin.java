package study.jsp.controller.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import study.jsp.helper.BaseController;
import study.jsp.helper.WebHelper;

/**
 * 회원 가입폼으로 가는 Servlet
 * @author "StellaNY (stella10137@gmail.com)"
 *
 */
@WebServlet("/member/join.do")
public class MemberJoin extends BaseController {
	private static final long serialVersionUID = 8987763673062497674L;
	
	WebHelper web;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		web = WebHelper.getInstance(request, response);
		
		/** 로그인 여부 검사 */
		if(web.getSession("loginInfo") != null) {
			web.redirect(web.getRootPath()+"/index.do", "이미 로그인 하셨습니다.");
			return null;
		}
		
		return "member/join";
	}

}
