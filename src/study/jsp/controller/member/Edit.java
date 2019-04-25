package study.jsp.controller.member;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import study.jsp.helper.BaseController;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.model.Member;

/**
 * Servlet implementation class Edit
 */
@WebServlet("/member/edit.do")
public class Edit extends BaseController {

	private static final long serialVersionUID = -8575884181447536564L;
	WebHelper web;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		web = WebHelper.getInstance(request, response);

		Member loginInfo = (Member) web.getSession("loginInfo");
		
		if(loginInfo == null) {
			web.redirect(web.getRootPath()+"/index.do", "로그인 후에 이용 가능합니다.");
			return null;
		}
		
		return "member/edit";
	}
	

}
