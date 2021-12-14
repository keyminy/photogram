<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글보기</title>
<link rel="stylesheet" href="/resources/css/board/boardview.css" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script type="text/javascript" src="/resources/js/util.js"></script>
<script type="text/javascript" src="/resources/js/reply.js"></script>
<script type="text/javascript">
	$(function(){
		//msg 뿌려주기
		${(empty msg)?"":"alert('"+= msg +="');"};
				
		//모달창 뛰워서 글 삭제하기
		$("#modal_deleteBtn").click(function(){
			$("#modal_deleteForm").submit();
		});
		//댓글 리스트 JSON형태로 가져오기 : replyService.list()
// 		replyService.list({no : ${vo.no}},function(list){
// 			console.log(JSON.stringify(list));
// 		});
		//글번호,페이지 정보
		var no = ${vo.no};
		var repPage=1;
		var repPerPageNum=5;
		
		showList();//게시글볼때 처음 댓글 보여야하므로 호출
		
		//[1]댓글 리스트를 보여주는 showList() 함수
		//호출 하는 경우 - 게시글볼때 처음 댓글 보여야함, 댓글 등록,수정,삭제 시 다시 호출해야함.
		function showList(){
			//1.데이터 가져오기.
			replyService.list({no:no,repPage:repPage,repPerPageNum:repPerPageNum},
					function(data){
						var list = data.list;
						//2.태그만들기
						var str="";
						if(list == null || list.length ==0){
							str += "<li class='list-group-item'>댓글이 존재하지 않습니다.</li>";
						}else{
							for(let i = 0; i < list.length; i++){
								str += "<li class='left clearfix list-group-item' data-rno='"+list[i].rno+"'>";
								str += "<div>";
								str += "<div class='header'>";
								str += "<strong class='primary-font replyWriterData'>"+list[i].writer+"</strong>";
								str += "<small class='pull-right text-muted'>"+displayDateTime(list[i].writeDate)+"</small>";
								str += "</div>";
								str += "<div>"
								str += "<p class='replyContentData'>"+list[i].content+"</p>";
								str += "<div class='text-right'>";
    						str += "<button class='btn btn-default btn-xs replyUpdateBtn'>수정</button>";	
    						str += "<button class='btn btn-default btn-xs replyDeleteBtn'>삭제</button>";	
    						str += "</div>";
    						str += "</div>";
								str += "</li>";
							}
						}
						//3.원하는 곳(ul의 chat)에 태그를 넣는다.
						$(".chat").html(str);
						//4.댓글 페이지 네이션 표시
						var pageObject = data.pageObject;
						$("#reply_nav").html(ajaxPage(pageObject));
					});
		}//end showList()
		
		//[2]댓글 쓰기 부분
		$("#writeReplyBtn").click(function(){
			var form= document.replyWriteForm; //form의 name으로 접근
			//form.submit();
			var reply={};
			reply.content = form.content.value;
			reply.pw = form.pw.value;
			reply.writer = form.writer.value;
			reply.no = form.no.value;
			if(!reply.content || !reply.pw || !reply.writer){
				alert("내용과 비밀번호 작성자는 필수 입력사항입니다.")
				return false;
			}else{
				replyService.write(reply,
						function(result){
							alert(result); //Controller로부터 "댓글 등록 성공하였습니다."
							form.content.value = "";
							form.pw.value = "";
							form.writer.value = "";
							//작성 완료 된 후 댓글 리스트 다시 뿌리기
							showList(); 
						}
				);
			}
		});//end writeReplyBtn.click()
		
		//[3-1]댓글 수정 폼 : 모달(replyModal)을 보이게 하기
		$(".chat").on("click",".replyUpdateBtn",function(){
			//1.모달창 제목 update라고 바꾸기
			$("#replyModalTitle").text("Reply Update");
			
			//2.푸터 삭제 버튼 안보이게 + readonly풀기
			$("#replyContent,#replyWriter").attr("readonly",false);
			var footer = $("#replyModal .modal-footer");
			footer.find("button").show();
			footer.find("#replyModalDeleteBtn").hide();
			
			//3.데이터 수집
			var li = $(this).closest("li");
			var rno = li.data("rno");
			var content = li.find(".replyContentData").text();
			var writer = li.find(".replyWriterData").text();
			//4.데이터 셋팅
			$("#replyRno").val(rno);
			$("#replyContent").val(content);
			$("#replyWriter").val(writer);
			//데이터 셋팅 후 비밀번호 지우기
			$("#replyPw").val("");
			//5.모달창 뛰우기
			$("#replyModal").modal("show");
		});//end replyUpdateBtn.click()
		
		//[3-2]모달창 댓글 수정 버튼 이벤트 처리
		$("#replyModalUpdateBtn").click(function(){
			//1.데이터 수집.
			var reply ={};
			reply.rno = $("#replyRno").val();
			reply.content = $("#replyContent").val();
			reply.writer = $("#replyWriter").val();
			reply.pw = $("#replyPw").val();
			//2.reply.js안에 있는 replyService.update를 호출해서 실행
			if(!reply.content || !reply.pw || !reply.writer){
				alert("내용과 비밀번호 작성자는 필수 입력사항입니다.")
				return false;
			}else{
				replyService.update(reply,
					function(result,status){
					if(status=="notmodified"){
						alert("수정이 되지 않았습니다. 정보를 확인해 주세요.");
					}else{
						alert(result);
						// 리스트를 다시 표시한다. showList()호출하면됨.
						showList();
						}
				},
				function(err,status){
					alert("실패" + status);
					alert(err);//실패 메시지 출력
				}
			);
			$("#replyModal").modal("hide");
			}
		});
		
		//[4-1]댓글 삭제 폼 : 모달(replyModal)
		$(".chat").on("click",".replyDeleteBtn",function(){
			//1.모달창 제목 Delete라고 바꾸기
			$("#replyModalTitle").text("Reply Delete");
			
			//2.푸터 수정 버튼 안보이게 + 내용 변경불가하게 readonly
			$("#replyContent,#replyWriter").attr("readonly",true);
			var footer = $("#replyModal .modal-footer");
			footer.find("button").show();
			footer.find("#replyModalUpdateBtn").hide();
			//3.데이터 수집
			var li = $(this).closest("li");
			var rno = li.data("rno");
			var content = li.find(".replyContentData").text();
			var writer = li.find(".replyWriterData").text();
			//4.데이터 셋팅
			$("#replyRno").val(rno);
			$("#replyContent").val(content);
			$("#replyWriter").val(writer);
			//데이터 셋팅 후 비밀번호 지우기
			$("#replyPw").val("");
			
			$("#replyModal").modal("show");
		});
		
		//[4-2]모달창 댓글 삭제 버튼 이벤트 처리
		$("#replyModalDeleteBtn").click(function(){
			//1.데이터 수집.
			var reply ={};
			reply.rno = $("#replyRno").val();
			reply.pw = $("#replyPw").val();
			reply.no = $("#replyNo").val();
			//2.reply.js안에 있는 replyService.deleteReply를 호출해서 실행
			if(!reply.pw){
				alert("비밀번호는 필수 입력사항입니다.")
				return false;
			}else{
			replyService.delete(reply,
					function(result,status){
						if(status=="notmodified"){
							alert("댓글 삭제가 되지 않았습니다. 비밀번호를 확인하세요");
						}else{
							alert(result);
							//삭제 완료 된 후 댓글 리스트 다시 뿌리기
							showList();
						}
				},
				function(err){
					alert(err);
					alert("상태 : " + status);
				});
			$("#replyModal").modal("hide");
			}
		});
		//댓글 페이지네이션 클릭 이벤트 - 태그가 나중에 나오므로 on 사용해야함.
		$("#reply_nav").on("click",".reply_nav_li",
				function(){
					//alert("댓글 페이지 네이션 클릭");
					if($(this).find("a").hasClass("move")){
						repPage = $(this).data("page");
						//alert(repPage + " 페이지로 이동");
						showList();
					}else{
						//alert("이동 불가");
					}
					return false; //a태그의 페이지 이동 막기
		});
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
					${vo.writer}
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
			<a href="update.do?no=${vo.no}&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}&key=${pageObject.key}&word=${pageObject.word}"
			 class="btn btn-default">수정</a>
			<a class="btn btn-default" onclick="return false;"
			data-toggle="modal" data-target="#myModal">삭제</a>
			<a href="list.do?page=${pageObject.page}&perPageNum=${pageObject.perPageNum}&key=${pageObject.key}&word=${pageObject.word}" 
			class="btn btn-default">리스트</a>
		</div>
		<!-- 댓글 영역 -->
		<div class="row" style="margin-top: 15px;">
			<div class="col-lg-12">
				<!-- panel -->
				<div class="panel panel-default">
				  <div class="panel-heading">
				  	<i class="fa fa-comments fa-fw"></i> Reply
				  </div>
				  <div class="panel-body">
				  	<!-- 댓글 작성란 폼 -->
				  	<form action="" class="form-inline" onSubmit="return false;"
				  	name="replyWriteForm" method="post">
				  		<input type="hidden" name="no" value="${vo.no}" />
				  		<div class="form-group">
						    <label for="writer">작성자:</label>
						    <input type="text" class="form-control" name="writer" id="writer" required="required">
						  </div>
						  <div class="form-group">
						    <label for="pw">비밀번호:</label>
						    <input type="password" class="form-control" name="pw" id="pw" required="required">
						  </div>
						  <br /><br />
							<div class="form-group">
								<label for="content">내용:</label> 
								<textarea class="form-control" rows="3" cols="70" name="content" id="content" required="required"></textarea>
							</div>
							<div class="Btns" style="margin-top: 20px;">
					  		<input type="button" id="writeReplyBtn" class="btn btn-primary" value="등록"></button>
					  		<button type="reset" class="btn btn-default">취소</button>
					  	</div>
				  	</form>
				  </div>
				  <hr />
				  <div class="panel-body">
				  	<!-- 댓글 리스트 뿌려지는 부분 -->
				  	<ul class="chat list-group">
				  		<li class="left clearfix list-group-item" data-rno='12'>
				  			<div>
				  				<div class="header">
				  					<strong class="primary-font">작성자</strong>
				  					<small class="pull-right text-muted">작성일</small>
				  				</div>
				  			</div>
				  			<p>댓글 내용</p>
				  			<div class="text-right">
	    							<button class="btn btn-default btn-xs replyUpdateBtn">수정</button>
	    							<button class="btn btn-default btn-xs replyDeleteBtn">삭제</button>
    						</div>
				  		</li>
				  	</ul>
				  </div><!-- end panel-body -->
				  <div class="panel-footer">
			  		<ul class="pagination" id="reply_nav">
						  <li><a href="">1</a></li>
						  <li class="active"><a href="">2</a></li>
						</ul><!-- JS에서 a태그 안먹게 코드짜야함. -->
				  </div> <!-- end panel-footer -->
				</div>
			</div>
		</div>
	</div><!-- end container -->
	
	<!-- Board글삭제 Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title">게시판 글삭제 비밀번호 입력</h4>
        </div>
        <div class="modal-body">
          <form action="delete.do" method="post" id="modal_deleteForm">
          	<input type="hidden" name="no" value="${vo.no}" />
          	<input type="hidden" name="perPageNum" value="${pageObject.perPageNum}"/>
          	<div class="form-group">
          		<label for="pw">비밀번호 : </label>
          		<input type="password" name="pw" id="pw" class="form-control"
          		 pattern="[^가-힣ㄱ-ㅎ]{4,20}" required="required"
			      	 title="4-20자.한글은 입력할 수 없습니다."/>
          	</div>
          </form>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-default" id="modal_deleteBtn">삭제</button>
          <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
        </div>
      </div>
    </div>
  </div>
  <!-- Modal 수정,삭제 창 -->
<div id="replyModal" class="modal fade" role="dialog">
  <div class="modal-dialog">
    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">
        	<i class="fa fa-comments fa-fw"></i>
       		<span id="replyModalTitle">Reply Modal</span>
        </h4>
      </div>
      <div class="modal-body">
        <form action="">
        	<!-- 댓글의 번호(rno),게시물번호(no) : hidden -->
        	<input type="hidden" name="rno" id="replyRno"/>
        	<input type="hidden" name="no" id="replyNo" value="${vo.no}"/>
      		<div class="form-group">
		     		<label for="replyContent">내용 : </label>
		     		<textarea name="content" id="replyContent" rows="5" class="form-control" required="required"></textarea>
	       	</div> 
        	<div class="form-group">
        		<label for="replyWriter">작성자</label>
        		<input type="text" class="form-control" id="replyWriter" required="required"/>
        	</div>
       		<div class="form-group">
	       		<label for="replyPw">비밀번호 : </label>
	       		<input type="password" name="pw" class="form-control" id="replyPw" 
	       		required="required" pattern=".{4,20}"/>
	       	</div> 
        </form>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" id="replyModalUpdateBtn">수정</button>
        <button type="button" class="btn btn-default" id="replyModalDeleteBtn">삭제</button>
        <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
      </div>
    </div>
  </div>
</div>
</body>
</html>