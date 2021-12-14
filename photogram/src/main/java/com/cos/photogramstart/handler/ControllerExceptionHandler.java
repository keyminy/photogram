package com.cos.photogramstart.handler;

import java.util.Map;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomValidationException;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	
	//RuntimeException이 발생하는 모든 예외를 validationException함수가 가로챈다.
	//@ExceptionHandler(RuntimeException.class)
	@ExceptionHandler(CustomValidationException.class) // errorMap의 메시지를 얻기위해
	public Map<String,String> validationException(CustomValidationException e) {
		return e.getErrorMap();
		//에러 맵을 리턴하겟죠?
	}
}
