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
import study.jsp.helper.PageHelper;
import study.jsp.helper.UploadHelper;
import study.jsp.helper.WebHelper;
import study.jsp.mysite.dao.MyBatisConnectionFactory;
import study.jsp.mysite.model.BbsDocument;
import study.jsp.mysite.service.BbsDocumentService;
import study.jsp.mysite.service.impl.BbsDocumentServiceImpl;

/**
 * @author "StellaNY (stella10137@gmail.com)"
 * @since 2019. 03. 28.
 */
@WebServlet("/bbs/document_list.do")
public class DocumentList extends BaseController {

	private static final long serialVersionUID = 5489088066652179270L;

	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	BBSCommon bbs;
	UploadHelper upload;
	PageHelper pageHelper;
	BbsDocumentService bbsDocuService;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		bbs = BBSCommon.getInstance();
		pageHelper = PageHelper.getInstance();
		upload = UploadHelper.getInstance();
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

		/** 검색어 가져오기 */
		String keyword = web.getString("keyword");
		log.debug("keyword = " + keyword);

		/** 조회할 정보에 대한 BEANS 생성 */
		BbsDocument document = new BbsDocument();
		document.setCategory(category);

		// 현재 페이지 수 --> 기본값은 1페이지로 설정
		int page = web.getInt("page", 1);
		log.debug("page = " + page);

		// 검색어 조회를 위한 Beans 넣기
		document.setSubject(keyword);
		document.setContent(keyword);

		/** 게시글 목록 조회 */
		int totalCnt = 0;
		List<BbsDocument> documentList = null;

		// 게시판 종류가 갤러리인 경우 사진목록을 함께 조회함
		document.setGallery(category.equals("gallery"));

		try {
			// 전체 게시물 수
			totalCnt = bbsDocuService.selectDocumentCnt(document);

			pageHelper.pageProcess(page, totalCnt, 12, 5);
			document.setLimitStart(pageHelper.getLimitStart());
			document.setListCnt(pageHelper.getListCnt());

			documentList = bbsDocuService.selectDocumentList(document);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
//			return null;
		} finally {
			sqlSession.close();
		}
		// 조회 결과가 존재할경우 --> 갤러리라면 이미지 경로를 썸네일로 교체
		if (document.isGallery() && documentList != null) {
			for (int i = 0; i<documentList.size(); i++) {
				BbsDocument item = documentList.get(i);
				String imagePath = item.getImagePath();
				if (imagePath != null) {
					String thumbPath = upload.createThumbnail(imagePath, 480, 320, true);
					item.setImagePath(thumbPath);
					log.debug("thumbnail create >> " + item.getImagePath());
				}

			}
		}

		/** 조회 결과를 View에 전달 */
		request.setAttribute("documentList", documentList);
		request.setAttribute("keyword", keyword);
		request.setAttribute("pageHelper", pageHelper);
		
		// 갤러리 목록인지 일반 게시판 목록인지 분기
		String view = "bbs/document_list";
		if(document.isGallery()) {
			view = "bbs/gallery_list";
		}
		
		return view;
	}

}
