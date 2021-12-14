package com.my.image.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.my.image.service.ImageService;
import com.my.image.vo.ImageVO;

import lombok.extern.log4j.Log4j;

@Log4j
@RequestMapping("/imageAjax")
@RestController
public class ImageAjaxController {
	
	@Autowired
	@Qualifier("isi")
	private ImageService service;
	
	@RequestMapping(value="/like.do",
			produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ImageVO dolike(ImageVO vo) throws Exception {
		service.dolike(vo);
		return service.view(vo);
	}
	@RequestMapping(value="/likeCancle.do",
			produces = {
					MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ImageVO likeCancle(ImageVO vo) throws Exception {
		service.likeCancle(vo);
		return service.view(vo);
	}
	
}
