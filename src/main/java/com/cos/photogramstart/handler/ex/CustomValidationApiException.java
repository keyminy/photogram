package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{
//map 에 담은 error.getDefaultMessage()를 꺼내기 위해..
	//객체를 구분할때 사용하는거임
	private static final long serialVersionUID = 1L;
	
	private Map<String,String> errorMap;
	
	public CustomValidationApiException(String message,Map<String,String> errorMap) {
		super(message);
		this.errorMap = errorMap;
	}
	public CustomValidationApiException(String message) {
		super(message);
	}
	
	public Map<String,String> getErrorMap(){
		return errorMap;
	}
}
