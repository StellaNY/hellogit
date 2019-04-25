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
import study.jsp.mysite.model.BbsDocument;
import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.BbsDocumentService;
import study.jsp.mysite.service.impl.BbsDocumentServiceImpl;

/**
 * @author "StellaNY (stella10137@gmail.com)"
 * @since 2019. 03. 27.
 */
@WebServlet("/bbs/document_del.do")
public class DocumentDelete extends BaseController {
	
	private static final long serialVersionUID = -4115053143365235984L;

	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	BBSCommon bbs;
	BbsDocumentService bbsDocuService;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		bbs = BBSCommon.getInstance();
		bbsDocuService = new BbsDocumentServiceImpl(log, sqlSession);
		
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
		
		int documentId = web.getInt("document_id");
		if(documentId == 0) {
			sqlSession.close();
			web.redirect(null, "글 번호가 없습니다.");
			return null;
		}

		BbsDocument document = new BbsDocument();
		document.setId(documentId);
		document.setCategory(category);
		
		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. ( 비로그인 시 0 으로 설정)
		Member loginInfo = (Member) web.getSession("loginInfo");
		if(loginInfo != null) {
			document.setMemberId(loginInfo.getId());
		}
		
		int documentCnt = 0;
		try {
			documentCnt = bbsDocuService.selectDocumentCntByMemberId(document);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}finally {
			sqlSession.close();
		}
		
		boolean myDocument = documentCnt > 0;
		request.setAttribute("myDocument", myDocument);
		request.setAttribute("documentId", documentId);
		
		return "bbs/document_del";
	}

}
