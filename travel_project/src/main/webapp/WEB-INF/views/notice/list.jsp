<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="pageObject" tagdir="/WEB-INF/tags" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판</title>
<style>
	th,td{
	text-align: center;
}
.RowHover:hover{
	/*table row에 손올리면 회색바탕으로*/
	background: #eee;
}
.page{
	display: flex;
	justify-content:center;
}
</style>
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
			})
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
		<h2>Go Travel 공지게시판</h2>
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
			        <th>공지번호</th>
			        <th>제목</th>
			        <th>공지시작일</th>
			        <th>공지종료일</th>
			        <th>최종수정일</th>
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
			      			<td>
			      				<a href="javascript:void(0);" class="title" data-no="${vo.no}">
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
  		<c:if test="${login.id == 'admin'}">
	 			<!-- write할때 perPageNum은 유지되어야한다. -->
				<a href="write.do?perPageNum=${pageObject.perPageNum}" class="btn btn-default writeBtn">글쓰기</a>
			</c:if>
			<div class="page">
				<pageObject:pageNav listURI="list.do" pageObject="${pageObject}"/>
			</div>
	</div>
</div>
</body>
</html>