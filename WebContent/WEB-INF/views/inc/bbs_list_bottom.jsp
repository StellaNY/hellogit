<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!-- 검색폼 + 글쓰기 버튼 시작 -->
<div class="clearfix" style="padding: 20px 0 0 0">
    <!-- 검색폼 -->
	<div class="center-block">
		<form method='get' class="center-block" action='${pageContext.request.contextPath}/bbs/document_list.do' style="width: 300px;">
			<input type="hidden" name="category" id="category" value="${category }">
			<div class="input-group">
				<input type="text" name='keyword' class="form-control" placeholder="제목or내용 검색" value="${keyword}" />
				<span class="input-group-btn">
					<button class="btn btn-success" type="submit"><i class='glyphicon glyphicon-search'></i></button>
				</span>
			</div>
		</form>
	</div>
    <!-- 글쓰기 버튼 -->
    <div class="pull-right">
        <a href="${pageContext.request.contextPath}/bbs/document_write.do?category=${category}" class="btn btn-primary">
            <i class="glyphicon glyphicon-pencil"></i> 글쓰기
        </a>
    </div>
</div>
<!-- // 검색폼 + 글쓰기 버튼 끝-->
<!-- 페이지 번호 시작 -->
<nav class="text-center">
	<ul class="pagination">
    	<!-- 이전그룹 -->
    	<c:choose>
	    	<c:when test="${pageHelper.prevPage > 0}">
		    	<c:url var="prevUrl" value="/bbs/document_list.do">
		    		<c:param name="category" value="${category }" />
		    		<c:param name="keyword" value="${keyword }" />
		    		<c:param name="page" value="${page}" />
		    	</c:url>
	    		<li> <a href="${prevUrl}">&laquo;</a> </li>
	    	</c:when>
	    	<c:otherwise>
	    		<li class="disable"> <a href="#">&laquo;</a> </li>
	    	</c:otherwise>
    	</c:choose>
    	
    	<!-- 페이지 번호 -->
    	<c:forEach begin="${pageHelper.startPage}" end="${pageHelper.endPage}" varStatus="s">
    		
    		<c:choose>
	    		<c:when test="${pageHelper.page} == ${s.index}">
	    			<li class="active"><a href="#">${s.index}</a></li>	
	    		</c:when>
	    		<c:otherwise>
				    <c:url var="pageUrl" value="/bbs/document_list.do">
				    	<c:param name="category" value="${category }" />
				    	<c:param name="keyword" value="${keyword }" />
				    	<c:param name="page" value="${s.index}" />
				    </c:url>
				    <li>
				    	<a href="${pageUrl}">${s.index}</a>
				    </li>
	    		</c:otherwise>
    		</c:choose>
    	</c:forEach>
    	<!-- 페이지 번호 -->
    	
    	<!-- 다음 그룹 -->
    	<c:choose>
	    	<c:when test="${pageHelper.nextPage > 0}">
		    	<c:url var="nextUrl" value="/bbs/document_list.do">
		    		<c:param name="category" value="${category }" />
		    		<c:param name="keyword" value="${keyword }" />
		    		<c:param name="page" value="${pageHelper.nextPage}" />
		    	</c:url>
	    		<li>
		    		<a href="${nextUrl}">&raquo;</a>
		    	</li>
	    	</c:when>
	    	<c:otherwise>
	    		<li class="disable">
	    			<a href="#">&raquo;</a>
	    		</li>
	    	</c:otherwise>
    	</c:choose>
    	
	</ul>
</nav>
<!-- // 페이지 번호 끝 -->