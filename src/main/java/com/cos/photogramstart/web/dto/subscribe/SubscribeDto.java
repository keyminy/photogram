package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	//누군가를 구독취소/하기하는 정보(=toUserId)에 해당
	private int id;
	//사용자의 username(닉네임) ex.love,ssar,cos
	private String username;
	//사용자의 프로필 이미지
	private String profileImageUrl;
	//구독 하고 있는 상태인지?
	private Integer subscribeState;
	//로그인 한 사용자와 동일인 인지?
	//=> 로그인한 유저와 구독한 유저가 동일하면 구독하기/취소 버튼 안보이게
	private Integer eqaulUserState;
}
