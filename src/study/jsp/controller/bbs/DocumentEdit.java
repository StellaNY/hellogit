package study.jsp.controller.bbs;

import java.io.IOException;
import java.util.List;

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
import study.jsp.mysite.model.BbsFile;
import study.jsp.mysite.service.BbsDocumentService;
import study.jsp.mysite.service.BbsFileService;
import study.jsp.mysite.service.impl.BbsDocumentServiceImpl;
import study.jsp.mysite.service.impl.BbsFileServiceImpl;

/**
 * @author "StellaNY (stella10137@gmail.com)"
 * @since 2019. 03. 27.
 */
@WebServlet("/bbs/document_edit.do")
public class DocumentEdit extends BaseController {

	private static final long serialVersionUID = 5489088066652179270L;

	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	BBSCommon bbs;
	BbsDocumentService bbsDocuService;
	BbsFileService bbsFileService;


	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		bbs = BBSCommon.getInstance();
		bbsDocuService = new BbsDocumentServiceImpl(log, sqlSession);
		bbsFileService = new BbsFileServiceImpl(log, sqlSession);

		/** 게시판 카테고리 값을 받아서 view에 전달 */
		String category = web.getString("category");
		request.setAttribute("category", category);

		String bbsName;

		/** 존재하는 게시판인지 판별 */
		try {
			bbsName = bbs.getBbsName(category);
			request.setAttribute("bbsName", bbsName);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}
		/** 글 번호 파라미터 받기 */
		int documentId = web.getInt("document_id");
		log.debug("documentId = " + documentId);

		if (documentId == 0) {
			sqlSession.close();
			web.redirect(null, "글 번호가 지정되지 않았습니다.");
			return null;
		}
		
		BbsDocument document = new BbsDocument();
		document.setId(documentId);
		document.setCategory(category);
		
		BbsFile file = new BbsFile();
		file.setBbsDocumentId(documentId);
		
		/** DB 조회 */
		// 지금 읽고 있는 게시물이 저장될 객체
		BbsDocument readDocument = null;
		// 첨부파일 정보가 저장될 객체
		List<BbsFile> fileList = null;
		
		
		
		try {
			readDocument = bbsDocuService.selectDocument(document);
			fileList = bbsFileService.selectFileList(file);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		} finally {
			sqlSession.close();
		}

		/** 읽어온 데이터를 View에 전달*/
		request.setAttribute("readDocument", readDocument);
		request.setAttribute("fileList", fileList);

		return "bbs/document_edit";
	}

}
