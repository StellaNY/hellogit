package study.jsp.controller.bbs;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import study.jsp.helper.BaseController;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.BbsComment;
import study.jsp.mysite.service.BbsCommentService;
import study.jsp.mysite.service.impl.BbsCommentServiceImpl;

/**
 * 
 * <pre>
 * @Project : mysite
 * @PackageNm : study.jsp.controller.bbs
 * </pre>
 * 
 * @FileName : CommentEdit.java
 * @author : "StellaNY (stella10137@gmail.com)"
 * @Description : 덧글 수정 페이지 view Controller
 * @Date : 2019-04-04
 *
 */
@WebServlet("/bbs/comment_edit.do")
public class CommentEdit extends BaseController {

	private static final long serialVersionUID = -2636678104602053686L;
	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	BbsCommentService bbsComService;
	
	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이지 형식을 JSON으로!
		response.setContentType("application/json");
		
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		bbsComService = new BbsCommentServiceImpl(log, sqlSession);
		
		int commentId = web.getInt("comment_id");
		if(commentId == 0) {
			sqlSession.close();
			web.redirect(null, "덧글 번호가 없습니다.");
			return null;
		}
		
		BbsComment comment = new BbsComment();
		comment.setId(commentId);
		
		BbsComment readBbsCom = null;
				
		try {
			readBbsCom = bbsComService.selectComment(comment);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		} finally {
			sqlSession.close();
		}
		
		
		request.setAttribute("comment", readBbsCom);
		
		return "bbs/comment_edit";
	}
	

}
