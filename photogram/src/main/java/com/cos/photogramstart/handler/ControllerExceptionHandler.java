package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController // 레스트컨트롤러! 데이터 메시지를 리턴함
@ControllerAdvice
public class ControllerExceptionHandler {
	
	//RuntimeException이 발생하는 모든 예외를 validationException함수가 가로챈다.
	//@ExceptionHandler(RuntimeException.class)
//	@ExceptionHandler(CustomValidationException.class) // errorMap의 메시지를 얻기위해
//	//public CMRespDto<Map<String,String>> validationException(CustomValidationException e) {
//	public CMRespDto<?> validationException(CustomValidationException e) {
//		//return new CMRespDto(-1,e.getMessage(),e.getErrorMap());
//		return new CMRespDto<Map<String,String>>(-1,e.getMessage(),e.getErrorMap());
//		//에러 맵을 리턴하겟죠?
//	}
	
	@ExceptionHandler(CustomValidationException.class) // errorMap의 메시지를 얻기위해
	public String validationException(CustomValidationException e) {
		//위의 CMRespDto와 Script를 비교
		//1.클라이언트에게 응답할때는 SCript가 좋음
		//2.Ajax통신을 할땐 CMRespDto를 쓰게됨.
		//3.Android통신도 CMRespDto씀
		//1번은 클라이언트가 응답받는거 2,3번은 개발자가 응답받을때임.
		return Script.back(e.getErrorMap().toString());
	}
	
	@ExceptionHandler(CustomValidationApiException.class) // errorMap의 메시지를 얻기위해
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		// <?> : 응답할때 제니릭 타입이 자동으로 결정됨
		System.out.println("====================나실행됨?");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomApiException.class) // errorMap의 메시지를 얻기위해
	public ResponseEntity<?> apiException(CustomApiException e) {
		// <?> : 응답할때 제니릭 타입이 자동으로 결정됨
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
	
	
}
