package com.my.board.controller;

import java.net.URLEncoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import com.my.board.service.BoardService;
import com.my.board.vo.BoardVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board")
@Log4j
public class BoardController {
	
	private final String MODULE = "board";
	
	@Autowired
	@Qualifier("bsi")
	private BoardService service;
	
	//1.게시판 리스트 /list.do - get
	@GetMapping("/list.do")
	public String list(Model model,@ModelAttribute PageObject pageObject) throws Exception {
		log.info("----------list().pageObject 생성 : " + pageObject + "------------");
		//파라매터에 pageObject 써줌으로써, pageObject가 생성되며 생성자 기본값이 셋팅됨
		//PageObject [page=1, perPageNum=10, startRow=0, endRow=0 ~~
		model.addAttribute("list",service.list(pageObject));
		log.info("-------list실행 후 : " + pageObject + "------------");
		return MODULE + "/list";
	}
	
	//2.게시판 글보기 /view.do -get
	@GetMapping("/view.do")
	public String view(Model model,Long no,
			@ModelAttribute PageObject pageObject) throws Exception {
		model.addAttribute("vo",service.view(no,"view"));
		return MODULE + "/view";
	}
	
	//3-1.게시판 등록폼으로 가기 /write.do - get
	@GetMapping("/write.do")
	public String writeForm() {
		return MODULE + "/write";
	}
	//3-2.게시판 등록 처리 /write.do - post
	@PostMapping("/write.do")
	public String write(BoardVO vo,int perPageNum,
			RedirectAttributes rttr) throws Exception {
		log.info("wirte().vo : " + vo);
		service.write(vo);
		rttr.addFlashAttribute("msg","게시판 글 등록 성공");
		//write후 perPageNum은 변함이 없어야하므로 매개변수에 perPageNum을 받는다.
		return "redirect:list.do?perPageNum="+perPageNum;
	}
	
	//4-1.게시판 글 수정 폼 /update.do - get
	@GetMapping("/update.do")
	public String updateForm(Model model,Long no) throws Exception {
		log.info("updateForm().no : " + no);
		model.addAttribute("vo",service.view(no,"update"));
		return MODULE + "/update";
	}
	//4-2.게시판글 수정 처리 /update.do - post
	@PostMapping("/update.do")
	public String update(BoardVO vo,RedirectAttributes rttr,
			PageObject pageObject) throws Exception {
		log.info("update().vo :" + vo);
		int result = service.update(vo);
		if(result==0) throw new Exception("게시판 수정 실패 - 비밀번호를 확인해 주세요");
		log.info("result : " + result);
		rttr.addFlashAttribute("msg","게시판 글 수정이 성공적으로 되었습니다.");
		return "redirect:view.do?no="+vo.getNo()
			   +"&page="+pageObject.getPage()
			   +"&perPageNum="+pageObject.getPerPageNum()
			   +"&key="+pageObject.getKey()
			   +"&word="+URLEncoder.encode(pageObject.getWord(),"utf-8");
		//+"&word="+pageObject.getWord(); 한글이 깨지는 문제..
		//redirect의 URL로 가는 바람에 서버의 한글이 적용되어버림
	}
	//5.게시판 글삭제 /delete.do
	@PostMapping("/delete.do")
	public String delete(BoardVO vo,RedirectAttributes rttr
			,int perPageNum) throws Exception{
		log.info("delete().vo : " + vo);
		int result = service.delete(vo);
		if(result==0) throw new Exception("게시판 비밀번호가 틀렸습니다.");
		rttr.addFlashAttribute("msg", "글삭제가 성공적으로 되었습니다."); 
		log.info("delete().result : " + result);
		return "redirect:list.do?perPageNum="+perPageNum;
	}
}
