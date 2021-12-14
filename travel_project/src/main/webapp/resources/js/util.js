//날짜객체(date)를 날짜 문자열로 표시하는 함수
//dateToTimeStr(date,sep(구분자 : . | / | - )) ex) yyyy.MM.dd
function dateToDateStr(date,sep){
	if(!sep) sep=".";
	//date를 날짜형 문자형(DateStr로 만들기)
	//여기서 date는 반드시 date 객체타입 이여야함.
	var yy = date.getFullYear();
	var MM = date.getMonth() + 1; //월은 0부터 시작함.
	var dd = date.getDate(); //getDay() : 오류 , 설정한 날짜를 가져와야하므로.
	return yy+sep+addNumZero(MM)+sep+addNumZero(dd);
}

//날짜객체(date)를 시간 문자열로 표시하는 함수
//dateToTimeStr(date) ex) hh:mm:ss
function dateToTimeStr(date){
	//여기서 date는 반드시 date 객체타입 이여야함.
	var hh = date.getHours();
	var mm = date.getMinutes();
	var ss = date.getSeconds();
	return addNumZero(hh) + ":" +addNumZero(mm) + ":" + addNumZero(ss);
}

//숫자 1자리를 -> 2자리로 만들어주는 함수
function addNumZero(data){
	if(data>9) return data;
	return "0"+data;
}

//날짜 데이터를 timeStemp라는 Long type으로 받아 시분초로 계산.
//현재시간 기준으로 24시간이 지났으면 날짜문자열 출력,그렇지 않으면 숫자 문자열 돌려주는 함수.
function displayDateTime(timeStamp){
	//오늘 날짜 객체 만들기
	var today = new Date();
	// 오늘 날짜 timeStamp - 파라매터의 timeStamp
	var gap = today.getTime() - timeStamp;
	//timeStamp : 1초가 1000
	if(gap < (1000*60*60*24)){
		//작성한 날짜가 24시간이 지나지않았으면 날짜를 시간문자열로 돌려준다.
		return dateToTimeStr(new Date(timeStamp));
	}else{
				//작성한 날짜가 24시간이 지났으면 날짜를 날짜문자열로 돌려준다.
		return dateToDateStr(new Date(timeStamp),".")
	}
}

//Ajax를 이용한 댓글 페이지네이션 태그 만들기(li tag만 만듬)
//구조 : li - a - data
//선택된 페이지는 click이벤트 시 active 클래스 속성 적용
//일단 모든 li태그에 class="reply_nav_li"로 주자. $(.reply_nav_li)가 클릭되면 어떤 이벤트~~
//넘어오는 데이터는 pageObject={"page":1, startPage:1, endPage:2, totalPage:2 , perGroupPageNum:10..} 등 으로 JSON형식이다.
//결과는 페이지네이션의 ul tag 안에 들어갈 li태그들을 문자열로 넘겨준다.
function ajaxPage(pageObject){
	var str = ""; // tag를 만들어서 저장할 변수 저장
	// 1페이지로 이동시키는 버튼.
	str += "<li data-page='1' class='reply_nav_li'>";
	if(pageObject.page > 1){
		str += "<a href='' onclick='return false' class='move'" 
				+	"title='click to move first page!' data-toggle='tooltip' data-placement='top' >";
		str += "<i class='glyphicon glyphicon-fast-backward'></i>";
		str += "</a>";
	}else if(pageObject.page == 1){ // 현재 page가 ==1
		str += "<a href='' onclick='return false' title='no move page!' ";
		str += " data-toggle='tooltip' data-placement='top' >";
		str += "<i class='glyphicon glyphicon-fast-backward' style='color: #999;'></i>";
		str += "</a>";
	}
	str += "</li>";
	
	//이전 그룹의 페이지로 이동시키기
	str += "<li data-page='"+(pageObject.startPage - 1)+"' class='reply_nav_li'>";
	if(pageObject.startPage>1){
		str += "<a href='' title='click to move first page!' class='move'";
		str += "data-toggle='tooltip' data-placement='top'>";
		str += "<i class='glyphicon glyphicon-step-backward' style='color: #999;'></i>";
		str += "</a>";
	}else{
		str += "<a href='' onclick='return false' ";
		str += "title='no move page!' data-toggle='tooltip' data-placement='top'>";
		str += "<i class='glyphicon glyphicon-fast-backward' style='color: #999;'></i>";
		str += "</a>";
	}
	str += "</li>";
	
	//startPage ~ endPage for문 돌려서 버튼만들기
	for(let i=pageObject.startPage; i<=pageObject.endPage; i++){
		str += "<li data-page='"+ i +"' class='reply_nav_li "; // reply_nav_li 클릭하면 동작시킬수 있게 이벤트.
		//active 속성을 넣기위해 reply_nav_li 뛰어쓰기 중요..
		if(pageObject.page==i) str += "active";
		str += "'>"; //클래스 속성값 끝내는 부분..조심
		if(pageObject.page == i) str += "<a href='' onclick='return false'>" + i + "</a>";
		else str += "<a href='' title='click to move "+ i +" page' class='move' " 
							+  "data-toggle='tooltip' data-placement='top'>" + i + "</a>";
		str += "</li>";
	}
	
	// 다음 그룹으로 이동 : page를 endPage +1 로 이동
	// page 현재 10 -> 다음 : 11(그룹이 바껴) startPage : 11 , endPage : 15
	// endpage == totalPage면 이동불가.
	str += "<li data-page='"+ (pageObject.endPage + 1) +"' class='reply_nav_li'>";
	if(pageObject.endPage < pageObject.totalPage){ // 담페이지로 이동 가능
		str += "<a href='' title='click to move next page group!' class='move' ";
		str += "data-toggle='tooltip' data-placement='top'>";
		str += "<i class='glyphicon glyphicon-step-forward'></i>";
		str += "</a>";
	}else if(pageObject.endPage == pageObject.totalPage){
		str += "<a href='' onclick='return false'";
		str += "title='no move page!' data-toggle='tooltip' data-placement='top'>";
		str += "<i class='glyphicon glyphicon-step-forward' style='color: #999;'></i>";
		str += "</a>";
	}
	str += "</li>";
	
	//맨 마지막 페이지로 이동 시키기 : totalpage로 이동시킴.
	// 현재 page == totalPage면 이동불가.
	str += "<li data-page='"+pageObject.totalPage+"' class='reply_nav_li'>";
	if(pageObject.page < pageObject.totalPage){
		str += "<a href='' title='click to move last page!' class='move' " 
				+	"data-toggle='tooltip' data-placement='top'>";
		str += "<i class='glyphicon glyphicon-fast-forward'></i>";
		str += "</a>";
	}else if(pageObject.page == pageObject.totalPage){
		str += "<a href='' onclick='return false' ";
		str += "title='no move page!' data-toggle='tooltip' data-placement='top'>";
		str += "<i class='glyphicon glyphicon-fast-forward' style='color: #999;'></i>";
		str += "</a>";
	}
	str += "</li>";
	
	return str;
}
 