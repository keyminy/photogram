<!-- sitemesh 사용을 위한 설정 파일 -->

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="decorator"
	uri="http://www.opensymphony.com/sitemesh/decorator"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
System.out.println("default_decorator.do [path] : " + request.getContextPath());
request.setAttribute("path", request.getContextPath());
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Go Travel:<decorator:title /></title>
<!-- BootStrap 라이브러리 등록 전체적으로 진행을 했다. filter가 적용이 되면 개별적으로 한것은 다 지워야 한다. -->
 <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0" />
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
<script type="text/javascript">
$(function(){
	<c:if test="${!empty login}">
		$("#newMsgCnt").load("/messageAjax/msgCount.do"); 
		//새로운 메시지와도 바뀌지 않고 한번만 실행됨
		//5초마다 한번 씩 새로운 메시지를 불러오는 처리하는 메서드
		setInterval(function(){ 
			$("#newMsgCnt").load("/messageAjax/msgCount.do");
		}, 5000);
	</c:if>
});
</script>
	<!-- reset css -->
<link rel="stylesheet" href="/resources/css/reset.css">
<style type="text/css">
@import url('https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap');
/* header, footer { 
 	background: AntiqueWhite; 
 } */
 
pre {
	background: white;
	border: 0px; /*테두리 없애기*/
}

/* Remove the navbar's default margin-bottom and rounded borders */
.navbar {
	margin-bottom: 0;
	border-radius: 0;
	background-color: #07c; /*헤더,푸터 배경색 셋팅*/
}

.navbar-brand.logo h1{
	font-family: 'Merienda', cursive;
	font-size: 20px;
	font-weight: 700;
	color:#fff;
	text-shadow: 0 -1px 0 #222;
}

/* .nav_bar li{ */
/* 	margin-left: 250px; */
/* } */

.a_link{
	color:#fff !important;
}
.a_link:hover{
	color:#000 !important;
	text-shadow: 0 -1px #07c;
}

/* Add asome padding to the footer */
footer {
	padding: 10px;
	color: #fff;
}
/*
.navbar-fixed-bottom{
	position: static;
}*/

.carousel-inner img {
	width: 100%; /* Set width to 100% */
	margin: auto;
	min-height: 200px;
}

/* Hide the carousel text when the screen is less than 600 pixels wide */
@media ( max-width : 600px) {
	.carousel-caption {
		display: none;
	}
/* 	#log_image{ */
/*  		display: none;  */
/* 	} */
}

article {
	min-height: 400px;
	margin-top: 80px; /* 메뉴부분만큼의 마진 적용 - 데이터가 메뉴에 가리는 것은 해결 */
	margin-bottom: 100px; /* copyRight 부분의 마진 적용 - 데이터가 copyRight에 가리는 것은 해결 */
}

#welcome {
	color: grey;
	margin: 0 auto;
}
/*font awesome*/
@import url('https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css');
/*xeicon*/
@import 
url('http://cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css');
/*google font Noto Sans*/
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100;300;400;500;700;900&display=swap');
/*google font Merienda*/
@import url('https://fonts.googleapis.com/css2?family=Merienda:wght@700&display=swap');

/* Default CSS */
body {
	font-family: 'Noto Sans KR', sans-serif;
	font-size: 18px; /*기본 16px이다*/
	line-height: 1.7em;
	margin: 0; /*body의 margin없애기*/
	background-color: #fff;
	color : #222;
}
/*CSS*/
.container{
	width: 70%;
	margin: 0 auto;
}
</style>
<decorator:head/>
</head>
<body>
	<header>
<!-- 		<div id="log_image"><img src="/upload/image/dog01.jpg"/></div> -->
		<nav class="navbar navbar-inverse navbar-fixed-top">
			<div class="container-fluid">
				<div class="navbar-header">
					<button type="button" class="navbar-toggle" data-toggle="collapse"
						data-target="#myNavbar">
						<span class="icon-bar"></span> <span class="icon-bar"></span> <span
							class="icon-bar"></span>
					</button>
					<a class="navbar-brand logo" href="/main/main.do">
						<h1>Go travel.com</h1>
					</a>
				</div>
				<div class="collapse navbar-collapse" id="myNavbar">
					<ul class="nav navbar-nav nav_bar">
						<li><a href="${path }/notice/list.do" class="a_link">공지사항</a></li>
						<li><a href="${path }/image/list.do" class="a_link">이미지</a></li>
							<li><a href="${path }/board/list.do" class="a_link">자유게시판</a></li>
						<!-- &amp; - &, &lt; -> <, &gt; -> >, &nbsp; blank -->
						<li><a href="${path }/qna/list.do" class="a_link">Q&amp;A</a></li>
						<!-- login이 되어있어야만 보여주는 메뉴임 -->
						<c:if test="${!empty login }">
							<li><a href="${path}/message/list.do" class="a_link">메시지</a></li>
						</c:if>
					</ul>
					<!-- 메인 메뉴 부분의 로그인 사용자 정보 -->
				    <ul class="nav navbar-nav navbar-right">
				      <c:if test="${empty login}">
				      <!-- 로그인이 안되어 있는 경우의 메뉴 -->
				      <li><a href="${path}/member/register.do" class="a_link">
				      	<span class="glyphicon glyphicon-user"></span> 회원가입</a>
				      </li>
				      <li><a href="${path}/member/loginForm.do" class="a_link"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
				      </c:if>
				      <c:if test="${!empty login}">
					      <!-- 로그인이 되어 있는 경우의 메뉴 -->
					      <li><a href="${path }/member/logout.do" class="a_link"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
					      <li>
					      	<a href="" class="a_link">
						      	<img alt="회원이미지" src="${login.photo}" style="width: 25px; height: 25px;">
								    		 ${login.name }(${login.gradeName })
										<%-- <span class="glyphicon glyphicon-user"></span> ${login.name } --%>
					      	</a>
					      </li>
					      <li>
									<a href="/message/list.do">
										<span class="badge" style="background: red;" id="newMsgCnt">?</span>
										<!-- badge안의 값을 MainController에서 db로부터 가져와야함 -->
									</a>
					      </li>
				      </c:if>
				    </ul>
				</div>
			</div>
		</nav>
	</header>
	<article>
		<decorator:body />
	</article>
	<!-- navbar-fixed-bottom : 다른 내용과 상관없이 맨 아래에 항상 고정으로 보임 -->
	<footer class="container-fluid text-center navbar navbar-inverse">
		<p>All right reserved</p>
	</footer>
</body>
</html>