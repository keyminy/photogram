package com.my.util.exception;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/error")
@Log4j
public class ErrorController {

	@RequestMapping("/auth_error.do")
	public String doLogin() {
		return "error/doLogin";
	}
	@RequestMapping("/admin_only.do")
	public String doAdmin() {
		return "error/doAdmin";
	}
}
