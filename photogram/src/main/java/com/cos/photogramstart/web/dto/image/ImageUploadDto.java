package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	private MultipartFile file;
	private String caption;
	
	//postImageUrl에 uuid가 붙은 경로를 가져와야하므로 매개변수로 postImageUrl를 받는다
	public Image toEntity(String postImageUrl,User user) {
		return Image.builder()
					.caption(caption)
					.postImageUrl(postImageUrl)
					.user(user)
					.build();
	}
}
