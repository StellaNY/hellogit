package study.jsp.basecontroller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import study.jsp.helper.BaseController;
import study.jsp.helper.WebHelper;

@WebServlet("/hello.do")
public class Hello extends BaseController {

	/**
	 * CPU의 값을 받아 자동생성한 serialVersionUID 명시할 필요는 없지만 명시를 요구함.
	 */
	private static final long serialVersionUID = 6990340522094406362L;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/** 파라미터 받기 */
		WebHelper web = WebHelper.getInstance(request, response);

		String msg = web.getString("msg");
		request.setAttribute("msg", msg);

		String view = "hello";
		return view;
	}

}
