package com.cos.photogramstart.web;

import java.util.HashMap;

import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

@Controller //1.IOC에 등록 2.파일을 리턴하는 컨트롤러의 의미
@RequiredArgsConstructor
public class AuthController {
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);

	private final AuthService authService;
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	//회원가입 버튼 클릭시 -> /auth/signup -> return : /auth/signin
	@PostMapping("/auth/signup")
	public String signup(@Valid SignupDto signupDto,BindingResult bindingResult) {

			User user = signupDto.toEntity();
			User userEntity = authService.회원가입(user);
			log.info("회원가입 입력 값 user : " + userEntity);
			return "auth/signin";
	}
}
