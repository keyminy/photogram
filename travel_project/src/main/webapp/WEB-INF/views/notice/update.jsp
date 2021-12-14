<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 글 수정</title>
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
		<h1>공지 글 수정 폼</h1>
		<form action="update.do" method="post">
			<input type="hidden" name="no" value="${vo.no}" />
			<input type="hidden" name="perPageNum" value="${param.perPageNum}"/>
			<input type="hidden" name="page" value="${param.page}"/>
			<div class="form-group">
				<label for="title">제목 : </label>
	      <input type="text" name="title" class="form-control" id="title" 
	      pattern=".{4,100}" maxlength="100" required="required" value="${vo.title}"
	      title="4자 이상 100자 이하 입력" placeholder="4자이상 100자이하 입력해야합니다.">
			</div>
			<div class="form-group">
				<label for="content">내용 : </label>
				<textarea name="content" id="content" rows="6" class="form-control">${vo.content}</textarea>
			</div>
			<div class="form-group">
				<label for="content">공지시작일 : </label>
				<input type="date" name="startDate" class="form-control" 
				id="startDate" required="required" value="${vo.startDate}"/>
			</div>
			<div class="form-group">
				<label for="content">공지종료일 : </label>
				<input type="date" name="endDate" class="form-control"
				id="endDate" required="required" value="${vo.endDate}"/>
			</div>
		   <button class="btn btn-success">수정</button>
	    <button type="reset" class="btn btn-warning">새로고침</button>
	    <button type="button" class="btn btn-danger cancleBackBtn">취소</button>
		</form>
	</div>
</body>
</html>