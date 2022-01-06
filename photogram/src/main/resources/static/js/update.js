// (1) 회원정보 수정
function update(userid,event) {
	event.preventDefault(); //폼태그의 action을 막음
	// form태그의 id : profileUpdate 폼태그가 담고있는 input값을 data변수에 담기(serialize()메소드..)
	let data = $("#profileUpdate").serialize();
	//console.log(data);
	//data : name=%EC%8C%80%EC%8C%80&username=ssar&password=&website=&bio=&email=ssar%40nate.com&tel=&gender=
	$.ajax({
		type : "put",
		url : `/api/user/${userid}`,
		data : data,
		contentType : "application/x-www-form-urlencoded; charset=utf-8", //위의 data가 뭔지 설명
		dataType : "json"//내가 서버로부터 응답받을 데이터 타입 : json으로 받을거야
	}).done(res=>{ //HttpStatus 상태코드가 200번일때 
		// 서버로부터 받은 json데이터를 파싱해서 res변수에 저장한다. => res는 js의 object가됨
		console.log("성공, " , res);
		location.href=`/user/${userid}`; //자기 원래 페이지로 돌아가기
	}).fail(error=>{ //HttpStatus 상태코드가 200번이 아닐때
		//(M1)하드코딩
		//console.log("update실패!" , error.responseJSON.data.name);
		//alert("update실패!" + error.responseJSON.data.name);
		
		//(M2)JSON메서드 쓰기
		//JSON.stringify(error.responseJSON.data) -> object를 JSON 문자열로 변환시킴
		if(error.responseJSON.data == null){
			alert(error.responseJSON.message);
		}else{
			alert(JSON.stringify(error.responseJSON.data.name));// "공백일 수 없습니다."		
		}	
	});
}