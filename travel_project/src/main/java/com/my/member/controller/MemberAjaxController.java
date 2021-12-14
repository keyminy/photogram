package com.my.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.member.service.MemberService;
import com.my.member.vo.MemberVO;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/memberAjax")
@RestController
public class MemberAjaxController {
	
	@Autowired
	@Qualifier("msi")
	private MemberService service;
	
	@RequestMapping("/checkId.do")
	public String checkId(MemberVO vo) throws Exception {
		String result = service.checkId(vo);
		return result;
	}
	
}
