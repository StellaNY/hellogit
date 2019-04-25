package study.jsp.helper;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 추상화 클래스로 만들어서 doRun을 실행시켜 view페이지를 만들자
 * WebServlet 어노테이션을 사용하려면 아래를 import를 시켜야됨.
 * import javax.servlet.annotation.WebServlet;
 * 
 * 하지만 추상화 클래스로 만들었기 때문에 필요없음.
 */
//@WebServlet("/BaseController")
public abstract class BaseController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Logger log = null;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BaseController() {
        super();
        // 실행되는 주체를 확인하기 
        String className = this.getClass().getName();
        System.out.println("[RUN] >>> "+className);
        
        log = LogManager.getLogger(className);
    }

	/**
	 * Get 방식 요청이 들어오면 실행된다.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공통 처리 메서드로 제어를 이동시킨다.
		this.pageInit(request, response);
	}

	/**
	 * Post 방식 요청이 들어오면 실행된다.
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//this.pageInit(request, response);
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
		String view = doRun(request, response);
		
		//획득한 View가 존재한다면 화면 표시
		if(view != null) {
			//View를 생성한다.
			view = "/WEB-INF/views/"+view+".jsp";
			log.info("[VIEW] >>> " + view);
			RequestDispatcher dispatcher = request.getRequestDispatcher(view);
			dispatcher.forward(request, response);
		}
	}
	
	/**
	 *  웹페이지 구성에 필요한 처리를 수행한 후, view의 이름을 리턴한다.
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public abstract String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
	
}
