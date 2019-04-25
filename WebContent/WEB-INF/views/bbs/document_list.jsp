<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
	<%@ include file = "/WEB-INF/views/inc/head.jsp" %>
</head>
<body>

	<%@ include file = "/WEB-INF/views/inc/topbar.jsp" %>
	<div class="container">
		<h1>${bbsName } - <small>글 목록</small></h1>
		<!--  글목록 시작 -->
        <div class="table-responsive">
            <table class="table table-hover ">
                <thead>
                    <tr>
                        <th class="text-center" style="width:100px">번호</th>
                        <th class="text-center">제목</th>
                        <th class="text-center" style="width:100px">작성자</th>
                        <th class="text-center" style="width:100px">조회수</th>
                        <th class="text-center" style="width:100px">작성일</th>
                    </tr>
                </thead>
                <tbody>
                	<c:choose>
                		<c:when test="${fn:length(documentList) > 0 }">
                			<c:forEach var="docu" items="${documentList}">
                			
                			<c:url var="readUrl" value="/bbs/document_read.do">
	                			<c:param name="category" value="${docu.category }" />
	                			<c:param name="document_id" value="${docu.id }" />
	                		</c:url>
	                		<c:set var = "mark" value="<mark>${keyword}</mark>"/>
	                		
                			<tr class="clickable-row" data-href="${readUrl}" style="cursor: pointer;">
                				<td class="text-center">${docu.id}</td>
                				<td class="text-center">${fn:replace(docu.subject, keyword, mark)}</td>
                				<td class="text-center">${docu.writerName}</td>
                				<td class="text-center">${docu.hit}</td>
                				<td class="text-center">${docu.regDate}</td>
                			</tr>
                			</c:forEach>
                		</c:when>
                		<c:otherwise>
		                    <tr>
		                        <td colspan="5" class="text-center" style="line-height: 100px;"></td>
		                    </tr>
                		</c:otherwise>
                	</c:choose>
                </tbody>
            </table>

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