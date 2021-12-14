<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="pageObject" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>qna게시판</title>
<link rel="stylesheet" href="/resources/css/qna/qnalist.css">
<link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
<script type="text/javascript">
	$(function(){
			// 처리후 나타나는 메시지 : 글쓰기나 삭제 후 리스트로 돌아오면 보여준다.
			${(empty msg)?"":"alert('" += msg += "');"}
			//제목 클릭시 view로 가기
			$(".title").click(function(){
				var no = $(this).data('no');//text() : 글자가져오기
				var query = ${(empty pageObject)?"''":" '&page="+=pageObject.page
						+="&perPageNum="+=pageObject.perPageNum+="' "};
				query += ${(empty pageObject.word)?"''":" '&key="+=pageObject.key
						+="&word="+=pageObject.word+="' "};
				var location = "view.do?no="+no+query;
				$(".title").attr("href", location);
			});
			// 한페이지에 보여주는 데이터 선택의 이벤트 처리 -> 변경이 일어나면 처리
			$("#sel_perPageNum").change(function(){
				// alert("값 변경");
				// 다시 리스트 불러오기 - 전달 정보는 페이지:1, perPageNum을 선택된 값을 전달.
				location = "list.do?page=1&perPageNum=" + $(this).val();
			});
	});
</script>
</head>
<body>
	<div class="container">
		<h2>Q&amp;A게시판</h2>
		<div style="padding: 10px; border-bottom: 2px solid #eee; height: 55px;">
			<div class="pull-right form-inline sel_perPageNum_div">
				<label>한 페이지에 표시할 데이터</label>
				<select class="form-control" id="sel_perPageNum">
					<option ${(pageObject.perPageNum == 4)?"selected":"" }>4</option>
					<option ${(pageObject.perPageNum == 8)?"selected":"" }>8</option>
					<option ${(pageObject.perPageNum == 12)?"selected":"" }>12</option>
					<option ${(pageObject.perPageNum == 16)?"selected":"" }>16</option>
				</select>
			</div>
		</div>
		<div class="panel-body">
			 <div class="table-responsive">          
			  <table class="table">
			    <thead>
			      <tr>
			        <th>글번호</th>
			        <th>제목</th>
			        <th>글쓴이</th>
			        <th>작성일</th>
			        <th>조회수</th>
			      </tr>
			    </thead>
			    <tbody>
			    	<!--list 데이터가 없는 경우 표시 -->
			      <c:if test="${empty list}">
			      	<tr>
			      		<td>데이터가 존재하지 않습니다..</td>
			      	</tr>
			      </c:if>
			      <!-- list 데이터가 있을때 출력 -->
			      <c:if test="${!empty list}">
			      	<c:forEach items="${list}" var="vo">
			      		<tr class="RowHover">
			      			<td>${vo.no}</td>
			      			<td class="td_title">
			      				<a href="javascript:void(0);" class="title" data-no="${vo.no}">
				      				<c:forEach begin="1" end="${vo.levNo}">&nbsp;&nbsp;&nbsp;&nbsp;</c:forEach>
				      					<c:if test="${vo.levNo>0}">
				      						<i class="material-icons">subdirectory_arrow_right</i>
				      					</c:if>${vo.title}
			      				</a>
		      				</td>
			      			<td>${vo.id}</td>
			      			<td><fmt:formatDate value="${vo.writeDate}" pattern="yyyy-MM-dd"/></td>
			      			<td>${vo.hit}</td>
			      		</tr>
			      	</c:forEach>
			      </c:if>			     
			    </tbody>
			  </table>
  		</div>
  		<c:if test="${!empty login}">
				<a href="questionForm.do?perPageNum=${pageObject.perPageNum}" class="btn btn-default writeBtn">질문하기</a>
  		</c:if>
  		<c:if test="${empty login}">
				<a href="javascript:void(0)" title="로그인을 하셔야 질문이 가능합니다." 
				class="btn btn-default writeBtn"  disabled="disabled">질문하기</a>
  		</c:if>
			<div class="page">
				<pageObject:pageNav listURI="list.do" pageObject="${pageObject}"/>
			</div>
	</div>
</div>
</body>
</html>