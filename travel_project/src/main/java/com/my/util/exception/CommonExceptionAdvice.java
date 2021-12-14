package com.my.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;

@ControllerAdvice
@Log4j
public class CommonExceptionAdvice {
	//404예외에 대한 클래스 파일을 넘겨서 생성해서 사용하도록 지정
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		log.info("handle404() - 요청하신 페이지가 존재하지 않습니다.");
		return "error/custom404";
	}
	
	//500예외 처리
	@ExceptionHandler(Exception.class)
	public String exception(Exception ex,Model model) {
		//예외메시지
		log.error("Exception....." + ex.getMessage());
		model.addAttribute("exception",ex);
		return "error/error_page";
	}
}
