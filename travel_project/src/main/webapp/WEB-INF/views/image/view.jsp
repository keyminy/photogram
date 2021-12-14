<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 게시판 보기</title>
<link rel="stylesheet" href="/resources/css/image/imageview.css">
<script type="text/javascript">
function dolike(){
	var id = "${login.id}";
	var no = "${vo.no}";
	var str = "";
	var like_img = "";
	$.ajax({
    	url : "/imageAjax/like.do",
    	type : "GET",
    	data : {"id":id,"no":no},
    	success : function(data){
				str += "좋아요 수 : " + data.likeCnt;
				$(".like_count").html(str);
   			if(data.myLiked!=null){
   				like_img = "/resources/img/좋아요_취소.png";
   				$("#like_a").attr("href", "javascript: likeCancle();");
   				$("#like_a").attr("id", "dislike_a");
   			}
				$(".like img").attr("src",like_img);
				$(".like img").attr("id","dislike_img");
    	},//end success
    	error : function() {alert("error");}
    });
}
function likeCancle(){
	var id = "${login.id}";
	var no = "${vo.no}";
	var str="";
	var like_img = "";
	$.ajax({
    	url : "/imageAjax/likeCancle.do",
    	type : "GET",
    	data : {"id":id,"no":no},
    	success : function(data){
				str += "좋아요 수 : " + data.likeCnt;
				$(".like_count").html(str);
   			if(data.myLiked==null){
   				like_img = "/resources/img/좋아요.png";
   				$("#dislike_a").attr("href", "javascript: dolike();");
   				$("#dislike_a").attr("id", "like_a");
   			}
   			$(".like img").attr("src",like_img);
   			$(".like img").attr("id","like_img");
    	},//end success
    	error : function() {alert("error");}
    });
}
$(function(){
	<c:if test="${!empty msg}">
		//처리된 결과를 보여주는 경고창 작성. - 이미지 등록/이미지 삭제 후 메시지
		alert("${msg}");
	</c:if>
	$("#deleteBtn").click(function(){
		if(!confirm("정말 삭제하시겠습니까?")){
			//취소를 누른경우
			return false;
		}
	});
	//tooltip처리
	 $('[data-toggle="tooltip"]').tooltip();   
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
			</li>
			 <li class="list-group-item row"> 
		  	<div class="content">
		  		<img src="${vo.fileName}" class="img-thumbnail" />
		  		<pre>${vo.content}</pre>
		  	</div>
		  	<!-- 좋아요 표시 처리 -->
		    <div class="like_div">
			  	 <div class="text-center like">
			  	   <c:if test="${empty login}">
			  	   	<!-- 로그인을 하지 않은 경우 - 좋아요 선택,취소 불가(회색버튼) -->
			  	   		<img src="/resources/img/좋아요_myLiked.png" data-toggle="tooltip"
			  	   		title="로그인을 하셔야 좋아요를 하실 수 있습니다."/>
			  	   </c:if>
			  	   <c:if test="${!empty login}">
			  	   	 <c:if test="${empty vo.myLiked}">
			  	   	 		<!-- myLiked가 비어있으면 좋아요 눌러서 갯수 올릴 수 있다. -->
			  	   	 		<a href='javascript: dolike();' id="like_a">
						  	   <img src="/resources/img/좋아요.png" id="like_img"/>
			  	   			</a>
				  	   </c:if>
			  	   	 <c:if test="${!empty vo.myLiked}">
			  	   	 	 <!-- myLiked가 비어있지 않으면 눌러서 좋아요 취소 할 수있다. -->
			  	   	 	 <a href='javascript: likeCancle();' id="dislike_a">
						  	   <img src="/resources/img/좋아요_취소.png" id="dislike_img" />
			  	   		</a>
				  	   </c:if>
				  	 </c:if>
			  	 </div>
			  	 <div class="text-center like_count">
			  	 	좋아요 수 : ${vo.likeCnt}
			  	 </div>
			  </div>
		  </li>
		</ul>
		<div class="btnGroup">
			<!-- 이미지 바꾸기 : 모달창 사용해서 post방식으로 넘기기 -->
			<!-- no와,삭제할 파일정보, 새로 바꿀 파일정보 3개가 넘어가야함 -->
			<c:if test="${vo.id == login.id || login.gradeNo==9}">
				<button type="button" class="btn btn-success" data-toggle="modal" data-target="#myModal">이미지 수정</button>
				<a href="update.do?no=${vo.no}&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}"
				 class="btn btn-success">글 내용 수정(제목,내용)</a>
				<a href="delete.do?no=${vo.no}&perPageNum=${pageObject.perPageNum}&deleteFileName=${vo.fileName}" 
				class="btn btn-danger" id ="deleteBtn">삭제</a>
			</c:if>
			<a href="list.do?page=${pageObject.page}&perPageNum=${pageObject.perPageNum}" 
			class="btn btn-info">리스트</a>
		</div>
	</div>
<!-- Modal -->
<div id="myModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
  	<form action="changeImage.do" method="post" enctype="multipart/form-data">
	  	<!-- hideen 데이터 : page,perPageNum,deleteFileName-->
	  	<input type="hidden" name="page" value="${pageObject.page}"/>
	  	<input type="hidden" name="perPageNum" value="${pageObject.perPageNum}"/>
	  	<input type="hidden" name="deleteFileName" value="${vo.fileName}"/> <!-- 원래파일인 vo.fileName -->
	    <!-- Modal content-->
	    <div class="modal-content">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal">&times;</button>
	        <h4 class="modal-title">사진 바꾸기</h4>
	      </div>
	      <div class="modal-body">
	        <p>
	        	<div class="form-group">
							<label for="no">글번호</label>
							<input name="no" id="no" type="text" value="${vo.no}"
							class="form-control" readonly="readonly"/>
						</div>
	        	<div class="form-group">
							<label for="imageFile">이미지 파일</label>
							<input name="multipartFile" id="imageFile" type="file" class="form-control" required="required"/>
						</div>
	        </p>
	      </div>
	      <div class="modal-footer">
	        <button class="btn btn-default">이미지 수정</button>
	        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
	      </div>
	    </div>
    </form>
  </div>
</div>
</body>
</html>