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
import study.jsp.helper.UploadHelper;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.BbsDocument;
import study.jsp.mysite.model.BbsFile;
import study.jsp.mysite.model.Member;
import study.jsp.mysite.service.BbsDocumentService;
import study.jsp.mysite.service.BbsFileService;
import study.jsp.mysite.service.impl.BbsDocumentServiceImpl;
import study.jsp.mysite.service.impl.BbsFileServiceImpl;

/**
 * @author "StellaNY (stella10137@gmail.com)"
 * @since 2019. 03. 27.
 */
@WebServlet("/bbs/document_del_ok.do")
public class DocumentDeleteOk extends BaseController {

	private static final long serialVersionUID = 5489088066652179270L;

	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	BBSCommon bbs;
	UploadHelper upload;
	BbsDocumentService bbsDocuService;
	BbsFileService bbsFileService;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		bbs = BBSCommon.getInstance();
		upload = UploadHelper.getInstance();
		bbsDocuService = new BbsDocumentServiceImpl(log, sqlSession);
		bbsFileService = new BbsFileServiceImpl(log, sqlSession);
		
		/** 게시판 카테고리 값을 받아서 view에 전달 */
		String category = web.getString("category");
		request.setAttribute("category", category);
		log.debug("category = " + category);

		/** 존재하는 게시판인지 판별 */
		try {
			String bbsName = bbs.getBbsName(category);
			request.setAttribute("bbsName", bbsName);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}
		int documentId = web.getInt("document_id");
		String writerPw = web.getString("writer_pw");
		
		log.debug("documentId = "+documentId);
		log.debug("writerPw = "+writerPw);

		if(documentId == 0) {
			sqlSession.close();
			web.redirect(null, "글 번호가 없습니다.");
			return null;
		}

		BbsDocument document = new BbsDocument();
		document.setId(documentId);
		document.setCategory(category);
		document.setWriterPw(writerPw);

		BbsFile file = new BbsFile();
		file.setBbsDocumentId(documentId);
		

		// 로그인 한 경우 현재 회원의 일련번호를 추가한다. ( 비로그인 시 0 으로 설정)
		Member loginInfo = (Member) web.getSession("loginInfo");
		if(loginInfo != null) {
			document.setMemberId(loginInfo.getId());
		}
		
		List<BbsFile> fileList = null;
		
		try {
			if(bbsDocuService.selectDocumentCntByMemberId(document) < 1) {
				bbsDocuService.selectDocumentCntByPw(document);
			}
			fileList = bbsFileService.selectFileList(file);
			bbsFileService.deleteFileAll(file);
			bbsDocuService.deleteDocument(document);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}finally {
			sqlSession.close();
		}
		
		if(fileList != null) {
			for(int i = 0; i< fileList.size(); i++) {
				BbsFile f = fileList.get(i);
				String filePath = f.getFileDir() +"/"+ f.getFileName();
				upload.removeFile(filePath);
			}
		}
		

		// 갤러리 목록인지 일반 게시판 목록인지 분기
		String url = "%s/bbs/document_list.do?category=%s";
		url = String.format(url,web.getRootPath(), category);

		web.redirect(url,"삭제되었습니다.");
		
		return null;
	}

}
