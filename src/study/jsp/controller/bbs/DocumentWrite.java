package study.jsp.controller.bbs;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.jsp.helper.BaseController;
import study.jsp.helper.WebHelper;

/**
 * @author "StellaNY (stella10137@gmail.com)"
 * @since 2019. 03. 27.
 */
@WebServlet("/bbs/document_write.do")
public class DocumentWrite extends BaseController {

	private static final long serialVersionUID = 5489088066652179270L;

	WebHelper web;
	BBSCommon bbs;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		web = WebHelper.getInstance(request, response);
		bbs = BBSCommon.getInstance();
		
		/** 게시판 카테고리 값을 받아서 view에 전달 */
		String category = web.getString("category");
		request.setAttribute("category", category);

		String bbsName;

		/** 존재하는 게시판인지 판별 */
		try {
			bbsName = bbs.getBbsName(category);
			request.setAttribute("bbsName", bbsName);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		return "bbs/document_write";
	}

}
