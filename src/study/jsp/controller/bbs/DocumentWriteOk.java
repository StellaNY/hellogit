package study.jsp.controller.bbs;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.ibatis.session.SqlSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import study.jsp.helper.BaseController;
import study.jsp.helper.FileInfo;
import study.jsp.helper.RegexHelper;
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
@WebServlet("/bbs/document_write_ok.do")
public class DocumentWriteOk extends BaseController {

	private static final long serialVersionUID = 5489088066652179270L;

	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	BBSCommon bbs;
	RegexHelper regex;
	UploadHelper upload;
	BbsDocumentService bbsDocuService;
	BbsFileService bbsFileService;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		bbs = BBSCommon.getInstance();
		regex = RegexHelper.getInstance();
		upload = UploadHelper.getInstance();
		bbsDocuService = new BbsDocumentServiceImpl(log, sqlSession);
		bbsFileService = new BbsFileServiceImpl(log, sqlSession);

		/** 파일이 포함된 post 파라미터 받기 */
		try {
			upload.multipartRequest(request);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, "multipart 데이터가 아닙니다.");

			log.error(e.getLocalizedMessage());
			return null;
		}

		Map<String, String> paramMap = upload.getParamMap();
		String category = paramMap.get("category");
		String writerName = paramMap.get("writer_name");
		String writerPw = paramMap.get("writer_pw");
		String email = paramMap.get("writer_email");
		String subject = paramMap.get("subject");
		String content = paramMap.get("content");
		// 작성자의 아이피 주소 가져오기
		String ipAddr = web.getClientIP();

		// 회원 일련번호 --> 비 로그인인 경우 0
		int memberId = 0;

		// 로그인한 경우 이름, 비밀번호, 이메일을 세션정보로 대체
		Member loginInfo = (Member) web.getSession("loginInfo");
		if (loginInfo != null) {
			writerName = loginInfo.getName();
			writerPw = loginInfo.getUserPw();
			email = loginInfo.getEmail();
			memberId = loginInfo.getId();
		}

		// 값 확인용
		log.debug("category = " + category);
		log.debug("writerName = " + writerName);
		log.debug("writerPw = " + writerPw);
		log.debug("email = " + email);
		log.debug("subject = " + subject);
		log.debug("content = " + content);
		log.debug("ipAddr = " + ipAddr);
		log.debug("memberId = " + memberId);

		/** 카테고리 값을 받아서 view에 전달 */
		request.setAttribute("category", category);
		String bbsName = null;
		/** 존재하는 게시판인지 판별 */
		try {
			bbsName = bbs.getBbsName(category);
			request.setAttribute("bbsName", bbsName);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		/** 입력한 값 유효성 검사 */
		// 이름 검사
		if (!regex.isValue(writerName)) {
			sqlSession.close();
			web.redirect(null, "작성자 이름을 입력하세요.");
			return null;
		}
		// 비밀번호 검사
		if (!regex.isValue(writerPw)) {
			sqlSession.close();
			web.redirect(null, "비밀번호를 입력하세요.");
			return null;
		}
		// 이메일 검사
		if (!regex.isValue(email)) {
			sqlSession.close();
			web.redirect(null, "이메일을 입력하세요.");
			return null;
		}
		if (!regex.isEmail(email)) {
			sqlSession.close();
			web.redirect(null, "이메일 형식이 잘못되었습니다.");
			return null;
		}
		// 제목 검사
		if (!regex.isValue(subject)) {
			sqlSession.close();
			web.redirect(null, "글 제목을 입력하세요.");
			return null;
		}
		// 내용 검사
		if (!regex.isValue(content)) {
			sqlSession.close();
			web.redirect(null, "글 내용을 입력하세요.");
			return null;
		}

		/** 파라미터 BEANS에 담기 */
		BbsDocument document = new BbsDocument();
		document.setCategory(category);
		document.setWriterName(writerName);
		document.setWriterPw(writerPw);
		document.setEmail(email);
		document.setSubject(web.convertHtmlTag(subject));
		document.setContent(content);
		document.setIpAddress(ipAddr);
		document.setMemberId(memberId);
		log.debug("document >>> " + document.toString());

		/** DB저장 처리 */
		try {
			bbsDocuService.insertDocument(document);
		} catch (Exception e) {
			sqlSession.close();
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}

		/** 첨부 파일 목록 처리 */
		List<FileInfo> fileList = upload.getFileList();
		try {
			for (int i = 0; i < fileList.size(); i++) {
				
				FileInfo info = fileList.get(i);
				
				//DB에 저장하기 위한 항목 생성
				BbsFile file = new BbsFile();
				
				// 몇번째 게시물에 속한 파일인지 지정한다.
				file.setBbsDocumentId(document.getId());
				
				//데이터 복사
				file.setOriginName(info.getOrginName());
				file.setFileDir(info.getFileDir());
				file.setFileName(info.getFileName());
				file.setContentType(info.getContentType());
				file.setFileSize(info.getFileSize());
				
				//저장
				bbsFileService.insertFile(file);

			}
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
			return null;
		}finally {
			sqlSession.close();
		}
		
		/** 저장 완료후 페이지 이동 */
		String url = "%s/bbs/document_read.do?category=%s&document_id=%d";
		url = String.format(url, web.getRootPath(), document.getCategory(), document.getId());
		web.redirect(url, "게시물이 등록되었습니다.");

		return null;
	}

}
