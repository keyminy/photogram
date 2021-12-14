<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>메시지 보기</title>
<script type="text/javascript">
$(function(){
	${(empty msg)?"":"alert('"+= msg +="');"};
	//모달창 뛰워서 글 삭제하기
	$("#modal_deleteBtn").click(function(){
		$("#modal_deleteForm").submit();
	});
});
</script>
</head>
<body>
	<div class="container">
		<h1>메시지 보기</h1>
		<ul class="list-group">
		  <li class="list-group-item">
		  	<label>번호 : </label> <span>${vo.no}</span>
		  </li>
		  <li class="list-group-item">
		  	<label>보낸 분 : </label> <span>${vo.senderName}(${vo.sender })</span>
		  </li>
		  <li class="list-group-item">
		  	<label>보낸 날짜 : </label> <span>${vo.sendDate }</span>
		  </li>
		  <li class="list-group-item">
		  	<label>받는 분 : </label> <span>${vo.accepterName}(${vo.accepter })</span>
		  </li>
		  <li class="list-group-item">
		  	<label>받은 날짜 : </label> <span>${(empty vo.acceptDate?"읽지 않음":vo.acceptDate)}</span>
		  </li>
		  <li class="list-group-item">
		  	<label>내용 : </label> <span>${vo.content }</span>
		  </li>
		</ul>
		<div class="btnGroup">
			<a href="list.do?page=${pageObject.page}&perPageNum=${pageObject.perPageNum}${(empty param.mode)?'':'&mode='+=param.mode+=''}" 
			class="btn btn-info">메시지 보기</a>
			<a class="btn btn-danger" onclick="return false;"
			data-toggle="modal" data-target="#myModal">삭제</a>
		</div>
	</div>
		<!-- Message 글삭제 Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">메시지 삭제 </h4>
        </div>
        <div class="modal-body">
        	정말로 삭제하시겠습니까??
          <form action="delete.do" method="post" id="modal_deleteForm">
          	<input type="hidden" name="no" value="${vo.no}" />
          	<input type="hidden" name="perPageNum" value="${pageObject.perPageNum}"/>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" id="modal_deleteBtn">삭제</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
        </div>
      </div>
    </div>
  </div>
</body>
</html>