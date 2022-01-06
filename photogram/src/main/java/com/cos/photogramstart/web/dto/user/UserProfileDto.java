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
	private boolean pageOwnerState; //이 page의 주인인지 아닌지 판단
	private int imageCount;
	private boolean subscribeState;
	private int subscribeCount;
	private User user;
}
