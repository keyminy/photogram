// (1) 회원정보 수정
function update(userid,event) {
	event.preventDefault(); //form태그 액션 막기
	let data = $("#profileUpdate").serialize();
	//form태그의 데이터가 모두 담긴다..
	//name=%EC%8C%80%EC%8C%80&username=ssar&password=&website=&bio=&email=ssar%40nate.com&tel=fasdf&gender=
	//console.log(data);
	$.ajax({
		type:"put",
		url:`/api/user/${userid}`,
		data:data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json"		
	}).done(res=>{
		console.log("update 성공",res);
		location.href=`/user/${userid}`;
	}).fail(error=>{
		console.log(error);
		if(error.responseJSON.data==null){
			alert(error.responseJSON.message);
		}else{
			console.log("update 실패",error.responseJSON.data.name);
			alert(Object.keys(error.responseJSON.data)+" 은(는)"+error.responseJSON.data.name);			
		}
	});
}