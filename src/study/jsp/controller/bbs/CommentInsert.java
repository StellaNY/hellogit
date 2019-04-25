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
import study.jsp.helper.RegexHelper;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.BbsComment;
import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.BbsCommentService;
import study.jsp.mysite.service.impl.BbsCommentServiceImpl;

/**
 * Servlet implementation class CommentInsert
 */
@WebServlet("/bbs/comment_insert.do")
public class CommentInsert extends BaseController {

	private static final long serialVersionUID = -8875690609915879495L;
	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	RegexHelper regex;
	BbsCommentService BbsComService;
	
	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 페이지 형식을 JSON으로!
		response.setContentType("application/json");
		
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		regex = RegexHelper.getInstance();
		BbsComService = new BbsCommentServiceImpl(log, sqlSession);
		
		int bbsDocumentId = web.getInt("document_id");
		String writerName = web.getString("writer_name");
		String writerPw = web.getString("writer_pw");
		String email = web.getString("email");
		String content = web.getString("content");
		// 작성자의 아이피
		String ipAddress =web.getClientIP();
		// 작성한 회원 일련번호 --> 비로그인인 경우 0 
		int memberId = 0;
		
		Member loginInfo = (Member) web.getSession("loginInfo");
		if(loginInfo != null) {
			writerName = loginInfo.getName();
			writerPw = loginInfo.getUserPw();
			email = loginInfo.getEmail();
			memberId = loginInfo.getId();
		}
		
		log.debug("bbsDocumentId = " + bbsDocumentId);
		log.debug("writerName = " + writerName);
		log.debug("writerPw = " + writerPw);
		log.debug("email = " + email);
		log.debug("content = " + content);
		log.debug("ipAddress = " + ipAddress);
		log.debug("memberId = " + memberId);
		
		if(bbsDocumentId == 0) {
			sqlSession.close();
			web.printJsonRt("게시물 일련번호가 없습니다.");
			return null;
		}
		if(!regex.isValue(writerName)) {
			sqlSession.close();
			web.printJsonRt("작성자 이름을 입력하세요.");
			return null;
		}
		if(!regex.isValue(writerPw)) {
			sqlSession.close();
			web.printJsonRt("작성자 비밀번호를 입력하세요.");
			return null;
		}
		if(!regex.isValue(email)) {
			sqlSession.close();
			web.printJsonRt("이메일을 입력하세요.");
			return null;
		}
		if(!regex.isEmail(email)) {
			sqlSession.close();
			web.printJsonRt("이메일 형식이 잘못되었습니다.");
			return null;
		}
		if(!regex.isValue(content)) {
			sqlSession.close();
			web.printJsonRt("내용을 입력하세요.");
			return null;
		}
		
		BbsComment comment = new BbsComment();
		comment.setBbsDocumentId(bbsDocumentId);
		comment.setWriterName(writerName);
		comment.setWriterPw(writerPw);
		comment.setEmail(email);
		comment.setContent(content);
		comment.setContent(content);
		comment.setIpAddress(ipAddress);
		comment.setMemberId(memberId);
		log.debug("comment >> "+ comment.toString());
		
		BbsComment item = null;
		
		try {
			BbsComService.insertComment(comment);
			item = BbsComService.selectComment(comment);
		} catch (Exception e) {
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		} finally {
			sqlSession.close();
		}
		
		// 커리 결과를 JSON으로 출력하기
		item.setWriterName(web.convertHtmlTag(item.getWriterName()));
		item.setEmail(web.convertHtmlTag(item.getEmail()));
		item.setContent(web.convertHtmlTag(item.getContent()));
		
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		
		// 처리 결과 JSON으로 출력하기
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
		
		return null;
	}
	
}
