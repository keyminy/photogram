<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 보내기 폼</title>
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
		<h1>메시지 보내기 폼</h1>
		<form action="write.do" method="post">
			<div class="form-group">
				<label for="accepter">받는 분 아이디</label>
	      <input type="text" name="accepter" class="form-control" id="accepter" 
	      required="required" placeholder="받는 사람의 아이디를 입력해주세요">
			</div>
			<div class="form-group">
				<label for="content">내용 : </label>
				<textarea name="content" id="content" rows="6" class="form-control" required></textarea>
			</div>
		 	<button class="btn btn-success">보내기</button>
	    <button type="reset" class="btn btn-warning">새로고침</button>
	    <button type="button" class="btn btn-danger cancleBackBtn">취소</button>
		</form>
	</div>
</body>
</html>