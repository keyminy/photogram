package com.my.notice.controller;

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

import com.my.notice.controller.NoticeController;
import com.my.notice.vo.NoticeVO;
import com.my.notice.service.NoticeService;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/notice")
@Log4j
public class NoticeController {
	
	private final String MODULE = "notice";
	
	@Autowired
	@Qualifier("nsi")
	private NoticeService service;
	
	//1.공지 리스트 /list.do - get
		@GetMapping("/list.do")
		public String list(Model model,@ModelAttribute PageObject pageObject) throws Exception {
			log.info("----------list().pageObject 생성 : " + pageObject + "------------");
			//파라매터에 pageObject 써줌으로써, pageObject가 생성되며 생성자 기본값이 셋팅됨
			//PageObject [page=1, perPageNum=10, startRow=0, endRow=0 ~~
			model.addAttribute("list",service.list(pageObject));
			log.info("-------list실행 후 : " + pageObject + "------------");
			return MODULE + "/list";
		}
		
		//2.공지 글보기 /view.do -get
		@GetMapping("/view.do")
		public String view(Model model,Long no,
				@ModelAttribute PageObject pageObject) throws Exception {
			model.addAttribute("vo",service.view(no));
			return MODULE + "/view";
		}
		
		//3-1.공지 등록폼으로 가기 /write.do - get
		@GetMapping("/write.do")
		public String writeForm() {
			return MODULE + "/write";
		}
		//3-2.공지 등록 처리 /write.do - post
		@PostMapping("/write.do")
		public String write(NoticeVO vo,int perPageNum,
				RedirectAttributes rttr) throws Exception {
			log.info("wirte().vo : " + vo);
			service.write(vo);
			rttr.addFlashAttribute("msg","공지 글 등록 성공");
			//write후 perPageNum은 변함이 없어야하므로 매개변수에 perPageNum을 받는다.
			return "redirect:list.do?perPageNum="+perPageNum;
		}
		
		//4-1.공지 글 수정 폼 /update.do - get
		@GetMapping("/update.do")
		public String updateForm(Model model,Long no) throws Exception {
			log.info("updateForm().no : " + no);
			NoticeVO vo = service.view(no);
			//input=date type의 값을 셋팅할때 HTML의 데이터 패턴이 yyyy-mm-dd이여야 하므로 .을 -로 바꾸는 작업
			vo.setStartDate(vo.getStartDate().replace(".", "-"));
			vo.setEndDate(vo.getEndDate().replace(".", "-"));
			model.addAttribute("vo",vo);
			return MODULE + "/update";
		}
		//4-2.공지글 수정 처리 /update.do - post
		@PostMapping("/update.do")
		public String update(NoticeVO vo,RedirectAttributes rttr,
				PageObject pageObject) throws Exception {
			log.info("update().vo :" + vo);
			int result = service.update(vo);
			if(result==0) throw new Exception("공지 수정 실패 - 비밀번호를 확인해 주세요");
			log.info("result : " + result);
			rttr.addFlashAttribute("msg","공지 글 수정이 성공적으로 되었습니다.");
			return "redirect:view.do?no="+vo.getNo()
				   +"&page="+pageObject.getPage()
				   +"&perPageNum="+pageObject.getPerPageNum();
		}
		//5.공지 글삭제 /delete.do
		@GetMapping("/delete.do")
		public String delete(NoticeVO vo,RedirectAttributes rttr
				,int perPageNum) throws Exception{
			log.info("delete().vo : " + vo);
			int result = service.delete(vo);
			rttr.addFlashAttribute("msg", "글삭제가 성공적으로 되었습니다."); 
			log.info("delete().result : " + result);
			return "redirect:list.do?perPageNum="+perPageNum;
		}
	
}
