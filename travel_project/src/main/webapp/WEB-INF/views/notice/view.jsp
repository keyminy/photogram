<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>공지 글보기</title>
<script type="text/javascript">
	$(function(){
		//msg 뿌려주기
		${(empty msg)?"":"alert('"+= msg +="');"};
	});
</script>
</head>
<body>
	<div class="container">
	 <h1>공지 글 보기</h1>
		<!-- 데이터 표시하는 부분 -->
		<table class="table">
			<tr>
				<th>번호</th>
				<td>${vo.no}</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>${vo.title}</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><pre style="font-size: 16px;">${vo.content}</pre></td>
			</tr>
			<tr>
				<th>공지시작일</th>
				<td>${vo.startDate}</td>
			</tr>
			<tr>
				<th>공지종료일</th>
				<td>${vo.endDate}</td>
			</tr>
			<tr>
				<th>공지작성일</th>
				<td>${vo.writeDate}</td>
			</tr>
			<tr>
				<th>최근수정일</th>
				<td>${vo.updateDate}</td>
			</tr>
		</table>
		<div class="btnGroup">
			<c:if test="${login.gradeNo==9}">
				<a href="update.do?no=${vo.no}&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}"
				 class="btn btn-info">수정</a>
				<a class="btn btn-danger" href="delete.do?no=${vo.no}&perPageNum=${pageObject.perPageNum}"
					onclick="return confirm('정말로 삭제하시겟습니까?');">삭제</a>
			</c:if>
			<a href="list.do?page=${pageObject.page}&perPageNum=${pageObject.perPageNum}" 
			class="btn btn-default">리스트</a>
		</div>
	</div><!-- end container -->
</body>
</html>