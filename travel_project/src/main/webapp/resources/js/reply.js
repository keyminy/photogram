// 댓글 처리 객체
//replyService 함수 - 처리 해야할 함수를 가지면서 리턴해주는 함수
var replyService = (function(){
	//--------------------실행할 함수 정의------------------
	//댓글 리스트 가져오기 : list()
	function list(param,callback,error){
		var no = param.no;
		var page = param.repPage || 1;
		var perPageNum = param.repPerPageNum || 5;
		
		//Ajax를 이용해 JSON 형태의 데이터 가져오는 $.getJSON
		$.getJSON(
			//요청이 받아지는 url
			"/reply/list.do?no="+no+"&repPage="+page+"&repPerPageNum"+perPageNum,
			//서버가 응답해주는 data
			function(data){
				if(callback){
					//callback함수 있으면 서버에서 응답해준 data값으로 실행
					callback(data); 
				}
			}
		).fail(function(xhr,status,err){
				if(error){
					error();
				}else {alert("데이터 가져오기를 실패하셨습니다.");}
		}).done(function( msg ) {
    $('[data-toggle="tooltip"]').tooltip();
});//end $.getJSON
	}// end list()
	
	//댓글 등록하기 : write()
	function write(reply,callback,error){
		$.ajax({
			type : "post",
			url : "/reply/write.do", //요청하는 서버 주소
			data : JSON.stringify(reply),
			contentType:"application/json; charset=utf-8",
			success : function(result,status,xhr){
				if(callback) callback(result);
				else alert("댓글이 등록되었습니다.");
			},
			error : function(xhr,status,err){
				if(error) error(err);
				else alert("댓글 쓰기 중 오류 발생");
			}
		});
	}
	
	function update(reply,callback,error){
			$.ajax({
			type : "patch",
			url : "/reply/update.do", //요청하는 서버 주소
			data : JSON.stringify(reply),
			contentType:"application/json; charset=utf-8",
			success : function(result,status,xhr){
				if(callback) callback(result,status);
				else alert("댓글이 수정이 되었습니다.");
			},
			error : function(xhr,status,err){
				if(error) error(err,status);
				else alert("댓글 수정에 실패하였습니다");
			}
		});
	}
	
	function deleteReply(reply,callback,error){
			$.ajax({
			type : "delete",
			url : "/reply/delete.do", //요청하는 서버 주소
			data : JSON.stringify(reply),
			contentType:"application/json; charset=utf-8",
			success : function(result,status,xhr){
				if(callback) callback(result,status);
				else alert("댓글 삭제가 완료 되었습니다.");
			},
			error : function(xhr,status,err){
				if(error) error(err);
				else alert("댓글 삭제 실패하였습니다");
			}
		});
	}

	//함수를 Object타입으로 만들어 return
	return {
		//replyService.list() 으로 실행
		list:list,
		write:write,
		update:update,
		delete:deleteReply
	};
})();