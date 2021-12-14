<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 등록</title>
<script type="text/javascript">
// 허용되는 이미지 파일 형식들
var imageExt = ["JPG", "JPEG", "GIF", "PNG"];

$(function(){
	// 전달할 때의 데이터 찍기
	$("#writeForm").submit(function(){
		var fileName = $("#imageFile").val();
		var ext = fileName.substring(fileName.lastIndexOf(".")+1).toUpperCase();
		// alert(ext);
	
		// 이미지 확장자인지 확인하는 반복문
		var checkExt = false; // 지원하지 않는 확장자를 기본으로 셋팅
		for(i = 0; i < imageExt.length; i++){
			if(ext == imageExt[i]){
				checkExt = true; // 지원하는 확장자로 바꾼다.
				break;
			}
		}
		// 지원하지 않는 이미지 파일 선택경의 처리
		if(!checkExt){
			alert("지원하지 않는 이미지 파일입니다.");
			$("#imageFile").focus();
			return false;
		}
		// submit을 취소
		// return false;
	});
	//취소 버튼을 누르면 이전 페이지로 가게하기. history back
	$("#cancelBtn").click(function(){
		history.back();
	});
});
</script>

</head>
<body>
<div class="container">
	<h1>이미지 등록</h1>
	<!-- 파일첨부를 하는 입력에는 반드시 post방식이여야 하고 enctype 을 지정해야만 한다.
	  input tag의 type="file"로 지정한다. -->
	<form action="write.do" method="post" enctype="multipart/form-data" id="writeForm" >
		<!-- param으로 ?뒤에 넘어오는 a href= write.do?perPageNum=${pageObject.perPageNum}그대로 받음.  -->
		<input name="perPageNum" value="${param.perPageNum }" type="hidden">
		<div class="form-group">
			<h3>${login.id}님이 로그인 중입니다.</h3>
		</div>
		<div class="form-group">
			<label for="title">제목</label>
			<input name="title" id="title" class="form-control" />
		</div>
		<div class="form-group">
			<label for="content">내용</label>
			<textarea name="content" id="content" class="form-control" rows="5"
			 ></textarea>
		</div>
		<div class="form-group">
			<label for="imageFile">이미지 파일(JPG, JPEG, GIF, PNG - 이미지 지원)</label>
			<input name="multipartFile" id="imageFile" type="file" class="form-control" />
		</div>
		<button class="btn btn-defualt">올리기</button>
		<button type="reset" class="btn btn-defualt">새로입력</button>
		<button type="button" id="cancelBtn" class="btn btn-defualt">취소</button>
	</form>
</div>
</body>
</html>