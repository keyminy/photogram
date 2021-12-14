<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<link rel="stylesheet" href="/resources/css/main/main.css">
<script type="text/javascript">
	$(function(){
			// 처리후 나타나는 메시지 : 글쓰기나 삭제 후 리스트로 돌아오면 보여준다.
			${(empty msg)?"":"alert('" += msg += "');"}
			//board 제목 클릭시 view로 가기
			$(".dataRow").click(function(){
				var no = $(this).find(".no").text();
				var query = ${(empty pageObject)?"''":" '&page="+=pageObject.page
						+="&perPageNum="+=pageObject.perPageNum+="' "};
				query += ${(empty pageObject.word)?"''":" '&key="+=pageObject.key
						+="&word="+=pageObject.word+="' "};
				location = "/board/view.do?no="+no+query;
			});
			//notice제목 클릭시 view로 가기
			$(".notice_title").click(function(){
				var no = $(this).data('no');//text() : 글자가져오기
				var query = ${(empty pageObject)?"''":" '&page="+=pageObject.page
						+="&perPageNum="+=pageObject.perPageNum+="' "};
				query += ${(empty pageObject.word)?"''":" '&key="+=pageObject.key
						+="&word="+=pageObject.word+="' "};
				var location = "/notice/view.do?no="+no+query;
				$(".notice_title").attr("href", location);
			})
			//image 제목 클릭시 view로 가기
			$(".imgdataRow").click(function(){
				var no = $(this).find(".no").text();
				location = "/image/view.do?no=" + no;
			});
	});
</script>
</head>
<body>
	<div class="container">
		<h1>Go Travel 사이트에 오신것을 환영합니다!</h1>
	<div class="row">
		<div class="col-md-6">
			<div class="panel panel-warning">
	      <div class="panel-heading"><a href="/notice/list.do">공지사항 가기</a></div>
	      <div class="panel-body">
	      	<!-- noticeList 출력부분 -->
					<table class="table">
			    <thead>
			      <tr>
			        <th>no</th>
			        <th style="text-align: center;">제목</th>
			        <th>공지시작일</th>
			        <th>공지종료일</th>
			        <th>최종수정일</th>
			      </tr>
			    </thead>
			    <tbody>
			    	<!--list 데이터가 없는 경우 표시 -->
			      <c:if test="${empty noticeList}">
			      	<tr>
			      		<td>데이터가 존재하지 않습니다..</td>
			      	</tr>
			      </c:if>
			      <!-- list 데이터가 있을때 출력 -->
			      <c:if test="${!empty noticeList}">
			      	<c:forEach items="${noticeList}" var="vo">
			      		<tr class="RowHover">
			      			<td>${vo.no}</td>
			      			<td>
			      				<a href="javascript:void(0);" class="notice_title" data-no="${vo.no}">
			      					${vo.title}
			      				</a>
		      				</td>
			      			<td>${vo.startDate}</td>
			      			<td>${vo.endDate}</td>
			      			<td>${vo.updateDate}</td>
			      		</tr>
			      	</c:forEach>
			      </c:if>			     
			    </tbody>
			  </table>
	      </div>
	    </div>
    </div>
    <div class="col-md-6">
	 	  <div class="panel panel-info">
		    <div class="panel-heading"><a href="/board/list.do">자유게시판 가기</a></div>
		    <div class="panel-body">
					<!-- boardlist 출력부분 -->
						<!-- 데이터가 없는 경우의 표시 -->
						<ul class="list-group">
						<c:if test="${empty boardList}">
							<li class="list-group-item">
								데이터가 존재하지 않습니다.
							</li>
						</c:if>
							<!-- 데이터가 있는 경우의 표시 -->
						<c:if test="${!empty boardList}">
							<c:forEach items="${boardList}" var="vo">
								<li class="list-group-item dataRow">
										<div>
											<span class="no">${vo.no}</span>.
											${vo.title} <span style="color:tomato;">${(vo.replyCnt==0)?"":[vo.replyCnt]}</span>
										</div>
										${vo.writer}
										(<fmt:formatDate value="${vo.writeDate}"/>)
										<span class="badge">${vo.hit}</span>
									</li>
								</c:forEach>
							</c:if>
						</ul>
	    </div>
	  </div> 
	 </div> <!-- end 자유게시판 -->
 </div>
  	 <div class="panel panel-success">
	    <div class="panel-heading"><a href="/image/list.do">이미지 게시판 가기</a></div>
	    <div class="panel-body">
				<div class="row">
					<c:forEach items="${imageList}" var="vo" varStatus="vs">
					<!-- 인덱스 번호가 0이상이고 4의 배수이면 한줄(div class="row")을 만든다.  -->
						${(vs.index!=0 && (vs.index % 4 ==0))?"</div><div class='row'>":""}
						<div class="col-md-3 imgdataRow">
		<!-- 				<div class="dataRow"> -->
							<div class="thumbnail">
								<img src="${vo.fileName}" style="width: 100%; height: 200px;">
									<div class="caption">
										<p class="title">[<span class="no">${vo.no}</span>] ${vo.title}</p>
									</div>
									<div>${vo.name}(${vo.id}) 
										<span class="pull-right"><fmt:formatDate value="${vo.writeDate}" pattern="yyyy.MM.dd"/></span>
									</div>
							</div>
						</div>
					</c:forEach>
				</div>
	    </div>
    </div>
</div>
</body>
</html>