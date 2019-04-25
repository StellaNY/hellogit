package study.jsp.controller.bbs;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import study.jsp.helper.BaseController;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.BbsComment;
import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.BbsCommentService;
import study.jsp.mysite.service.impl.BbsCommentServiceImpl;

/**
 * 
 * <pre>
 * @Project : mysite
 * @PackageNm : study.jsp.controller.bbs
 * </pre>
 * 
 * @FileName : CommentDeleteOk.java
 * @author : "StellaNY (stella10137@gmail.com)"
 * @Description : 덧글 삭제 페이지 액션 Controller
 * @Date : 2019-04-04
 *
 */
@WebServlet("/bbs/comment_del_ok.do")
public class CommentDeleteOk extends BaseController {

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
		
		/** 파라미터 받기 */
		int commentId = web.getInt("comment_id");
		String writerPw = web.getString("writer_pw");
		
		// 이곳에서 에러가 났담.
		//javascript 구분을 쓰면 json이 파싱을 못한다.
		// json에선 무조오오오오건 web.printJsonRt 사용하기!
		if(commentId == 0) {
			sqlSession.close();
			web.printJsonRt("덧글 번호가 없습니다.");
			return null;
		}
		
		BbsComment comment = new BbsComment();
		comment.setId(commentId);
		comment.setWriterPw(writerPw);
		
		/**로그인 중이라면 회원일련번호를 넣기 */
		Member loginInfo = (Member) web.getSession("loginInfo");
		if(loginInfo != null) {
			comment.setMemberId(loginInfo.getId());
		}
		
		try {
			// Bens에 추가된 자신의 회원번호를 사용하여 자신의 덧글임을 판별
			// --> 자신의 덧글이 아니라면 비멀번호 검사
			if(bbsComService.selectCommentCntByMemberId(comment) < 1) {
				bbsComService.selectCommentCntByPw(comment);
			}
			bbsComService.deleteComment(comment);	// 덧글 삭제
		} catch (Exception e) {	
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		} finally {
			sqlSession.close();
		}

		// JSON 형식 만들기
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("commentId", commentId);
		
		// 처리 결과 JSON으로 출력하기
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
		
		//JSON 형식일땐 return null; 처리 
		return null;
	}
	

}
