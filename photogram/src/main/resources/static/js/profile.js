/**
  1. 유저 프로파일 페이지
  (1) 유저 프로파일 페이지 구독하기, 구독취소
  (2) 구독자 정보 모달 보기
  (3) 구독자 정보 모달에서 구독하기, 구독취소
  (4) 유저 프로필 사진 변경
  (5) 사용자 정보 메뉴 열기 닫기
  (6) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
  (7) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달 
  (8) 구독자 정보 모달 닫기
 */

// (1) 유저 프로파일 페이지 구독하기, 구독취소
function toggleSubscribe(toUserId,obj) {
	if ($(obj).text() === "구독취소") {
		$.ajax({
			//구독취소하기
			type:"delete",
			url:"/api/subscribe/"+toUserId,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독하기");
			$(obj).toggleClass("blue");
		}).fail(error=>{
			console.log("구독 취소 실패",error);
		});
	} else {
		$.ajax({
			//구독하기
			type:"post",
			url:"/api/subscribe/"+toUserId,
			dataType:"json"
		}).done(res=>{
			$(obj).text("구독취소");
			$(obj).toggleClass("blue");
		}).fail(error=>{
			console.log("구독 하기 실패",error);
		});
	}
}

// (2) 구독자 정보  모달 보기
function subscribeInfoModalOpen(pageUserId) {
	$(".modal-subscribe").css("display", "flex");
	//alert(pageUserId); //user/2로 갔다면 2가 나옴
	$.ajax({
		url:`/api/user/${pageUserId}/subscribe`,
		dataType:"json" //그 결과를 json으로받겠습니다.
	}).done(res=>{
		console.log(res.data);
		res.data.forEach((user)=>{
			let item = getSubscribeModalItem(user);
			$("#subscribeModalList").append(item);
		})
	}).fail(error=>{
		console.log("구독정보 불러오기 오류",error);
	});
}
//그림에 대한 데이터 만들기..
function getSubscribeModalItem(user) {
	let item=`<div class="subscribe__item" id="subscribeModalItem-${user.id}">
		<div class="subscribe__img">
			<img src="/upload/${user.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
		</div>
		<div class="subscribe__text">
			<h2>${user.username}</h2>
		</div>
		<div class="subscribe__btn">`;
			//동일한 유저가 아니라는말 = 버튼이 만들어짐
		if(!user.equalUserState){ //equalUserState가 1인것만 구독취소,하기 정보 뛰울수잇게
			if(user.subscribeState){
				//1 : 구독한 상태
				item += `<button class="cta blue" onclick="toggleSubscribe(${user.id},this)">구독취소</button>`;		
			}	else{
				//0 : 구독안한 상태 
				item += `<button class="cta blue" onclick="toggleSubscribe(${user.id},this)">구독하기</button>`;		
			}		
		}
		item += `
		</div>
	</div>`;
	return item;	
}

// (3) 유저 프로파일 사진 변경 (완)
function profileImageUpload() {
	$("#userProfileImageInput").click();

	$("#userProfileImageInput").on("change", (e) => {
		let f = e.target.files[0];

		if (!f.type.match("image.*")) {
			alert("이미지를 등록해야 합니다.");
			return;
		}

		// 사진 전송 성공시 이미지 변경
		let reader = new FileReader();
		reader.onload = (e) => {
			$("#userProfileImage").attr("src", e.target.result);
		}
		reader.readAsDataURL(f); // 이 코드 실행시 reader.onload 실행됨.
	});
}


// (4) 사용자 정보 메뉴 열기 닫기
function popup(obj) {
	$(obj).css("display", "flex");
}

function closePopup(obj) {
	$(obj).css("display", "none");
}


// (5) 사용자 정보(회원정보, 로그아웃, 닫기) 모달
function modalInfo() {
	$(".modal-info").css("display", "none");
}

// (6) 사용자 프로파일 이미지 메뉴(사진업로드, 취소) 모달
function modalImage() {
	$(".modal-image").css("display", "none");
}

// (7) 구독자 정보 모달 닫기
function modalClose() {
	$(".modal-subscribe").css("display", "none");
	location.reload();
}






