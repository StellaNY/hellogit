<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html>

<head>
    <%@ include file = "/WEB-INF/views/inc/head.jsp" %></head>

<body>
    <%@ include file = "/WEB-INF/views/inc/topbar.jsp" %>
    
    <div class="container">
        <h1>회원 가입</h1>
	    
	    <!-- 가입폼 시작 -->
	    <form action="${pageContext.request.contextPath}/member/join_ok.do" method="POST" class="form-horizontal"
	        role="form" name="myform" enctype="multipart/form-data">
	
	        <div class="form-group">
	            <label for="user_id" class="col-md-2 control-label">아이디<span style="color:red;">*</span></label>
	            <div class="col-md-10">
	                <input type="text" name="user_id" id="user_id" class="form-control">
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="user_pw" class="col-md-2 control-label">비밀번호<span style="color:red;">*</span></label>
	            <div class="col-md-10">
	                <input type="password" name="user_pw" id="user_pw" class="form-control">
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="user_pw_re" class="col-md-2 control-label">비밀번호 확인<span style="color:red;">*</span></label>
	            <div class="col-md-10">
	                <input type="password" name="user_pw_re" id="user_pw_re" class="form-control">
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="name" class="col-md-2 control-label">이름<span style="color:red;">*</span></label>
	            <div class="col-md-10">
	                <input type="text" name="name" id="name" class="form-control">
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="email" class="col-md-2 control-label">이메일<span style="color:red;">*</span></label>
	            <div class="col-md-10">
	                <input type="email" name="email" id="email" class="form-control">
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="tel" class="col-md-2 control-label">연락처<span style="color:red;">*</span></label>
	            <div class="col-md-10">
	                <input type="tel" name="tel" id="tel" class="form-control" placeholder="'-'없이 입력">
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="birthdate" class="col-md-2 control-label">생년월일<span style="color:red;">*</span></label>
	            <div class="col-md-10">
	                <input type="text" name="birthdate" id="birthdate" class="form-control datepicker" placeholder="yyyy-mm-dd">
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="gender1" class="col-md-2 control-label">성별<span style="color:red;">*</span></label>
	            <div class="col-md-10">
	                <label for="gender1" class="radio-inline">
	                    <input type="radio" name="gender" id="gender1" value="M">남자
	                </label>
	                <label for="gender2" class="radio-inline">
	                    <input type="radio" name="gender" id="gender2" value="F">여자
	                </label>
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="postcode" class="col-md-2 control-label">우편번호</label>
	            <div class="col-md-10 clearfix">
	                <input type="text" name="postcode" id="postcode" class="form-control pull-left"
	                    style="width: 120px; margin-right: 5px;" readonly  onclick="execDaumPostcode('postcode', 'addr1', 'addr2', 'addr3')">
	                <input type="button" class="btn btn-success" onclick="execDaumPostcode('postcode', 'addr1', 'addr2', 'addr3')" value="주소 검색">
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="addr1" class="col-md-2 control-label">주소</label>
	            <div class="col-md-10">
	                <input type="text" name="addr1" id="addr1" class="form-control" readonly>
	            </div>
	        </div>
	        <div class="form-group">
	            <label for="addr2" class="col-md-2 control-label">상세주소</label>
		        <div class="col-md-5">
		            <input type="text" name="addr2" id="addr2" class="form-control">
		        </div>
		        <div class="col-md-5">
		            <input type="text" name="addr3" id="addr3" class="form-control" readonly>
		        </div>
	        </div>
	        <div class="form-group">
	            <label for="profile_img" class="col-md-2 control-label">프로필 사진</label>
	            <div class="col-md-10">
	                <input type="file" name="profile_img" id="profile_img" class="form-control">
	            </div>
	        </div>
	
	        <div class="form-group">
	            <div class="col-md-10 col-sm-offset-2">
	                <button type="submit" class="btn btn-danger">가입하기</button>
	                <button type="reset" class="btn btn-warning">다시작성</button>
	            </div>
	        </div>
	    </form>
   		<!-- 가입폼 끝 -->
    </div>
    
    <%@ include file = "/WEB-INF/views/inc/footer.jsp" %>
</body>

</html>