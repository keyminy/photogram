package com.cos.photogramstart.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId,
			@AuthenticationPrincipal PrincipalDetails principalDetails,Model model) {
		UserProfileDto dto=userService.회원프로필(pageUserId,principalDetails.getUser().getId());
		model.addAttribute("dto",dto);
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String updateForm(@PathVariable int id,@AuthenticationPrincipal PrincipalDetails principalDetails,Model model) {
		//System.out.println("세션 정보 : " + principalDetails.getUser());
		//model.addAttribute("principal",principalDetails.getUser());
		return "user/update";
	}
}
