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
		<h1>${bbsName } - <small>수정 하기</small></h1>
		<!--  글쓰기 시작 -->
        <form action="${pageContext.request.contextPath}/bbs/document_edit_ok.do" method="POST" class="form-horizontal"
            enctype="multipart/form-data">
            
            <input type="hidden" name="category" id="category" value="${category}" />
            <!-- 수정 대상에 대한 상태 유지 -->
            <input type="hidden" name="document_id" id="document_id" value="${readDocument.id}" />
			<!-- 작성자, 비밀번호, 이메일은 자신의 글을 수정하는 경우만 생략 한다. -->
			<c:if test="${loginInfo == null || loginInfo.id != readDocument.memberId }">
			<!-- 작성자 -->
		    	<div class="form-group">
		    		<label for="writer_name" class="col-sm-2 control-label">작성자</label>
		    		<div class="col-sm-10">
						<input type="text" name="writer_name" id="writer_name" class="form-control" value="${readDocument.writerName}">
					</div>
				</div>
				<!-- 비밀번호 -->
		    	<div class="form-group">
		    		<label for="writer_pw" class="col-sm-2 control-label">비밀번호</label>
		    		<div class="col-sm-10">
						<input type="password" name="writer_pw" id="writer_pw" class="form-control" placeholder="글 작성시 설정하신 비밀번호를 입력하세요.">
					</div>
				</div>
				<!-- 이메일 -->
		    	<div class="form-group">
		    		<label for="writer_email" class="col-sm-2 control-label">이메일</label>
		    		<div class="col-sm-10">
						<input type="email" name="writer_email" id="writer_email" class="form-control"  value="${readDocument.email}">
					</div>
				</div>
			</c:if>
			<!-- 비로그인시 끝 -->
			
			<!-- 제목 -->
	    	<div class="form-group">
	    		<label for="subject" class="col-sm-2 control-label">제목</label>
	    		<div class="col-sm-10">
					<input type="text" name="subject" id="subject" class="form-control"  value="${readDocument.subject}">
				</div>
			</div>
			
			<!-- 내용 -->
	    	<div class="form-group">
	    		<label for="content" class="col-sm-2 control-label">내용</label>
	    		<div class="col-sm-10">
					<textarea name="content" id="content" class="ckeditor" style="resize:none;">${readDocument.content }</textarea>
				</div>
			</div>
			
			<!-- 파일첨부 -->
	    	<div class="form-group">
	    		<label for="file" class="col-sm-2 control-label">파일첨부</label>
	    		<div class="col-sm-10">
					<input type="file" name="file" id="file" class="form-control" multiple="multiple" />
					<!-- 첨부파일 리스트가 존재할 경우 삭제할 항목 선택 가능 -->
					<c:if test="${fileList != null }">
						<c:forEach var="file" items="${fileList}">
							<c:url var="downloadUrl" value="/download.do">
								<c:param name="file" value="${file.fileDir}/${file.fileName}" />
							</c:url>
							<div class="checkbox">
								<label>
									<input type="checkbox" name="del_file" id="img_del"value="${file.id }" />
									${file.originName } [삭제하기]
									<a href="${downloadUrl}"> [다운받기]</a>
								</label>
							</div>
						</c:forEach>
					</c:if>
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