<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!--
	/**
	 * 
	 * @author : "StellaNY (stella10137@gmail.com)"
	 * @description : 덧글 수정을 위한 view 페이지 
	 * @Date 2019-04-04
	 * 
	 */
-->
<form id="comment_edit_form" action="${pageContext.request.contextPath}/bbs/comment_edit_ok.do" method="post">

    <input type="hidden" name="comment_id" id="comment_id" value="${comment.id}">
    <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" aria-lagel="Close">&times;</button>
            <h4 class="modal-title">덧글 수정</h4>
        </div>
        <div class="modal-body">
            <c:choose>
                <c:when test="${comment.memberId == loginInfo.id}">
                    <div class="form-group">
                        <textarea name="content" id="content" class="form-control" rows="5" >${comment.content}</textarea>
                    </div>
                </c:when>
            	<c:otherwise>
	                <!-- 자신의 글이 아닌 경우 -->
	                <p>삭제하시려면 비밀번호를 입력하세요.</p>
	                <div class="form-group">
	                	<input type="text" name="writer_name" id="writer_name" class="form-control" value="${comment.writerName}"/>
	                </div>
	                <div class="form-group">
	                    <input type="password" name="writer_pw" id="writer_pw" class="form-control"placeholder="작성시 설정한 비밀번호" />
	                </div>
	                <div class="form-group">
	                    <input type="email" name="email" id="email" class="form-control"value="${comment.email}" />
	                </div>
                    <div class="form-group">
                        <textarea name="content" id="content" class="form-control" rows="5" >${comment.content}</textarea>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
        <div class="modal-footer">
            <button type="button" class="btn btn-warning" data-dismiss="modal">취소</button>
            <button type="submit" class="btn btn-danger" id="btn_com_del">수정</button>
        </div>
</form>