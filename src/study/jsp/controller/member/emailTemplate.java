package study.jsp.controller.member;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Servlet implementation class emailTemplate
 */
@WebServlet("/emailTemplate")
public class emailTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Logger log = null;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public emailTemplate() {
        super();
        // 실행되는 주체를 확인하기 
        String className = this.getClass().getName();
        System.out.println("[RUN] >>> "+className);
        
        log = LogManager.getLogger(className);
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/// 공통 처리 메서드로 제어를 이동시킨다.
		this.pageInit(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	/**
	 * Get, Post 방식에 상관없이 공통 처리되는 메서드
	 * @param request		- JSP request 내장 객체
	 * @param response		- JSP response 내장 객체
	 */
	private void pageInit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String classNm = this.getClass().getSimpleName();
		log.debug(classNm+" 클래스의 doRun 메서드 실행됨");
		
		// 페이지 형식 지정하기
		response.setContentType("text/html; charset=utf-8");
		// 파라미터 처리 형식 지정하기
		request.setCharacterEncoding("utf-8");
		
		// 현재 URL을 획득해서 로그에 출력
		String url = request.getRequestURI().toString();
		if(request.getQueryString() != null) {
			url = url +"?" + request.getQueryString();
		}
		
		// Method(GET,POST) 확인
		String methodNm = request.getMethod();
		log.info("["+methodNm+"] >>> " + url);
		
		// view 이름
		String view = "/templates/emailTemplate";
		
		//획득한 View가 존재한다면 화면 표시
		if(view != null) {
			//View를 생성한다.
			view = "/WEB-INF/views/"+view+".html";
			log.info("[VIEW] >>> " + view);
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
	}
	

}
