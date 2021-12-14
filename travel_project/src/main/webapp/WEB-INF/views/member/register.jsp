<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입화면</title>
<link rel="stylesheet" href="/resources/css/member/register.css">
<link rel="stylesheet" href="//code.jquery.com/ui/1.13.0/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.13.0/jquery-ui.js"></script>
<script type="text/javascript">
$(function(){
    $(".radio").checkboxradio();
    // datepicker 클래스 이벤트 - 적정한 옵션을 넣어서 초기화 시켜 준다. 기본 datepicker()로 사용 가능
    $(".datepicker").datepicker({
	    changeMonth: true,
	    changeYear: true,
	    yearRange: '1950:2031',
	    dateFormat: "yy.mm.dd",
	    dayNamesMin: [ "일", "월", "화", "수", "목", "금", "토" ],
	    monthNamesShort: [ "1월", "2월", "3월", "4월", "5월", "6월", "7월", "8월", "9월", "10월", "11월", "12월" ],
    });
    $(".cancleBackBtn").click(function(){
    	history.back();
    }); 
    //아이디 중복체크
    $("#doubleCheckBtn").click(function(){
    	var str ="";
    	var id_length_error = "아이디는 3자 이상이여야 합니다";
    	var id = $("#id").val(); //input의 값 가져오기
    	if(!id || id.legnth<3){
    		$("#checkId").text(id_length_error).css("color","red");
    		$("#id").focus(); //focus를 위치시킴
    		return false;
    	}
    	 $.ajax({
    	    	url : "/memberAjax/checkId.do",
    	    	type : "POST",
    	    	data : {"id":id},
    	    	success : function(data){
    	    		if(data){
    	    			str += "<span style='color:tomato;'>"+data+"</span>은(는) 사용불가능한 아이디 입니다.";
    	    			$("#checkId").html(str).css("background-color","#ffdf40");
								$("#go_register").attr('disabled', true);
    	    		}else{
    	    			str += "<span style='color:tomato;'>"+id+"</span>은(는) 사용 가능한 아이디 입니다.";
    	    			$("#checkId").html(str).css("background-color","#a3d278");
    	    			$("#go_register").attr('disabled', false);
    	    		}
    	    	},//end success
    	    	error : function() {alert("error");}
    	    });
    });
});
</script>
</head>
<body>
	<div class="container">
		<h2>회원가입화면</h2>
		<div class="panel panel-default">
			<div class="panel-body">
				<form action="register.do" method="post" class="form-horizontal" enctype="multipart/form-data" >
					<div class="form-group">
						<label class="control-label col-sm-2" for="id">아이디:</label>
						<div class="col-sm-10 idBtns">
							<input type="text" class="form-control" id="id" name="id" placeholder="아이디를 입력하세요"
							maxlength="20" pattern="[A-Za-z][A-Za-z0-9]{2,19}" autocomplete="off" style="width: 30%" 
							title="영문자로 시작해서 영문자 숫자를 3~20 크기로 입력하셔야 합니다." required="required">
							<input type="button" value="중복체크" class="btn btn-warning" id="doubleCheckBtn" />
							<span id="checkId"></span>
						</div>
					</div>
<!-- 					<div class="alert alert-warning" style="width: 30%;"> -->
<!-- 						아이디는 4자 이상 이여야 합니다. -->
<!-- 					</div> -->
					<div class="form-group">
						<label class="control-label col-sm-2" for="pw">비밀번호:</label>
						<div class="col-sm-10">
							<input type="password" class="form-control" id="pw" name="pw" required="required"
								maxlength="20" placeholder="비밀번호를 입력하세요" style="width: 30%">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="name">이름:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="name" name="name"
								placeholder="이름을 입력하세요" style="width: 30%" required="required">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="age">성별:</label>
						<div class="col-sm-10">
						  <fieldset>
						    <label for="man">남자</label>
						    <input type="radio" name="gender" id="man" 
						    value="남자" checked="checked" class="radio">
						    <label for="woman">여자</label>
						    <input type="radio" name="gender" id="woman" 
						    value="여자" class="radio">
						  </fieldset>
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="birth">생년월일:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control datepicker" id="birth" name="birth" required="required"
							autocomplete="off" placeholder="생년월일을 입력하세요" style="width: 30%">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="tel">연락처:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="tel" name="tel"
								placeholder="전화번호를 입력하세요" style="width: 30%" required="required">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="email">이메일:</label>
						<div class="col-sm-10">
							<input type="text" class="form-control" id="email" name="email"
								placeholder="이메일을 입력하세요" style="width: 30%" required="required">
						</div>
					</div>
					<div class="form-group">
						<label class="control-label col-sm-2" for="multipartFile">사진:</label>
						<div class="col-sm-10">
							<input type="file" class="control-label" id="multipartFile" name="multipartFile">
						</div>
					</div>
					<input type="hidden" name="filename" id="filename" value="">
					<div class="Btns">
		       <button class='btn btn-primary' id="go_register">등록</button>
	      	 <button type="reset" class="btn btn-warning">새로고침</button>
	      	 <button type="button" class="btn btn-danger cancleBackBtn">돌아가기</button>
	       </div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>