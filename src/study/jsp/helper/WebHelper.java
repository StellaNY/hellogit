package study.jsp.helper;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

public class WebHelper {

	/* 싱글톤 객체 */
	private static WebHelper current = null;

	public static WebHelper getInstance(HttpServletRequest request, HttpServletResponse response) {
		if (current == null) {
			current = new WebHelper();
		}
		current.init(request, response); // 초기화 메서드 호출
		return current;
	}

	private static void freeInstance() {
		current = null;
	}

	private WebHelper() {
		super();
	}

	/** 쿠키에서 사용할 도메인 */
	private static final String DOMAIN = "localhost";

	/** JSP의 request 내장 객체 */
	// --> import javax.servlet.http.HttpServletRequest;
	private HttpServletRequest request;
	/** JSP의 response 내장 객체 */
	// --> import javax.servlet.http.HttpServletResponse;
	private HttpServletResponse response;
	/** JSP의 out 내장 객체 */
	// --> import java.io.PrintWriter;
	private PrintWriter out;

	/** JSP의 session 내장 객체 */
	// --> import javax.servlet.http.HttpSession;
	private HttpSession session;

	/**
	 * 초기화 메서드
	 * 
	 * @param request
	 */
	public void init(HttpServletRequest request, HttpServletResponse response) {

		this.request = request;
		this.response = response;

		// 세션 객체 생성하기
		this.session = request.getSession();

		// 페이지 이동없이 세션이 유지되는 시간 설정 (초)
		// --> 24시간
		this.session.setMaxInactiveInterval(60 * 60 * 24);

		/** 내장객체 초기화 -> utf-8 설정 */
		try {
			this.request.setCharacterEncoding("utf-8");
			this.response.setCharacterEncoding("utf-8");
			// out 객체 생성
			this.out = response.getWriter();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 파라미터를 전달받아서 리턴한다.
	 * 
	 * @param fieldName  - 파라미터 이름
	 * @param defaultVal - 값이 없을 경우 사용될 기본값
	 * @return String
	 */
	public String getString(String fieldName, String defaultVal) {
		// 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정
		String result = defaultVal;

		// Get, Post 파라미터를 받는다.
		String param = this.request.getParameter(fieldName);

		// 값이 공백이 아닐때
		if (param != null) {
			param = param.trim();
			if (!param.equals("")) {
				result = param;
			}
		}
		// 값을 리턴. param값이 존재하지 않을 경우 default값을 리턴
		return result;

	}

	/**
	 * 파라미터를 전달받아서 리턴한다.
	 * 
	 * @param fieldName  - 파라미터 이름
	 * @param defaultVal - 값이 없을 경우 사용될 기본값
	 * @return int
	 */
	public int getInt(String fieldName, int defaultVal) {
		// 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정
		int result = defaultVal;

		// Get, Post 파라미터를 받는다.
		String param = this.getString(fieldName, null);

		// 파라미터값을 숫자로 형변환
		try {
			result = Integer.parseInt(param);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		// 값을 리턴. param값이 존재하지 않을 경우 default값을 리턴
		return result;

	}

	/**
	 * 배열형태의 파라미터를 전달받아서 리턴한다. (체크박스 등 다중값)
	 * 
	 * @param fieldName  - 파라미터 이름
	 * @param defaultVal - 값이 없을 경우, 길이가 0일 경우 사용될 기본값
	 * @return String[]
	 */
	public String[] getStringArray(String fieldName, String[] defaultVal) {
		// 리턴을 위한 값을 두 번째 파라미터(기본값)로 설정
		String[] result = defaultVal;

		// Get, Post 파라미터를 받는다.
		String[] param = this.request.getParameterValues(fieldName);

		// 파라미터가 존재하며 길이가 0이상이면 값이 있당!
		if (param != null) {
			if (param.length > 0) {
				result = param;
			}
		}

		// 값을 리턴. param값이 존재하지 않을 경우 default값을 리턴
		return result;

	}

	/**
	 * getString()의 기본값을 null로 처리하도록 메서드 오버로드
	 * 
	 * @param fieldName - 파라미터 이름
	 * @return String
	 */
	public String getString(String fieldName) {
		return this.getString(fieldName, null);
	}

	/**
	 * getInt()의 기본값을 0으로 처리하도록 메서드 오버로드
	 * 
	 * @param fieldName - 파라미터 이름
	 * @return int
	 */
	public int getInt(String fieldName) {
		return this.getInt(fieldName, 0);
	}

	/**
	 * getStringArray()의 기본값을 null로 처리하도록 메서드 오버로드
	 * 
	 * @param fieldName - 파라미터 이름
	 * @return String[]
	 */
	public String[] getStringArray(String fieldName) {
		return this.getStringArray(fieldName, null);
	}

	/**
	 * 메시지 표시 후, 페이지를 지정된 곳으로 이동한다.
	 * <p>
	 * HowToUse:
	 * </p>
	 * 
	 * <pre>
	 * // 메시지 표시 후, 특정페이지로 이동하기	
	 * web.redirect("이동할 URL", "메시지 내용");
	 * // 메시지 표시 없이, 특정페이지로 이동하기	
	 * web.redirect("이동할 URL", null);
	 * // 메시지 표시 후, 이전페이지로 이동하기	
	 * web.redirect(null, "메시지 내용");
	 * // 메시지 표시 없이, 이전페이지로 이동하기	
	 * web.redirect(null, null);
	 * </pre>
	 * 
	 * @param url - 이동할 페이지의 URL, null일 경우 이전페이지로 이동
	 * @param msg - 화면에 표시할 메시지. null일 경우 표시 안함
	 */
	public void redirect(String url, String msg) {
		// 가상의 view로 만들기 위한 HTML 태그 구성
		String html = "<!doctype html>";
		html += "<html>";
		html += "<head>";
		html += "<meta charset='utf-8' />";

		// 메시지 표시
		if (msg != null) {
			html += "<script type='text/javascript'>alert('" + msg + "');</script>";
		}
		// 페이지 이동
		if (url != null) {
			html += "<meta http-equiv='refresh' content='0; url=" + url + "' />";
		} else {
			html += "<script type='text/javascript'>history.back();</script>";

		}

		html += "</head>";
		html += "<body></body>";
		html += "</html>";
		// HTML 출력
		out.print(html);

	}

	// ------------------쿠키 관련 메소드 시작------------------
	/**
	 * 쿠키값을 저장한다.
	 * 
	 * @param key     - 쿠키이름
	 * @param value   - 값
	 * @param timeout - 설정시간(초). 브라우저를 닫으면 즉시 삭제 : -1
	 */
	public void setCookie(String key, String value, int timeout) {
		/** 전달된 값을 URLEncoding 처리 한다. */
		if (value != null) {
			try {
				value = URLEncoder.encode(value, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		/** 쿠키 객체 생성 및 기본 설정 */
		Cookie cookie = new Cookie(key, value);
		cookie.setPath("/");
		cookie.setDomain(DOMAIN);

		/** 유효시간 설정 */
		// 시간값이 0보다 작은 경우 이 메서드를 설정하지 않는다.
		// 0으로 설정할 경우 즉시 삭제 된다.
		if (timeout > -1) {
			cookie.setMaxAge(timeout);
		}

		/** 쿠키 저장하기 */
		this.response.addCookie(cookie);

	}

	/**
	 * 쿠키값을 조회한다.
	 * 
	 * @param key          - 쿠키 이름
	 * @param defaultValue - 값이 없을 경우 사용될 기본값
	 * @return String
	 */
	public String getCookie(String key, String defaultValue) {
		/** 리턴할 값을 설정 */
		String result = defaultValue;

		/** 쿠키 배열 가져오기 */
		// --> import javax.servlet.http.Cookie;
		Cookie[] cookies = this.request.getCookies();

		/**
		 * 쿠키가 있다면 추출된 배열의 항목 수 만큼 반복하여 원하는 이름값을 검색
		 */
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String cookieNm = cookie.getName();
				if (cookieNm.equals(key)) {
					result = cookie.getValue();

					try {
						result = URLDecoder.decode(result, "UTF-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
					break;
				} // end if
			} // end foreach
		} // end if

		return result;
	}

	/**
	 * 쿠키값을 조회한다. 값이 없을 경우 Null을 리턴한다.
	 * 
	 * @param key - 쿠키이름
	 * @return
	 */
	public String getCookie(String key) {
		return this.getCookie(key, null);
	}

	/**
	 * 지정된 키에 대한 쿠키를 삭제한다.
	 * 
	 * @param key
	 */
	public void removeCookie(String key) {
		this.setCookie(key, null, 0);
	}
	// -------------------쿠키 관련 메소드 끝-------------------

	// ------------------세션 관련 메소드 시작------------------

	/**
	 * 세션값을 저장한다.
	 * 
	 * @param key   -세션이름
	 * @param value -저장할 데이터
	 */
	public void setSession(String key, Object value) {
		this.session.setAttribute(key, value);
	}

	/**
	 * 세션값을 조회한다.
	 * 
	 * @param key          - 조회할 세션이름
	 * @param defaultValue - 값이 없을 경우 대체할 기본값
	 * @return Object이므로 명시적 형변호나이 필요함.
	 */
	public Object getSession(String key, Object defaultValue) {
		Object value = this.session.getAttribute(key);

		if (value == null) {
			value = defaultValue;
		}

		return value;
	}

	/**
	 * 세션값을 조회한다. 값이 없을 경우에 대한 기본값을 null로 설정
	 * 
	 * @param key - 조회할 세션이름
	 * @return Object이므로 명시적 형변호나이 필요함.
	 */
	public Object getSession(String key) {
		return this.getSession(key, null);
	}

	/**
	 * 특정 세션값을 삭제한다.
	 * 
	 * @param key - 삭제할 세션이름
	 */
	public void removeSession(String key) {
		this.session.removeAttribute(key);

	}

	/**
	 * 현재 사용자에 대한 모든 세션값을 일괄 삭제한다.
	 */
	public void removeAllSession() {
		this.session.invalidate();
	}

	// -------------------세션 관련 메소드 끝-------------------

	/**
	 * 현재 프로젝트의 최상위 경로값을 "/프로젝트명" 형식으로 리턴한다.
	 * 
	 * @return - String : /프로젝트명
	 */
	public String getRootPath() {
		return this.request.getContextPath();

	}

	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 
	 * 현재 접속자에 대한 IP주소를 조회하여 리턴한다.<br/>
	 * windows7부터는 IPv6주소를 가져온다.<br/>
	 * localhost  경우 0:0:0:0:0:0:0:1 로 출력이 된다.<br/>
	 * IPv4로 출력을 원하는 경우 WAS세팅 수정<br/>
	 * '-Djava.net.preferIPv4Stack=true' 추가
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-03-29
	 * 
	 * @Method Name : getClientIP
	 * @return String - IPv6주소
	 */
	public String getClientIP() {
		String ip = request.getHeader("X-FORWARDED-FOR");

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	/**
	 * 
	 * <pre>
	 * 메소드 정의 : 결과 메시지를 JSON으로 출력한다.
	 * JSONAPI에서 web.redirect() 기능을 대체할 용도.
	 * </pre>
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @Date 2019-04-02
	 * 
	 * @Method Name : printJsonRt
	 * @param rt : JSON에 포함할 메시지 내용
	 */
	public void printJsonRt(String rt) {
		Map<String, String> data = new HashMap<String, String>();
		data.put("rt", rt);

		ObjectMapper mapper = new ObjectMapper();
		try {
			mapper.writeValue(response.getWriter(), data);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String convertHtmlTag(String content) {
		// 변경 결과를 저장할 객체
		StringBuilder builder = new StringBuilder();

		// 문자열에 포함된 한 글자
		char charBuff;

		// 글자 수 만큼 반복한다.
		for (int i = 0; i < content.length(); i++) {
			// 한글자를 추출
			charBuff = (char) content.charAt(i);

			// 특수문자 형태에 부합할 경우 변환하여 builder에 추가
			// 그렇지 않는 경우 원본 그대로 builder에 추가
			switch (charBuff) {
			case '<':
				builder.append("&lt;");
				break;
			case '>':
				builder.append("&gt;");
				break;
			case '&':
				builder.append("&amp;");
				break;
			case '\n':
				builder.append("&lt;br /&gt;");
				break;
			default:
				builder.append(charBuff);
			}

		}
		// 조립된 결과를 문자열로 변환해서 리턴한다.
		return builder.toString();
	}
}
