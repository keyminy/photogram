package com.cos.photogramstart.web.dto.user;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserProfileDto {
	private boolean pageOwnerState;
	private User user;
	//구독하는 상태인지?
	private boolean subscribeState;
	//유저가 구독한 갯수
	private int subscribeCount;
	private int imageCount;
}
