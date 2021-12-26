package com.cos.photogramstart.web.api;

import java.util.HashMap;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.user.UserUpdateDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class UserApiController {
	
	private final UserService userService; //DI
	
	@PutMapping("/api/user/{id}")
	public CMRespDto<?> update(@PathVariable int id
			,@Valid UserUpdateDto userUpdateDto
			,BindingResult bindingResult //꼭 @Valid가 적혀있는 다음 파라메터에 적어야함..
			,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		if(bindingResult.hasErrors()) {
			Map<String,String> errorMap = new HashMap<>();
			for(FieldError error : bindingResult.getFieldErrors()) {
				// getFieldErrors() : 리스트 리턴
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("====================");
				System.out.println(error.getDefaultMessage()); //20자 이하여야 합니다.
				System.out.println("====================");
			}
			throw new CustomValidationApiException("유효성 검사 실패함",errorMap);
		} else { //정상일때 회원수정 쿼리 실행
			User userEntity = userService.회원수정(id, userUpdateDto.toEntity());
			principalDetails.setUser(userEntity); //세션정보 변경
			return new CMRespDto<User>(1,"회원수정완료",userEntity);			
		}
	}
}
