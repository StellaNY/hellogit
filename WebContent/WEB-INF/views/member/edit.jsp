<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!doctype html>
<html>

<head>
    <%@ include file = "/WEB-INF/views/inc/head.jsp" %></head>
</head>

<body>
    <%@ include file = "/WEB-INF/views/inc/topbar.jsp" %>
	<div class="container">

		<div class="page-header">
			<h1>회원 정보 수정</h1>
		</div>
		<!-- 수정 폼 시작-->
		<form action="${pageContext.request.contextPath}/member/edit_ok.do" method="POST" class="form-horizontal" name="myform"
			enctype="multipart/form-data">
			<!-- 이메일은 수정 안되게~-->
			<div class="form-group">
				<label for="input" class="col-sm-2 control-label">아이디</label>
				<div class="col-sm-10">
					<p class="form-control-static">${loginInfo.userId}</p>
				</div>
			</div>

			<div class="form-group">
				<label for="user_pw" class="col-md-2 control-label">현재 비밀번호<span
					style="color: red;">*</span></label>
				<div class="col-md-10">
					<input type="password" name="user_pw" id="user_pw"
						class="form-control">
				</div>
			</div>
			<div class="form-group">
				<label for="user_pw" class="col-md-2 control-label">변경할 비밀번호<span
					style="color: red;">*</span></label>
				<div class="col-md-10">
					<input type="password" name="new_user_pw" id="new_user_pw"
						class="form-control">
				</div>
			</div>
			<div class="form-group">
				<label for="user_pw_re" class="col-md-2 control-label"> 변경할
					비밀번호 확인<span style="color: red;">*</span>
				</label>
				<div class="col-md-10">
					<input type="password" name="new_user_pw_re" id="new_user_pw_re"
						class="form-control">
				</div>
			</div>
			<div class="form-group">
				<label for="name" class="col-md-2 control-label">이름<span
					style="color: red;">*</span></label>
				<div class="col-md-10">
					<input type="text" name="name" id="name" class="form-control"
						value="${loginInfo.name}">
				</div>
			</div>
			<div class="form-group">
				<label for="email" class="col-md-2 control-label">이메일<span
					style="color: red;">*</span></label>
				<div class="col-md-10">
					<input type="email" name="email" id="email" class="form-control"
						value="${loginInfo.email}">
				</div>
			</div>
			<div class="form-group">
				<label for="tel" class="col-md-2 control-label">연락처<span
					style="color: red;">*</span></label>
				<div class="col-md-10">
					<input type="tel" name="tel" id="tel" class="form-control"
						placeholder="'-'없이 입력" value="${loginInfo.tel}">
				</div>
			</div>
			<div class="form-group">
				<label for="birthdate" class="col-md-2 control-label">생년월일<span
					style="color: red;">*</span></label>
				<div class="col-md-10">
					<input type="date" name="birthdate" id="birthdate"
						class="form-control" placeholder="yyyy-mm-dd"
						value="${loginInfo.birthdate}">
				</div>
			</div>
			<div class="form-group">
				<label for="gender1" class="col-md-2 control-label">성별<span
					style="color: red;">*</span></label>
				<div class="col-md-10">
					<label for="gender1" class="radio-inline"> <input
						type="radio" name="gender" id="gender1" value="M"
						<c:if test="${loginInfo.gender == 'M' }"> checked</c:if>>남자
					</label> <label for="gender2" class="radio-inline"> <input
						type="radio" name="gender" id="gender2" value="F"
						<c:if test="${loginInfo.gender == 'F' }"> checked</c:if>>여자
					</label>
				</div>
			</div>
			<div class="form-group">
				<label for="postcode" class="col-md-2 control-label">우편번호</label>
				<div class="col-md-10 clearfix">
					<input type="text" name="postcode" id="postcode"
						class="form-control pull-left"
						style="width: 120px; margin-right: 5px;" readonly
						onclick="execDaumPostcode('postcode', 'addr1', 'addr2', 'addr3')"
						value="${loginInfo.postcode}"> <input type="button"
						class="btn btn-success"
						onclick="execDaumPostcode('postcode', 'addr1', 'addr2', 'addr3')"
						value="주소 검색">
				</div>
			</div>
			<div class="form-group">
				<label for="addr1" class="col-md-2 control-label">주소</label>
				<div class="col-md-10">
					<input type="text" name="addr1" id="addr1" class="form-control"
						value="${loginInfo.addr1}" readonly>
				</div>
			</div>
			<div class="form-group">
				<label for="addr2" class="col-md-2 control-label">상세주소</label>
				<div class="col-md-5">
					<input type="text" name="addr2" id="addr2" class="form-control"
						value="${loginInfo.addr2}">
				</div>
				<div class="col-md-5">
					<input type="text" name="addr3" id="addr3" class="form-control"
						value="${loginInfo.addr3}" readonly>
				</div>
			</div>
			<div class="form-group">
				<label for="profile_img" class="col-md-2 control-label">프로필
					사진</label>

				<div class="col-md-10">
					<input type="file" name="profile_img" id="profile_img"
						class="form-control">
				</div>
			</div>
			<c:if test="${cookie.profileThumbnail != null}">
				<div class="form-group">

					<div class="col-md-10 col-md-offset-2">
						<p>
							<img src="${pageContext.request.contextPath}/download.do?file=${cookie.profileThumbnail.value}" class="img-circle">
							<label class="checkbox-inline">
								<input type="checkbox" name="img_del" id="img_del" value="Y" />
								이미지 삭제 
							</label>
						</p>
						<script type="text/javascript">
							$(function(){
								//이미지가 등록된 상태이므로 파일의 신규 등록을 방지
								$("#profile_img").prop("disabled", true);
								$("#img_del").change(function(){
									$("#profile_img").prop("disabled", !$(this).is(":checked"));
								});
							});
						
						</script>
					</div>
				</div>
			</c:if>

			<div class="form-group">
				<div class="col-md-10 col-sm-offset-2">
					<button type="submit" class="btn btn-danger">수정하기</button>
					<button type="reset" class="btn btn-warning">다시작성</button>
				</div>
			</div>
		</form>


	</div>
    <%@ include file = "/WEB-INF/views/inc/footer.jsp" %>
</body>

</html>