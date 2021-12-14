package com.my.message.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.member.vo.LoginVO;
import com.my.message.mapper.MessageMapper;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/messageAjax")
@RestController
public class MessageAjaxController {
	
	@Autowired
	private MessageMapper mapper;
	
	//새로운 메시지 개수 가져오기
	@RequestMapping(value="/msgCount.do",
			produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE})
	public Long msgCount(HttpSession session,PageObject pageObject) throws Exception {
		pageObject.setAcceptMode(4);
		pageObject.setAccepter( ( (LoginVO)session.getAttribute("login") ).getId());
		return mapper.getTotalRow(pageObject);
	} 
}
