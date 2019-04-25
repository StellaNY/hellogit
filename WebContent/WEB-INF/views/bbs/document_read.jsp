<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri = "http://java.sun.com/jsp/jstl/functions" %>
<!doctype html>
<html>
<head>
	<%@ include file = "/WEB-INF/views/inc/head.jsp" %>
		
</head>
<body>

	<%@ include file = "/WEB-INF/views/inc/topbar.jsp" %>
	<div class="container">
		<h1>${bbsName } - <small>글 읽기</small></h1>
		
		<!-- 제목, 작성자, 작성일시, 조회수 -->
		<div class="alert alert-info">
            <h3 style="margin: 0">${readDocument.subject} <br />
            <small>${readDocument.writerName}
                <a href="mailto:${readDocument.email}">
                <span class="glyphicon glyphicon-envelope" aria-hidden="true"></span>
                </a>
                / 조회수 : ${readDocument.hit} / 작성일시 : ${readDocument.regDate}
            </small>
            </h3>
        </div>
        
		<!-- 내용 -->
		<section style="padding: 0 10px 20px 10px">
			${readDocument.content }
		</section>
		
		<!-- 첨부파일 목록 표시 -->
        <c:if test="${fn:length(fileList) > 0 }">
            <!-- 첨부파일 목록 -->
            <table class="table table-bordered table-hover">
                <tbody>
                    <tr>
                        <th class="warning" style="width: 100px">첨부파일</th>
                    </tr>
                    <tr>
                        <td>
							<c:forEach var="file" items="${fileList }">
								<!-- 다운로드를 위한 URL만들기 -->
								<c:url var="downloadUrl" value="/download.do">
									<c:param name="file" value="${file.fileDir }/${file.fileName }" />
									<c:param name="orgin" value="${file.originName }" />
								</c:url>
								<!-- 다운로드 링크 -->
								<a href="${downloadUrl }" class="btn btn-link btn-xs">${file.originName }</a>
							</c:forEach>
                        </td>
                    </tr>
                </tbody>
            </table>

            <!-- 이미지만 별도로 화면에 출력하기 -->
            <c:forEach var="file" items="${fileList }">
            	<c:if test="${fn:substringBefore(file.contentType, '/') == 'image' }">
            		<c:url var="downloadUrl" value="/download.do">
            			<c:param name="file" value="${file.fileDir }/${file.fileName }" />
            		</c:url>
            		<div class="row">
	            		<div class="col-xs-6 col-sm-6 col-md-3 col-lg-3">
							<div class="thumbnail">
		            			<img src="${downloadUrl}" class="img-responsive" style="margin: auto" />
							    <!-- 파일 이름 -->
							    <div class="item clearfix">
							   		<div class="text-center">${file.originName }</div>
							    </div>
							</div>
						</div>
					</div>
            	</c:if>
            </c:forEach>
        </c:if>
        
		<!-- 다음글 이전글 -->
        <table class="table table-bordered table-hover">
            <tbody>
                <tr>
                    <th class="success" style="width: 100px">다음글</th>
                    <td>
                        <c:choose>
                        	<c:when test="${nextDocument != null }">
                        		<c:url var="nextUrl" value="/bbs/document_read.do">
                        			<c:param name="category" value="${category}" />
                        			<c:param name="document_id" value="${nextDocument.id}" />
                        		</c:url>
                        		<a href="${nextUrl}">${nextDocument.subject}</a>
                        	</c:when>
                        	
                        	<c:otherwise>
                        		다음글이 없습니다.
                        	</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
                <tr>
                    <th class="success" style="width: 100px">이전글</th>
                    <td>
                        <c:choose>
                        	<c:when test="${prevDocument != null }">
                        		<c:url var="prevUrl" value="/bbs/document_read.do">
                        			<c:param name="category" value="${category}" />
                        			<c:param name="document_id" value="${prevDocument.id}" />
                        		</c:url>
                        		<a href="${prevUrl}">${prevDocument.subject}</a>
                        	</c:when>
                        	
                        	<c:otherwise>
                        		이전글이 없습니다.
                        	</c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </tbody>
        </table>
        
		<!-- 버튼들 -->
        <div class="clearfix">
            <div class="pull-right">
                <a class="btn btn-info" href="${pageContext.request.contextPath}/bbs/document_list.do?category=${category}" role="button">목록</a>
                <a class="btn btn-primary" href="${pageContext.request.contextPath}/bbs/document_write.do?category=${category}" role="button">글쓰기</a>
                <a class="btn btn-warning" href="${pageContext.request.contextPath}/bbs/document_edit.do?category=${category}&document_id=${readDocument.id}" role="button">수정</a>
                <a class="btn btn-danger" href="${pageContext.request.contextPath}/bbs/document_del.do?category=${category}&document_id=${readDocument.id}" role="button">삭제</a>
            </div>
        </div>
		<!-- //버튼들 -->
		
		<!-- 댓글 -->
        <hr />
        <form id="comment_form" action="${pageContext.request.contextPath}/bbs/comment_insert.do" method="post">
            <input type="hidden" name="document_id" value="${readDocument.id}" />
            <c:if test="${loginInfo == null}">
                <div class="form-group form-inline">
                    <div class="form-group">
                        <input type="text" name="writer_name" id="writer_name" class="form-control" placeholder="작성자" />
                    </div>
                    <div class="form-group">
                        <input type="password" name="writer_pw" id="writer_pw" class="form-control" placeholder="비밀번호" />
                    </div>
                    <div class="form-group">
                        <input type="email" name="email" id="email" class="form-control" placeholder="이메일" />
                    </div>
                </div>
            </c:if>
            <!-- 내용입력, 저장 버튼-->
            <div class="form-group">
                <div class="input-group">
                    <textarea class="form-control custom-control" id="content" placeholder="Search" name="content" style="resize:none; height: 80px;"></textarea>
                    <span class="input-group-btn">
                        <button type="submit" class="btn btn-success"  style="height: 80px;">저장</button>
                    </span>
                </div>
            </div>
        </form>
        <!-- 덧글 리스트 -->
        <ul class="media-list" id="comment_list">
        </ul>
		<!-- //댓글 -->
	</div>
	
	
	<!-- 덧글 삭제에서 사용할 modal 형식 -->
	<div class="modal fade" id="comment_delete_modal">
		<div class="modal-dialog modal-sm">
			<div class="modal-content"></div>
		</div>
	</div>
	<!-- 덧글 수정에서 사용할 modal 형식 -->
	<div class="modal fade" id="comment_edit_modal">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
	
	
	<%@ include file = "/WEB-INF/views/inc/footer.jsp" %>	
	
	<!-- 덧글 항목에 대한 템플릿 참조 -->
	<script id="tmpl_comment_item" type="text/x-handlebars-template">
    <li class="media" style='border-top: 1px dotted #ccc; padding-top: 15px' id="comment_{{id}}">
        <div class="media-body">
            <h4 class="media-heading clearfix">
                <!-- 작성자,작성일시 -->
                <div class='pull-left'>
                    {{writerName}}
                    <small>
                        <a href='mailto:{{email}}'>
                            <i class='glyphicon glyphicon-envelope'></i></a>
                            / {{regDate}}
                    </small>
                </div>
                <!-- 수정,삭제 버튼 -->
                <div class='pull-right'>
                    <a href='${pageContext.request.contextPath}/bbs/comment_edit.do?comment_id={{id}}' data-toggle="modal" data-target="#comment_edit_modal" class='btn btn-warning btn-xs'><i class='glyphicon glyphicon-edit'></i>
                    </a>
                    <a href='${pageContext.request.contextPath}/bbs/comment_del.do?comment_id={{id}}' data-toggle="modal" 
						data-target="#comment_delete_modal" class='btn btn-danger btn-xs'><i class='glyphicon glyphicon-remove'></i>
                    </a>
                </div>
            </h4>
            <!-- 내용 -->
            <p>{{{content}}}</p>
        </div>
    </li>
	</script>
	<!-- //덧글 에서 사용할 modal 형식 -->
	<script>
		$(function(){
			// 페이징을 위한 변수
			var nowPage = 0;
			var lastPage = 0;
			/** ajax 재사용을 위한 function*/
			function add_comment() {
				$.get("${pageContext.request.contextPath}/bbs/comment_list.do", {document_id:"${readDocument.id}", page: nowPage+1},function(json){
					if(json.rt != "OK"){
						//alert(json.rt);
						return false;
					}
					nowPage = json.pageHelper.page;
					lastPage = json.pageHelper.endPage;

					// 핸들바 템플릿 사용
					var template = Handlebars.compile($("#tmpl_comment_item").html());
					
					// JSON에 포함된 '&lt;br /&gt;'을 검색해서 <br />로 변경하고 뿌려줌
					for(var i=0; i<json.item.length; i++){
						json.item[i].content = json.item[i].content.replace(/&lt;br \/&gt;/g, "<br />");
						
						var html = template(json.item[i]);
						$("#comment_list").append(html);
					}
									
				});
			}// end add_comment

			/** 페이지가 열리면서 동작하도록 이벤트 정의 없이 Ajax요청 */
			add_comment();
			
			/** 덧글 작성 폼의 submit 이벤트 ajax 구현*/
			// <form>요소의 method, action속성과 <input>태그를 ajax요청으로 자동구성
			$("#comment_form").ajaxForm(function(json){
				if(json.rt != "OK"){
					alert(json.rt);
					return false;
				}
				
				// 줄바꿈에 대한 처리
				// --> 정규표현식 /~~~/g는 문자열 전체의 의미.
				// --> JSON에 포함된 '&lt;br /&gt;'을 검색해서 <br />로 변경함.
				json.item.content = json.item.content.replace(/&lt;br \/&gt;/g, "<br />");
				
				// 핸들바 템플릿 사용
				var template = Handlebars.compile($("#tmpl_comment_item").html());
				var html = template(json.item);
				
				$("#comment_list").append(html);
				$("#comment_form").trigger('reset');
			})
			
			/** 덧글 페이징 구현 (인피니티 스크롤) */
		    $(window).scroll(function(){
		    	if ($(window).height() + $(window).scrollTop() == $(document).height()) {
		    		if(nowPage != lastPage){
		    			add_comment();
		    		}

                };

		    });
			
			/** 모든 modal창의 캐시 방지 처리*/
			$(".modal").on("hidden.bs.modal", function(e){
				$(this).removeData("bs.modal");
			})
			
			/**동적으로 로드된 폼 안에서의 submit 이벤트*/
			$(document).on("submit", "#comment_delete_form", function(e){
				e.preventDefault();
				
				// ajaxForm Plugin 강제 호출
				$(this).ajaxSubmit(function(json){
					if(json.rt != "OK"){
						alert(json.rt);
						return false;
					}
					
					alert("삭제 되었습니다.");
					
					// modal 강제로 닫기
					$("#comment_delete_modal").modal("hide");
					
					// 덧글 일련번호를 통해 삭제한 덧글틀 삭제하쟈
					var comment_id = json.commentId;
					$("#comment_"+comment_id).remove();
					
				});
			});

			/**동적으로 로드된 폼 안에서의 submit 이벤트*/
			$(document).on("submit", "#comment_edit_form", function(e){
				e.preventDefault();
				
				// ajaxForm Plugin 강제 호출
				$(this).ajaxSubmit(function(json){
					if(json.rt != "OK"){
						alert(json.rt);
						return false;
					}
					alert("수정 되었습니다.");
					json.item.content = json.item.content.replace(/&lt;br \/&gt;/g, "<br />");
					
					// 핸들바 템플릿 사용
					var template = Handlebars.compile($("#tmpl_comment_item").html());
					var html = template(json.item);
					$("#comment_"+json.item.id).replaceWith(html);
					
					$("#comment_edit_modal").modal('hide');
					
				});
			});
		});
	</script>
</body>
</html>