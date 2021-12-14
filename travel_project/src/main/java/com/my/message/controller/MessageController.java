package com.my.message.controller;

import java.net.URLEncoder;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.my.member.vo.LoginVO;
import com.my.message.controller.MessageController;
import com.my.message.service.MessageService;
import com.my.message.vo.MessageVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/message")
@Log4j
public class MessageController {

	private final String MODULE = "message";
	
	@Autowired
	@Qualifier("msi")
	private MessageService service;
	
	//1.메시지 리스트 /list.do - get
	@GetMapping("/list.do")
	public String list(Model model,@ModelAttribute PageObject pageObject
			,HttpSession session,String mode) throws Exception {
		
		pageObject.setAcceptMode(3); //기본 모드 3번으로 셋팅
		pageObject.setAccepter( ( (LoginVO)session.getAttribute("login") ).getId());
		if(mode != null) pageObject.setAcceptMode(Integer.parseInt(mode));
		log.info("----------list().pageObject 생성 : " + pageObject + "------------");
		//파라매터에 pageObject 써줌으로써, pageObject가 생성되며 생성자 기본값이 셋팅됨
		//PageObject [page=1, perPageNum=10, startRow=0, endRow=0 ~~
		model.addAttribute("list",service.list(pageObject));
		log.info("-------list실행 후 : " + pageObject + "------------");
		return MODULE + "/list";
	}
	
	//2.메시지 글보기 /view.do -get
	@GetMapping("/view.do")
	public String view(Model model,Long no,@ModelAttribute String mode,HttpSession session,
			MessageVO vo,@ModelAttribute PageObject pageObject) throws Exception {
		vo.setNo(no);
		vo.setAccepter( ( (LoginVO)session.getAttribute("login") ).getId());
		model.addAttribute("vo",service.view(no,vo));
		return MODULE + "/view";
	}
	
	//3-1.메시지 등록폼으로 가기 /write.do - get
	@GetMapping("/wirteForm.do")
	public String writeForm() {
		return MODULE + "/wirteForm";
	}
	//3-2.메시지 등록 처리 /write.do - post
	@PostMapping("/write.do")
	public String write(MessageVO vo,HttpSession session
			,RedirectAttributes rttr) throws Exception {
		vo.setSender( ( (LoginVO)session.getAttribute("login") ).getId());
		log.info("wirte().vo : " + vo);
		service.write(vo);
		rttr.addFlashAttribute("msg","메시지 글 등록 성공");
		//write후 perPageNum은 변함이 없어야하므로 매개변수에 perPageNum을 받는다.
		return "redirect:list.do";
	}
	
	//4.메시지 글삭제 /delete.do
	@PostMapping("/delete.do")
	public String delete(MessageVO vo,RedirectAttributes rttr,HttpSession session
			,int perPageNum) throws Exception{
		vo.setSender( ( (LoginVO)session.getAttribute("login") ).getId());
		log.info("delete().vo : " + vo);
		int result = service.delete(vo);
		if(result==0) throw new Exception("내가 받은 글 혹은 상대방이 읽지 않은 글만 삭제 가능합니다!");
		rttr.addFlashAttribute("msg", "글삭제가 성공적으로 되었습니다."); 
		log.info("delete().result : " + result);
		return "redirect:list.do?perPageNum="+perPageNum;
	}
}
