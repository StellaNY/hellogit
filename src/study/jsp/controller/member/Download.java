package study.jsp.controller.member;

import java.io.IOException;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import study.jsp.helper.BaseController;
import study.jsp.helper.UploadHelper;
import study.jsp.helper.WebHelper;

@WebServlet("/download.do")
public class Download extends BaseController implements Servlet {
	private static final long serialVersionUID = 1L;

	Logger log;
	WebHelper web;
	UploadHelper upload;

	@Override
	public String doRun(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		log = LogManager.getFormatterLogger(request.getRequestURI());
		web = WebHelper.getInstance(request, response);
		upload = UploadHelper.getInstance();
		
		String filePath = web.getString("file");
		String orginName = web.getString("orgin");
		
		if(filePath != null) {
			try {
				log.debug("Create Thumbnail Image --> " + filePath);
				upload.printFileStream(response, filePath, orginName);
			} catch (Exception e) {
				log.debug(e.getLocalizedMessage());
			}
		}
		return null;
	}

}
