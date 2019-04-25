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
    <!-- 케러셀 -->
    
    <div id="carousel" class="carousel slide" data-ride="carousel">
        <!-- indicators-->
        <ol class="carousel-indicators">
        	<c:forEach var="document" items="${galleryList }" varStatus="s">
        		<c:set var = "cls" value="" />
        		<c:if test="${s.index ==0 }">
        			<c:set var="cls" value="active" />
        		</c:if>
            	<li data-target="#carousel" data-slide-to="${s.index}" class="${cls}"></li>
        	</c:forEach>
        </ol>
        <!--Wrapper for slides-->
        <div class="carousel-inner">
        	<c:forEach var="document" items="${galleryList }" varStatus="s">
        		<c:set var = "cls" value="" />
        		<c:if test="${s.index ==0 }">
        			<c:set var="cls" value="active" />
        		</c:if>
        		<c:url var="image_url" value = "/download.do">
        			<c:param name="file" value="${document.imagePath}" />
        		</c:url>
        		
	            <div class="item ${cls}">
	            	<div class="img" style="background-image:url('${image_url}')"></div>
	                <div class="carousel-caption">
	                	<h2>${document.subject}</h2>
						<p><a class="btn btn-lg btn-primary" href="${pageContext.request.contextPath}/bbs/document_read.do?category=gallery&document_id=${document.id}">view</a></p>
	                </div>
	            </div>
        	</c:forEach>
        </div>

        <!-- controls -->
        <a class="left carousel-control" href="#carousel" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left"></span>
        </a>
        <a class="right carousel-control" href="#carousel" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right"></span>
        </a>
    </div>
    

	<!--  최신 게시물 목록 영역 -->
	<div class="container">
		<div class="row">
			<!-- 공지사항 -->
			<div class="col-md-4 article-item">
				<div class="page-header clearfix">
					<h4 class="pull-left">공지사항</h4>
					<div class="pull-right">
						<a class="btn btn-warning btn-xs" href="${pageContext.request.contextPath}/bbs/document_list.do?category=notice">more</a>
					</div>
				</div>
				<ul class="list-group">
        			<c:forEach var="document" items="${noticeList}" varStatus="s">
						<li class="list-group-item">
							<a href="${pageContext.request.contextPath}/bbs/document_read.do?category=${document.category}&document_id=${document.id}">${document.subject }</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			<!-- 자유게시판 -->
			<div class="col-md-4 article-item">
				<div class="page-header clearfix">
					<h4 class="pull-left">자유게시판</h4>
					<div class="pull-right">
						<a class="btn btn-warning btn-xs" href="${pageContext.request.contextPath}/bbs/document_list.do?category=free">more</a>
					</div>
				</div>
				<ul class="list-group">
        			<c:forEach var="document" items="${freeList}" varStatus="s">
						<li class="list-group-item">
							<a href="${pageContext.request.contextPath}/bbs/document_read.do?category=${document.category}&document_id=${document.id}">${document.subject }</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			
			
			<!-- 질문답변 -->
			<div class="col-md-4 article-item">
				<div class="page-header clearfix">
					<h4 class="pull-left">공지사항</h4>
					<div class="pull-right">
						<a class="btn btn-warning btn-xs" href="${pageContext.request.contextPath}/bbs/document_list.do?category=qna">more</a>
					</div>
				</div>
				<ul class="list-group">
        			<c:forEach var="document" items="${qnaList}" varStatus="s">
						<li class="list-group-item">
							<a href="${pageContext.request.contextPath}/bbs/document_read.do?category=${document.category}&document_id=${document.id}">${document.subject }</a>
						</li>
					</c:forEach>
				</ul>
			</div>
			
		</div>
	</div>
	
	<%@ include file = "/WEB-INF/views/inc/footer.jsp" %>	
</body>
</html>