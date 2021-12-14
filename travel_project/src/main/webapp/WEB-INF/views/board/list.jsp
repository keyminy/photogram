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
<link rel="stylesheet" href="/resources/css/board/boardlist.css">
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
		<h2>여행 자유게시판</h2>
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
			      			<td>
			      				<a href="javascript:void(0);" class="title" data-no="${vo.no}">
			      					${vo.title}
			      				</a>
			      				<span style="color:tomato;">${(vo.replyCnt==0)?"":[vo.replyCnt]}</span>
		      				</td>
			      			<td>${vo.writer}</td>
			      			<td><fmt:formatDate value="${vo.writeDate}" pattern="yyyy-MM-dd"/></td>
			      			<td>${vo.hit}</td>
			      		</tr>
			      	</c:forEach>
			      </c:if>			     
			    </tbody>
			  </table>
  		</div>
  		<div class="Btns">
  			<form>
  				<input type="hidden" name="page" value="1"/> <!-- 검색을 하면 새로운 페이지인 1페이지로 감 -->
  				<input type="hidden" name="perPageNum" value="${pageObject.perPageNum}"/>
				  <div class="input-group search_bar">
				  	<span class="input-group-addon">
				  		<select name="key">
				  			<option value="t" ${(pageObject.key == "t")?"selected":""}>제목</option>
				  			<option value="c" ${(pageObject.key == "c")?"selected":""}>내용</option>
				  			<option value="w" ${(pageObject.key == "w")?"selected":""}>작성자</option>
				  			<option value="tc" ${(pageObject.key == "tc")?"selected":""}>제목/내용</option>
				  			<option value="tw" ${(pageObject.key == "tw")?"selected":""}>제목/작성자</option>
				  			<option value="wc" ${(pageObject.key == "wc")?"selected":""}>작성자/내용</option>
				  			<option value="tcw" ${(pageObject.key == "tcw")?"selected":""}>모두</option>
				  		</select>
				  	</span>
				    <input type="text" class="form-control" placeholder="Search" name="word" value="${pageObject.word}">
				    <div class="input-group-btn">
				      <button class="btn btn-default" type="submit">
				        <i class="glyphicon glyphicon-search"></i>
				      </button>
				    </div>
				  </div>
				</form>
  			<!-- write할때 perPageNum은 유지되어야한다. -->
 				<a href="write.do?perPageNum=${pageObject.perPageNum}" class="btn btn-default writeBtn">글쓰기</a>
		</div>
			<div class="page">
				<pageObject:pageNav listURI="list.do" pageObject="${pageObject}"
				query="&key=${pageObject.key}&word=${pageObject.word}"/>
			</div>
	</div>
</div>
</body>
</html>