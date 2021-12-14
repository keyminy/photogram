<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="pageObject" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>이미지 게시판 리스트</title>
<link rel="stylesheet" href="/resources/css/image/imagelist.css">
<script type="text/javascript">
	$(function(){
		<c:if test="${!empty msg}">
			//처리된 결과를 보여주는 경고창 작성. - 이미지 등록/이미지 삭제 후 메시지
			alert("${msg}");
		</c:if>
		$(".dataRow").click(function(){
			var no = $(this).find(".no").text();
			location = "view.do?no=" + no + "&page=${pageObject.page}&perPageNum=${pageObject.perPageNum}";
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
		<h3>이미지 게시판 리스트</h3>
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
		<!-- line문장은 공백(위의 내용과 틈을 빌리는 빈줄) -->
		<div class="line"></div>
		<div class="row">
			<c:forEach items="${list}" var="vo" varStatus="vs">
			<!-- 인덱스 번호가 0이상이고 4의 배수이면 한줄(div class="row")을 만든다.  -->
<%-- 				${(vs.index!=0 && (vs.index % 4 ==0))?"</div><div class='row'>":""} --%>
				<div class="col-md-3 dataRow">
<!-- 				<div class="dataRow"> -->
					<div class="thumbnail">
						<img src="${vo.fileName}" style="width: 100%; height: 350px;">
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
		<c:if test="${!empty login}">
			<div>
				<a href="write.do?perPageNum=${pageObject.perPageNum }" class="btn btn-default">등록</a>
			</div>
		</c:if>
		<c:if test="${empty login}">
			<div>
				<a href="javascript:void(0)" title="로그인을 하셔야 글 등록이 가능합니다."
				class="btn btn-default" disabled="disabled">등록</a>
			</div>
		</c:if>
		<div class="page">
			<pageObject:pageNav listURI="list.do" pageObject="${pageObject}"/>
		</div>
	</div>
</body>
</html>