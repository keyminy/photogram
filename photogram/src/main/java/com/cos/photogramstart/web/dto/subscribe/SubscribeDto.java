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
	private int id;
	private String username;
	private String profileImageUrl;
	private Integer subscribeState;
	private Integer equalUserState; //로그인한 사용자와 동일인인지 아닌지? false면 보여주고 true면 안보여지게
	
}
