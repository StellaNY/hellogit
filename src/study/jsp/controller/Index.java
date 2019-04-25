package study.jsp.controller;

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
import study.jsp.mysite.service.BbsDocumentService;
import study.jsp.mysite.service.impl.BbsDocumentServiceImpl;
/**
 * 메인 주소 servlet
 * @author "StellaNY (stella10137@gmail.com)"
 *
 */
@WebServlet("/index.do")
public class Index extends BaseController{

	private static final long serialVersionUID = 9163962838963908080L;
	
	Logger log;
	SqlSession sqlSession;
	WebHelper web;
	BbsDocumentService bbsDocuService;

	
	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		sqlSession = MyBatisConnectionFactory.getSqlSession();
		web = WebHelper.getInstance(request, response);
		bbsDocuService = new BbsDocumentServiceImpl(log, sqlSession);
		
		List<BbsDocument> galleryList = null;
		List<BbsDocument> noticeList = null;
		List<BbsDocument> freeList = null;
		List<BbsDocument> qnaList = null;
		
		try {
			galleryList = this.getDocumentList("gallery", 3);
			noticeList = this.getDocumentList("notice", 5);
			freeList = this.getDocumentList("free", 5);
			qnaList = this.getDocumentList("qna", 5);
		} catch (Exception e) {
			web.redirect(null, e.getLocalizedMessage());
		}finally {
			sqlSession.close();
		}
		
		request.setAttribute("galleryList", galleryList);
		request.setAttribute("noticeList", noticeList);
		request.setAttribute("freeList", freeList);
		request.setAttribute("qnaList", qnaList);
		
		return "index";
	}
	
	/**
	 * 특정 카테고리에 대한 상위 n개의 게시물 가져오기
	 * @param category - 카테고리
	 * @param listCnt - 가져울 게시물 수
	 * @return
	 * @throws Exception
	 */
	private List<BbsDocument> getDocumentList(String category, int listCnt) throws Exception{
		List<BbsDocument> list = null;
		
		// 조회할 조건 생성하기
		// 지정된 카테고리의 0번쨰부터 listCnt개 만큼 조회
		BbsDocument docu = new BbsDocument();
		docu.setCategory(category);
		docu.setLimitStart(0);
		docu.setListCnt(listCnt);
		//갤러리인지 판별해서 가져오기
		docu.setGallery(category.equals("gallery"));
		
		list = bbsDocuService.selectDocumentList(docu);
		
		return list;
	}
	
}