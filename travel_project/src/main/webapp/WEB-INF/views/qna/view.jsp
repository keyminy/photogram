<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>qna 게시판 글보기</title>
<style>
.title{
	font-size: 35px;
	font-weight: bold;
}
</style>
<script type="text/javascript">
$(function(){
	${(empty msg)?"":"alert('" += msg += "');"}
});
</script>
</head>
<body>
	<div class="container">
	 <h1>게시판 글 보기</h1>
		<!-- 데이터 표시하는 부분 -->
		<ul class="list-group">
			<li class="list-group-item row">
					<div>
						 <span class="title">${vo.title}</span> 
					</div>
					${vo.id}(${vo.name})
					(작성일 : <fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd"/>
		  		<fmt:formatDate value="${vo.writeDate}" pattern="hh:mm:ss"/>)
					<span class="badge">${vo.hit}</span>
			</li>
			 <li class="list-group-item row"> 
		  	<div class="content">
		  		<pre>${vo.content}</pre>
		  	</div>
		  </li>
		</ul>
		<div class="btnGroup">
			<a href="answerForm.do?no=${vo.no}&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}" 
			class="btn btn-success">답변하기</a>
			<!-- 내가 쓴글만 수정,삭제가 가능해야한다. + 관리자는 수정,삭제가 가능해야함 -->
			<c:if test="${vo.id == login.id || login.gradeNo==9}">
				<a href="updateForm.do?no=${vo.no}&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}"
				class="btn btn-info">수정</a>
				<a class="btn btn-danger" href="delete.do?no=${vo.no}&perPageNum=${pageObject.perPageNum}"
				onclick="return confirm('정말로 삭제하시겟습니까?');">삭제</a>
			</c:if>
				<a href="list.do?page=${pageObject.page}&perPageNum=${pageObject.perPageNum}" 
			class="btn btn-primary">리스트</a>
		</div>
	</div>
</body>
</html>