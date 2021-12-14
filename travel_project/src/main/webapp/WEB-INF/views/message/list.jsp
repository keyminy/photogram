<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="pageObject" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 리스트</title>
<link rel="stylesheet" href="/resources/css/message/messagelist.css">
<script type="text/javascript">
$(function(){
	${(empty msg)?"":"alert('" += msg += "');"}
	//제목 클릭시 view로 가기
	$(".dataRow").click(function(){
		var no = $(this).find(".no").text();
		var query = ${(empty pageObject)?"''":" '&page="+=pageObject.page
				+="&perPageNum="+=pageObject.perPageNum+="' "};
		query += ${(empty param.mode)?"''":"'&mode="+=param.mode+="'"};
		location = "view.do?no=" + no+query;
	});
});
</script>
</head>
<body>
	<div class="container">
		<ul class="nav nav-tabs">
		  <li ${(empty pageObject.acceptMode || pageObject.acceptMode == 3)?"class='active'":""}>
		  <a href="list.do?mode=3">모두 보기</a></li>
		  <li ${(pageObject.acceptMode == 1)?"class='active'":""}>
		  <a href="list.do?mode=1">받은 메시지</a></li>
		  <li ${(pageObject.acceptMode == 2)?"class='active'":""}>
		  <a href="list.do?mode=2">보낸 메시지</a></li>
		  <li ${(pageObject.acceptMode == 4)?"class='active'":""}>
		  <a href="list.do?mode=4">새로운 메시지</a></li>
		</ul>
		<div>
			<table class="table">
				<tr>
					<th>번호</th>
					<th>보낸사람(아이디)</th>
					<th>보낸날짜</th>
					<th>받는사람(아이디)</th>
					<th>받은날짜</th>
				</tr>
				<c:if test="${empty list}">
					<tr>
						<td colspan="5">메시지가 존재하지 않습니다.</td>
					</tr>
				</c:if>
				<c:if test="${!empty list}">
					<c:forEach items="${list}" var="vo">
						<tr class="dataRow ${(empty vo.acceptDate)?'noRead':''}">
							<td class="no">${vo.no}</td>
							<td>${vo.senderName}(${vo.sender})</td>
							<td>${vo.sendDate}</td>
							<td>${vo.accepterName}(${vo.accepter})</td>
							<td>${(empty vo.acceptDate?"읽지 않음":vo.acceptDate)}</td>
						</tr>
					</c:forEach>
					<!-- acceptDate가 empty = null일때 읽지않음 -->
				</c:if>
			</table>
			<div>
				<a href="wirteForm.do" class="btn btn-success">메시지 보내기</a>
			</div>
			<div class="page">
				<pageObject:pageNav listURI="list.do" pageObject="${pageObject}"/>
			</div>
		</div>
	</div>
</body>
</html>