package study.jsp.controller.bbs;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
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
import study.jsp.helper.PageHelper;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.BbsComment;
import study.jsp.mysite.service.BbsCommentService;
import study.jsp.mysite.service.impl.BbsCommentServiceImpl;

/**
 * Servlet implementation class CommentList
 */
@WebServlet("/bbs/comment_list.do")
public class CommentList extends BaseController {

	private static final long serialVersionUID = -8862277678120009135L;
	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	PageHelper pageHelper;
	BbsCommentService BbsComService;
	
	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이지 형식을 JSON으로!
		response.setContentType("application/json");
		
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		pageHelper = PageHelper.getInstance();
		BbsComService = new BbsCommentServiceImpl(log, sqlSession);

		int bbsDocumentId = web.getInt("document_id");
		log.debug("bbsDocumentId = " + bbsDocumentId);

		if(bbsDocumentId == 0) {
			sqlSession.close();
			web.printJsonRt("게시물 일련번호가 없습니다.");
			return null;
		}

		// 현재 페이지 수 --> 기본값은 1페이지로 설정
		int page = web.getInt("page", 1);
		log.debug("page = " + page);

		/** 조회할 정보에 대한 BEANS 생성 */
		BbsComment comment = new BbsComment();
		comment.setBbsDocumentId(bbsDocumentId);

		int totalCnt = 0;
		List<BbsComment> item = null;
		try {
			// 전체 게시물 수
			totalCnt = BbsComService.selectCommentCnt(comment);
			//페이징 처리하기
			pageHelper.pageProcess(page, totalCnt, 10, 5);
			comment.setLimitStart(pageHelper.getLimitStart());
			comment.setListCnt(pageHelper.getListCnt());
			
			item = BbsComService.selectCommentList(comment);
		} catch (Exception e) {
			log.debug(e.getLocalizedMessage());
			web.printJsonRt(e.getLocalizedMessage());
			return null;
		} finally {
			sqlSession.close();
		}
		
		// 특수문자 처리
		for ( int i=0; i<item.size(); i++) {
			BbsComment temp = item.get(i);
			temp.setWriterName(web.convertHtmlTag(temp.getWriterName()));
			temp.setEmail(web.convertHtmlTag(temp.getEmail()));
			temp.setContent(web.convertHtmlTag(temp.getContent()));
		}
		// JSON 형식 만들기
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("rt", "OK");
		data.put("item", item);
		data.put("pageHelper", pageHelper);
		
		// 처리 결과 JSON으로 출력하기
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(response.getWriter(), data);
		
		return null;
	}
	

}
