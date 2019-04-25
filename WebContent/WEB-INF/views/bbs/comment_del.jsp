<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 
	 덧글 삭제를 위한 modal 폼!
-->
<form action="${pageContext.request.contextPath}/bbs/comment_del_ok.do" method="post" id="comment_delete_form">
    
    <input type="hidden" name="comment_id" id="comment_id" value="${commentId}">
    <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-hidden="true" aria-lagel="Close">&times;</button>
        <h4 class="modal-title">덧글삭제</h4>
    </div>
    <div class="modal-body">
        <c:choose>
            <c:when test="${myComment == ture}">
                <!-- 자신의 글에 대한 삭제-->
                <p>정말 이 덧글을 삭제 하시곘습니까?</p>
            </c:when>
            <c:otherwise>
                <!-- 자신의 글이 아닌 경우 -->
                <p>삭제하시려면 비밀번호를 입력하세요.</p>
                <div class="form-group">
                    <input type="password" name="writer_pw" id="writer_pw" class="form-control" required="required" title="">
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    <div class="modal-footer">
        <button type="button" class="btn btn-warning" data-dismiss="modal">취소</button>
        <button type="submit" class="btn btn-danger" id="btn_com_del">삭제</button>
    </div>
    
</form>
