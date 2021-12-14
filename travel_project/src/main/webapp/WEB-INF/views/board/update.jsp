<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 수정</title>
<script type="text/javascript">
	$(function(){
		$(".cancleBackBtn").click(function(){
			history.back();
		});
	});
</script>
</head>
<body>
<div class="container">
		<h1>게시판 글 수정 폼</h1>
		<form action="update.do" method="post">
			<input type="hidden" name="no" value="${vo.no}" />
			<input type="hidden" name="perPageNum" value="${param.perPageNum}"/>
			<input type="hidden" name="page" value="${param.page}"/>
			<input type="hidden" name="key" value="${param.key}"/>
			<input type="hidden" name="word" value="${param.word}"/>
			<div class="form-group">
				<label for="title">제목 : </label>
	      <input type="text" name="title" class="form-control" id="title" 
	      pattern=".{4,100}" maxlength="100" required="required"
	      title="4자 이상 100자 이하 입력" placeholder="4자이상 100자이하 입력해야합니다."
	      value="${vo.title}">
			</div>
			<div class="form-group">
				<label for="content">내용 : </label>
				<textarea name="content" id="content" rows="6" class="form-control">${vo.content}</textarea>
			</div>
			<div class="form-group">
				<label for="writer">작성자 : </label>
				<input type="text" name="writer" class="form-control" id="writer"
				required="required" pattern="[A-Za-z가-힣][0-9A-Za-z가-힣]{1,9}"
				placeholder="이름은 2자부터 10자까지" title="이름은 2자부터 10자까지, 첫글자는 숫자가 올 수 없습니다."
				value="${vo.writer}"/>
			</div>
			<div class="form-group">
				<label for="pw">비밀번호 : 본인 확인용</label>
				<input type="password" name="pw" id="pw" class="form-control"
				pattern="[^가-힣ㄱ-ㅎ]{4,20}" required="required"
				placeholder="4-20자,한글은 입력할 수 없습니다."
				title="4-20자,한글은 입력할 수 없습니다."/>
			</div>
		  <button class="btn btn-success">수정</button>
	    <button type="reset" class="btn btn-warning">새로고침</button>
	    <button type="button" class="btn btn-danger cancleBackBtn">취소</button>
		</form>
	</div>
</body>
</html>