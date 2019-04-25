<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <!-- 로고 영역 -->
        <div class="navbar-header">
            <!-- 반응형 메뉴 버튼 -->
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <!-- //반응형 메뉴 버튼-->
            <!-- 로고 -->
            <a class="navbar-brand" href="${pageContext.request.contextPath}/index.do">STELLA</a>
            <!-- // 로고-->
        </div>

        <!-- 메뉴 영역 -->
        <div class="navbar-collapse collapse">
            <!-- 사이트 메뉴 -->
            <ul class="nav navbar-nav">
                <li> <a href="${pageContext.request.contextPath}/content/introduce.do">Home</a> </li>
                <li> <a href="${pageContext.request.contextPath}/bbs/document_list.do?category=notice">공지사항</a> </li>
                <li> <a href="${pageContext.request.contextPath}/bbs/document_list.do?category=gallery">포토갤러리</a> </li>
                <li> <a href="${pageContext.request.contextPath}/bbs/document_list.do?category=free">자유게시판</a> </li>
                <li> <a href="${pageContext.request.contextPath}/bbs/document_list.do?category=qna">질문/답변</a> </li>
            </ul>
            <!--// 사이트 메뉴 -->
            <c:choose>
                <c:when test="${loginInfo == null }">
                    <!-- 로그인  (메뉴 우측)-->
                    <form class="navbar-form navbar-right" method="post"
                        action="${pageContext.request.contextPath}/member/login_ok.do">
                        <div class="form-group">
                            <input type="text" name="user_id" id="user_id" class="form-control" placeholder="USER ID">
                        </div>

                        <div class="form-group">
                            <input type="password" name="user_pw" id="user_pw" class="form-control"
                                placeholder="PASSWORD">
                        </div>

                        <button type="submit" class="btn btn-default">
                            <i class="glyphicon glyphicon-user"></i>
                        </button>
                        <a class="btn btn-warning" href="${pageContext.request.contextPath}/member/join.do">
                            <i class="glyphicon glyphicon-plus"></i>
                        </a>
                        <a class="btn btn-info" href="${pageContext.request.contextPath}/member/find_pw.do">
                            <i class="glyphicon glyphicon-search"></i>
                        </a>
                    </form>
                    <!-- //로그인  (메뉴 우측)-->
                </c:when>
                <c:otherwise>
                    <!-- 로그인 된 경우 -->

                    <ul class="nav navbar-nav">

                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown" style="padding: 5px">
                                <c:if test="${cookie.profileThumbnail != null}">
                                    <img src="${pageContext.request.contextPath}/download.do?file=${cookie.profileThumbnail.value}" class="img-circle">
                                </c:if>
                                ${loginInfo.name}님 <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                            <li>
                            	<a href="${pageContext.request.contextPath}/member/logout.do">로그아웃</a>
                            </li>
                            <li>
                            	<a href="${pageContext.request.contextPath}/member/edit.do">회원정보수정</a>
                            </li>
                            <li>
                            	<a href="${pageContext.request.contextPath}/member/out.do">회원탈퇴</a>
                            </li>
                            </ul>
                        </li>
                    </ul>


                    <!--// 로그인 된 경우 -->
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>