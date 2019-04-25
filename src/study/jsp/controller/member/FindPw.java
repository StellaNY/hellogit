package study.jsp.controller.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import study.jsp.helper.BaseController;
import study.jsp.helper.WebHelper;

/**
 * Servlet implementation class FindPw
 */
@WebServlet("/member/find_pw.do")
public class FindPw extends BaseController {
	private static final long serialVersionUID = -2572111654292982692L;
	
	WebHelper web;
	
	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		web = WebHelper.getInstance(request, response);
		
		/** 로그인 여부 검사 */
		if(web.getSession("loginInfo") != null) {
			web.redirect(web.getRootPath()+"/index.do", "이미 로그인 하셨습니다.");
			return null;
		}
		
		
		return "member/find_pw";
	}
       

}
