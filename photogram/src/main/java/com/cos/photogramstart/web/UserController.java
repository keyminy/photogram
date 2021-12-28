package com.cos.photogramstart.web;

import org.springframework.security.core.Authentication;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.service.UserService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/user/{id}")
	public String profile(@PathVariable int id,Model model) {
		User userEntity = userService.회원프로필(id);
		model.addAttribute("user",userEntity);
		return "/user/profile";
	}
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id
			,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		// (M1)추천
		System.out.println("세션 정보 : " + principalDetails.getUser());
		//결과 : User(id=1,username=ssar~~)
			
		//(M2)비추
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		PrincipalDetails mprincipalDetails = (PrincipalDetails)auth.getPrincipal(); //Object -> PrincipalDetails로 다운캐스팅
//		System.out.println("직접 찾은 세션 정보 : " + mprincipalDetails.getUser());
		//PrincipalDetails(user=User(id=1, username=ssar, password=$2a$10$abTZdWdy3E5luUJ7UJx3qeznBPBJkMK.NwionCPnCwuJNmGVNcNBa, name=쌀쌀, website=null, bio=null, email=ssar@nate.com, phone=null, gender=null, profileImageUrl=null, role=ROLE_USER, createDate=2021-12-25T11:14:27.669481))

		return "/user/update";
	}
}
