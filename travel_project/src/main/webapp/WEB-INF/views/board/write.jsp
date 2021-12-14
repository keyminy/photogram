<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기 입력창</title>
<script>
	$(function(){
		$(".cancleBackBtn").click(function(){
			history.back();//리스트로 돌아가기
		});
	});
</script>
</head>
<body>
	<div class="container">
		<h1>게시판 글쓰기 폼</h1>
		<form action="write.do" method="post">
			<input type="hidden" name="perPageNum" value="${param.perPageNum}"/>
			<div class="form-group">
				<label for="title">제목 : </label>
	      <input type="text" name="title" class="form-control" id="title" 
	      pattern=".{4,100}" maxlength="100" required="required"
	      title="4자 이상 100자 이하 입력" placeholder="4자이상 100자이하 입력해야합니다.">
			</div>
			<div class="form-group">
				<label for="content">내용 : </label>
				<textarea name="content" id="content" rows="6" class="form-control"></textarea>
			</div>
			<div class="form-group">
				<label for="writer">작성자 : </label>
				<input type="text" name="writer" class="form-control" id="writer"
				required="required" pattern="[A-Za-z가-힣ㄱ-ㅎ][0-9A-Za-z가-힣ㄱ-ㅎ]{1,9}"
				placeholder="이름은 2자부터 10자까지" title="이름은 2자부터 10자까지, 첫글자는 숫자가 올 수 없습니다."/>
			</div>
			<div class="form-group">
				<label for="pw">비밀번호 : </label>
				<input type="text" name="pw" id="pw" class="form-control"
				pattern="[^가-힣ㄱ-ㅎ]{4,20}" required="required"
				placeholder="4-20자,한글은 입력할 수 없습니다."
				title="4-20자,한글은 입력할 수 없습니다."/>
			</div>
		 <button class="btn btn-success">등록</button>
	    <button type="reset" class="btn btn-warning">새로고침</button>
	    <button type="button" class="btn btn-danger cancleBackBtn">취소</button>
		</form>
	</div>
</body>
</html>