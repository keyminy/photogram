package com.my.qna.controller;


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
import com.my.qna.service.QnaService;
import com.my.qna.vo.QnaVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/qna")
@Log4j
public class QnaController {
	private final String MODULE = "qna";
	
	@Autowired
	@Qualifier("qsi")
	private QnaService service;
	
	//1.qna 리스트 /list.do - get
	@GetMapping("/list.do")
	public String list(Model model,@ModelAttribute PageObject pageObject) throws Exception {
		log.info("----------list().pageObject 생성 : " + pageObject + "------------");
		//파라매터에 pageObject 써줌으로써, pageObject가 생성되며 생성자 기본값이 셋팅됨
		//PageObject [page=1, perPageNum=10, startRow=0, endRow=0 ~~
		model.addAttribute("list",service.list(pageObject));
		log.info("-------list실행 후 : " + pageObject + "------------");
		return MODULE + "/list";
	}
	
	//2.qna 글보기 /view.do -get
	@GetMapping("/view.do")
	public String view(Model model,Long no,
			@ModelAttribute PageObject pageObject) throws Exception {
		model.addAttribute("vo",service.view(no,"view"));
		return MODULE + "/view";
	}
	
	//3-1.qna 질문폼으로 가기 /questionForm.do - get
	@GetMapping("/questionForm.do")
	public String questionForm() {
		return MODULE + "/questionForm";
	}
	//3-2.qna 질문처리 가기 /question.do - post
	@PostMapping("/question.do")
	public String question(QnaVO vo, int perPageNum,HttpSession session,
			RedirectAttributes rttr) throws Exception {
		//넘어 오는 데이터 title,content(사용자입력),id(session에서 받아야함)
		//글쓰기 처리 시 로그인- session안에 id에 대한 정보가 있기땜에 id저장가능.
		vo.setId( ( (LoginVO)session.getAttribute("login") ).getId());
		
		log.info("question().vo : " + vo);
		service.question(vo);
		rttr.addFlashAttribute("msg","질문 글 등록 성공");
		return "redirect:list.do?perPageNum="+perPageNum;
	}
	
	//4-1.qna 답변글 폼으로 가기 /answerForm.do - get
	@GetMapping("/answerForm.do")
	public String answerForm(Model model,Long no) throws Exception {
		//(질문글) view의 vo정보를 넘겨주며 answerForm으로 가기
		model.addAttribute("vo",service.view(no,"answer"));
		return MODULE + "/answerForm";
	}
	//4-2.qna 답변처리 가기 /question.do - post
	@PostMapping("/answer.do")
	public String answer(QnaVO vo, PageObject pageObject,HttpSession session,
			RedirectAttributes rttr) throws Exception {
		vo.setOrdNo(vo.getOrdNo()+1);
		vo.setLevNo(vo.getLevNo()+1);
		vo.setId( ( (LoginVO)session.getAttribute("login") ).getId());
		log.info("답변처리 넘어거는 answer().vo :" + vo);
		service.answer(vo);
		rttr.addFlashAttribute("msg","답변 글 등록 성공");
		return "redirect:list.do?page="+pageObject.getPage()
		+"&perPageNum="+pageObject.getPerPageNum();
	}

	//5-1.게시판 글 수정 폼 /update.do - get
	@GetMapping("/updateForm.do")
	public String updateForm(Model model,Long no) throws Exception {
		log.info("updateForm().no : " + no);
		model.addAttribute("vo",service.view(no,"update"));
		return MODULE + "/updateForm";
	}
	
	//5-2.게시판글 수정 처리 /update.do - post
	@PostMapping("/update.do")
	public String update(QnaVO vo,RedirectAttributes rttr,
			PageObject pageObject) throws Exception {
		log.info("update().vo :" + vo);
		int result = service.update(vo);
		log.info("result : " + result);
		rttr.addFlashAttribute("msg","게시판 글 수정이 성공적으로 되었습니다.");
		return "redirect:view.do?no="+vo.getNo()
			   +"&page="+pageObject.getPage()
			   +"&perPageNum="+pageObject.getPerPageNum();		 
	}
	
	//5.게시판 글삭제 /delete.do
	@GetMapping("/delete.do")
	public String delete(QnaVO vo,RedirectAttributes rttr
			,int perPageNum) throws Exception{
		log.info("delete().vo : " + vo);
		int result = service.delete(vo);
		rttr.addFlashAttribute("msg", "글삭제가 성공적으로 되었습니다."); 
		log.info("delete().result : " + result);
		return "redirect:list.do?perPageNum="+perPageNum;
	}
}
