package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ImageController {
	
	private final ImageService imageService;
	
	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}
	@GetMapping({"/image/popular"})
	public String popular() {
		return "image/popular";
	}
	@GetMapping({"/image/upload"})
	public String uload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		//매개변수로 받을것? : caption과 file을 받아야함(<- 둘다 받기위해 dto를 만듬)
		//로그인이 되어있는 User정보인 세션도 필요하다
		
		//서비스 호출
		imageService.사진업로드(imageUploadDto, principalDetails);
		return "redirect:/user/"+principalDetails.getUser().getId(); //현재 로그인한 user의 id로 redirect
	}
}
