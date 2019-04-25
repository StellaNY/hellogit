<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
	<%@ include file = "/WEB-INF/views/inc/head.jsp" %>
	<style>
		/** 메인페이지 > 최근게시물 > 제목 영역 */
		.article-item .page-header {
			margin-bottom: 0px;
			border-bottom: 0px;
		}
		
		/** 메인페이지 > 최근게시물 > 제목 영역 > 제목 텍스트 */
		.article-item h4 {
			font-weight: bold;
			margin-bottom: 0;
		}
		
		/** 메인페이지 > 최근게시물 > 제목 우측의 more 버튼 */
		.article-item .btn {
			margin-bottom: -15px;
		}
		
		/** 메인페이지 > 최근게시물 > 글 목록 > 링크 */
		.article-item .list-group-item a {
			display: block;
			color: #222;
			white-space: nowrap;
			overflow: hidden;
			text-overflow: ellipsis;
		}
		
		/** 케러셀 영역의 전체 높이 지정 */
		#carousel {
			height: 80%;
		}
		
		/** 핸드폰 사이즈에서는 케러셀이 화면을 가득 채움 */
		@media (max-width: 767px) {
			#carousel {
				height: 100%;
			}
		}
		
		/** 케러셀의 각 영역 높이가 케러셀 안에서 가득 차도록 구성 */
		#carousel .item, #carousel .carousel-inner {
			height: 100%;
		}
		
		/** 케러셀 이미지 */
		#carousel .img {
			height: 100%;
			background-size: cover;
			background-position: center center;
		}
		
	</style>
</head>
<body>

	<%@ include file = "/WEB-INF/views/inc/topbar.jsp" %>
	<div class="container">
		<h1>${bbsName } <small>글 삭제 하기</small></h1>
		
        <form action="${pageContext.request.contextPath}/bbs/document_del_ok.do" method="POST" class="form-horizontal">
            <input type="hidden" name="category" id="category" class="form-control" value="${category}">
            <input type="hidden" name="document_id" id="document_id" class="form-control" value="${documentId}">
            <div style="width: 300px; margin:50px auto;">
				<c:choose>
					<c:when test="${myDocument == true }">
						<h4>정말 이 게시물을 삭제하시겠습니까?</h4>
								
						<!-- 버튼틀 -->
				    	<div class="form-group">
							<button type="submit" class="btn btn-danger btn-block">삭제하기</button>
							<button type="button" class="btn btn-primary btn-block" onclick="history.back();">삭제취소</button>
						</div>
					</c:when>
					<c:otherwise>
						<c:choose>
							<c:when test="${loginInfo.id != null }">
								<h4>글 작성자만 삭제 가능합니다.</h4>
								
								<!-- 버튼틀 -->
						    	<div class="form-group">
									<button type="button" class="btn btn-warning btn-block" onclick="history.back();">이전페이지</button>
									<a href="${pageContext.request.contextPath}/index.do" class="btn btn-danger btn-block" >메인으로</a>
								</div>
							</c:when>
							<c:otherwise>
								<h4>글 작성시 설정한 비밀번호를 입력해야 합니다.</h4>
								<div class="form-group">
									<label for="writer_pw">비밀번호</label>
									<input type="password" name="writer_pw" id="writer_pw" class="form-control">
								</div>
								
								<!-- 버튼틀 -->
						    	<div class="form-group">
									<button type="submit" class="btn btn-danger btn-block">삭제하기</button>
									<button type="button" class="btn btn-primary btn-block" onclick="history.back();">삭제취소</button>
								</div>
							</c:otherwise>
						</c:choose>
					</c:otherwise>
				</c:choose>
				
			</div>
		</form>               
	</div>
	
	<%@ include file = "/WEB-INF/views/inc/footer.jsp" %>	
</body>
</html>