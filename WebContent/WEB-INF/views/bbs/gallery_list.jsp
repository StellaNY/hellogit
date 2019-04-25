<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
	<%@ include file = "/WEB-INF/views/inc/head.jsp" %>
	<style>
		/* 게시물 항목 하나 */
		.item{ padding: 0 px 5px; }
		/* 게시물 제목 */
		.item h4{ margin-top: 10px; margin-bottom: 5px; }
		/* 썸네일 이미지 크기 */
		.img-responsive{ width: 100%; }
	</style>
</head>
<body>

	<%@ include file = "/WEB-INF/views/inc/topbar.jsp" %>
	<div class="container">
		<h1>${bbsName } - <small>글 목록</small></h1>
		<!--  글목록 시작 -->
		
		<div class="row multi-columns-row">
		
		<c:choose>
			<c:when test="${fn:length(documentList) > 0 }">
				<c:forEach var="docu" items="${documentList}">	
					<div class="col-xs-12 col-sm-6 col-md-3 col-lg-3">
					    <div class="thumbnail">
							<c:url var="readUrl" value="/bbs/document_read.do">
								<c:param name="category" value="${docu.category }" />
								<c:param name="document_id" value="${docu.id }" />	
							</c:url>
							<c:set var = "mark" value="<mark>${keyword}</mark>"/>
					        <a href="${readUrl}" class="thumbnail">
					        	<c:choose>
					        		<c:when test="${docu.imagePath != null}">
					        			<c:url var="downloadUrl" value="/download.do">
											<c:param name="file" value="${docu.imagePath }" />
					        			</c:url>
					            		<img src="${downloadUrl }" class="img-responsive" />
					        		</c:when>
					        		<c:otherwise>
					            		<img src="${pageContext.request.contextPath}/assets/img/no_image.png" class="img-responsive img-circle" />
					        		</c:otherwise>
					        	</c:choose>
					        </a>
					        <!-- 제목 작성자 조회수 -->
					        <div class="item">
					        	<h4>${fn:replace(docu.subject, keyword, mark)}</h4>
					        	<div>${docu.writerName}</div>
					        	<div class="clearfix">
					        		<div class="pull-left">${docu.regDate}</div>
					        		<div class="pull-right"><i class="far fa-eye"> ${docu.hit}</i> </div>
					        	</div>
					        </div>
					    </div>
					</div>
				</c:forEach>
			</c:when>
			<c:otherwise>
				<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
					<p class="text-center"> 조회된 글이 없습니다.</p>
				</div>
			</c:otherwise>
		</c:choose>		
		</div>
		<!--  글목록 끝 -->
		
		<%@ include file = "/WEB-INF/views/inc/bbs_list_bottom.jsp" %>
	</div>
	<%@ include file = "/WEB-INF/views/inc/footer.jsp" %>	
	
    <script>
		//테이블 row 전체 클릭
	    $(".clickable-row").click(function() {
	        window.location = $(this).data("href");
	    });
    </script>
</body>
</html>