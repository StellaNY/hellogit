<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>
<head>
	<%@ include file = "/WEB-INF/views/inc/head.jsp" %>
</head>
<body>
	<%@ include file = "/WEB-INF/views/inc/topbar.jsp" %>
	<div class="container">
		<h1>${bbsName } - <small>새글 쓰기</small></h1>
		<!--  글쓰기 시작 -->
        <form action="${pageContext.request.contextPath}/bbs/document_write_ok.do" method="POST" class="form-horizontal"
            enctype="multipart/form-data">
            <input type="hidden" name="category" id="category" class="form-control" value="${category}">
			<!-- 비로그인시 -->
			<c:if test="${loginInfo == null }">
			<!-- 작성자 -->
		    	<div class="form-group">
		    		<label for="writer_name" class="col-sm-2 control-label">작성자</label>
		    		<div class="col-sm-10">
						<input type="text" name="writer_name" id="writer_name" class="form-control">
					</div>
				</div>
				<!-- 비밀번호 -->
		    	<div class="form-group">
		    		<label for="writer_pw" class="col-sm-2 control-label">비밀번호</label>
		    		<div class="col-sm-10">
						<input type="password" name="writer_pw" id="writer_pw" class="form-control">
					</div>
				</div>
				<!-- 이메일 -->
		    	<div class="form-group">
		    		<label for="writer_email" class="col-sm-2 control-label">이메일</label>
		    		<div class="col-sm-10">
						<input type="email" name="writer_email" id="writer_email" class="form-control">
					</div>
				</div>
			</c:if>
			<!-- 비로그인시 끝 -->
			
			<!-- 제목 -->
	    	<div class="form-group">
	    		<label for="subject" class="col-sm-2 control-label">제목</label>
	    		<div class="col-sm-10">
					<input type="text" name="subject" id="subject" class="form-control">
				</div>
			</div>
			
			<!-- 내용 -->
	    	<div class="form-group">
	    		<label for="content" class="col-sm-2 control-label">내용</label>
	    		<div class="col-sm-10">
					<textarea name="content" id="content" class="ckeditor" style="resize:none;"></textarea>
				</div>
			</div>
			
			<!-- 파일첨부 -->
	    	<div class="form-group">
	    		<label for="file" class="col-sm-2 control-label">파일첨부</label>
	    		<div class="col-sm-10">
					<input type="file" name="file" id="file" class="form-control" multiple="multiple" />
				</div>
			</div>
			
			<!-- 버튼틀 -->
	    	<div class="form-group">
	    		<div class="col-sm-offset-2 col-sm-10">
	    			<button type="submit" class="btn btn-primary">저장하기</button>
	    			<button type="button" class="btn btn-danger" onclick="history.back();">작성취소</button>
				</div>
			</div>
			
		</form>               
		<!--  글쓰기 끝 -->                                   
	</div>
	<%@ include file = "/WEB-INF/views/inc/footer.jsp" %>	
</body>
</html>