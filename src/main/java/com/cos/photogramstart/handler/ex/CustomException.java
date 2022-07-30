package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomException extends RuntimeException{
//map 에 담은 error.getDefaultMessage()를 꺼내기 위해..
	//객체를 구분할때 사용하는거임
	private static final long serialVersionUID = 1L;
	
	public CustomException(String message) {
		super(message);
	}
	

}
