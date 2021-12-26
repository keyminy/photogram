package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomApiException extends RuntimeException{

	// Exception이 될려면 RuntimeException을 상속받으면 된다.
	//시리얼번호 : 객체를 구분할때 사용(jvm한테 중요한거고 우리는 ㄴㄴ)
	private static final long serialVersionUID = 1L;
	
	//생성자 정의 1개있는 거는 id못찾을때 orElseThrow부분..
	public CustomApiException(String message) {
		super(message);
	}

}
