package com.my.main.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.board.service.BoardService;
import com.my.image.service.ImageService;
import com.my.notice.service.NoticeService;
import com.webjjang.util.PageObject;

@Controller
@RequestMapping("/main")
public class MainController {
	@Autowired
	@Qualifier("bsi")
	private BoardService boardService;
	
	@Autowired
	@Qualifier("isi")
	private ImageService imageService;
	
	@Autowired
	@Qualifier("nsi")
	private NoticeService noticeService;
	
	@GetMapping("/main.do")
	public String main(Model model) throws Exception {
		
		//board 게시판 : 맨처음 1페이지 보여주고, 7개뿌리기
		PageObject pageObject = new PageObject(1,7);
		model.addAttribute("boardList",boardService.list(pageObject));
		
		//notice 게시판 : 맨처음 1페이지 보여주고,7개뿌리기
		model.addAttribute("noticeList",noticeService.list(pageObject));
		
		//image 게시판 : 맨처음 1페이지 보여주고, 4개뿌리기
		pageObject = new PageObject(1,4);
		model.addAttribute("imageList",imageService.list(pageObject));
		return "main/main";
		

	}
}
