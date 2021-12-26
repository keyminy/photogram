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

@RequiredArgsConstructor //(M3) authService에 final붙이고 롬복 : final 필드를 DI할때 사용
@Controller //1.IOC에 등록 2.파일을 리턴하는 컨트롤러의 의미
public class AuthController {
	
	
	private final AuthService authService;
	//* AuthService DI해서 불러오기!
	//(M1) @Autowired
	//(M2)AuthController의 생성자 만들어도됨.
//	public AuthController(AuthService authService) {
//		this.authService = authService;
//	}
	
	private static final Logger log = LoggerFactory.getLogger(AuthController.class);
	
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
	public String signup(@Valid SignupDto signupDto,BindingResult bindingResult) { // key=value 형식 : (x-www-form-urlencoded방식)
		//BindingResult : 문제가 생길때.. 오류를 모아줌
		if(bindingResult.hasErrors()) {
			Map<String,String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				// getFieldErrors() : 리스트 리턴
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("====================");
				System.out.println(error.getDefaultMessage()); //20자 이하여야 합니다.
				System.out.println("====================");
			}
			//return "오류남"; //이거하지말고 오류가 나면 RuntimeException 발생시키자
			//throw new RuntimeException("유효성 검사 실패함");
			throw new CustomValidationException("유효성 검사 실패함",errorMap);
		} //end if
		else {
			//else문 : 회원가입이 실행됨
			//User 객체에 <- 매개변수 SignupDto(회원이 입력한거) 넣기
			User user = signupDto.toEntity();
			log.info("회원가입 입력 값 user : " + user);
			User userEntity = authService.회원가입(user);
			log.info("userEntity : " + userEntity);
			return "auth/signin";
		}
		//회원가입 완료후 signin 페이지(로그인페이지)로 가기
		//System.out.println("signup 실행되요?"); // <- csrf토큰땜에 실행안되네

		//log.info(signupDto.toString()); // SignupDto(username=ssar, password=1234, email=2134@24124, name=안뇽)
	}
	
}
