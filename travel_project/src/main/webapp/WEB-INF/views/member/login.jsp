<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로그인 폼</title>
<style>

	.loginForm{
		margin: 0 auto;
		border:1px solid black;
		box-sizing:border-box;
		width: 25%;
	}
	.form_class{
		padding: 100px;
	}

</style>
</head>
<body>
	<div class="wrap">
			<div class="loginForm">
				<form action="login.do" method="post" class="form_class">
					<h2>로그인 폼</h2>
			    <div class="form-group">
			      <label for="id" class="control-label">아이디</label>
			      <input type="text" class="form-control" id="id" 
			      placeholder="id를 입력하세요" name="id">
			    </div>
			    <div class="form-group">
			      <label for="pw" class="control-label">비밀번호</label>
			      <input type="password" class="form-control" id="pw"
			      placeholder="비밀번호를 입력하세요" name="pw">
			    </div>
			    <div class="btns">
			    	<button type="submit" class="btn btn-default">로그인</button>
			    	<a href="register.do" class="btn btn-default">회원가입</a>
			    </div>
	  	</form>
  	</div>
	</div>
</body>
</html>