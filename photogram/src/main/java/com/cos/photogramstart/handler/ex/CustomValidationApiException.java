package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{

	// Exception이 될려면 RuntimeException을 상속받으면 된다.
	//시리얼번호 : 객체를 구분할때 사용(jvm한테 중요한거고 우리는 ㄴㄴ)
	private static final long serialVersionUID = 1L;
	
	private Map<String,String> errorMap;
	//생성자 정의 1개있는 거는 id못찾을때 orElseThrow부분..
	public CustomValidationApiException(String message) {
		super(message);
	}
	public CustomValidationApiException(String message,Map<String, String> errorMap) {
		super(message);//메시지에 대한 게터안만들어도 되는이유 : 이렇게 그냥 부모한테 던지기
		this.errorMap = errorMap;
	}
	
	public Map<String,String> getErrorMap(){
		return errorMap;
	}
}
