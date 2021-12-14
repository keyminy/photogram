package com.my.image.controller;

import javax.servlet.http.HttpServletRequest;

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

import com.my.image.vo.ImageVO;

import com.my.image.service.ImageService;
import com.my.image.util.FileUtil;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/image")
@Log4j
public class ImageController {
	
private final String MODULE = "image"; //MODULE :  폴더와 동일한 역할
	
	// 저장할 위치 - 운영되는 서버에서 부터 찾는 상대위치
	String path = "/upload/image/";
	
	@Autowired
	@Qualifier("isi")
	private ImageService service;
	
	// 1.이미지 갤러리 리스트
	@GetMapping("/list.do")
	public String list(@ModelAttribute PageObject pageObject,
			Model model, HttpSession session ) 
			throws Exception {
		
		//기본값 : 이미지 한줄에 4개씩 해서 1페이지에 총 8개 뿌림.
		if(pageObject.getPerPageNum() == 10) {
			pageObject.setPerPageNum(8);  //기본 값 : 한 줄당 이미지의 갯수를 4개로
		}

		//page정보에 맞는 데이터 가져오기
		log.info("list().pageObject : " + pageObject);

		model.addAttribute("list",service.list(pageObject));
		//jsp의 정보를 리턴한다
		return MODULE + "/list";
	}
	
	//보기
	@GetMapping("/view.do")
	public String view(ImageVO vo,@ModelAttribute PageObject pageObject
			,HttpSession session,Model model) throws Exception {
		if((LoginVO)session.getAttribute("login")!=null){
			vo.setId( ( (LoginVO)session.getAttribute("login") ).getId());
		}
		model.addAttribute("vo",service.view(vo));
		log.info("view().vo : " + vo);
		return MODULE + "/view";
	}
	//등록 폼
	@GetMapping("/write.do")
	public String writeForm() {
		//글쓰기의 a href= write.do?perPageNum=${pageObject.perPageNum}에서
		//perPageNum은 jsp에서 param으로 받자.
		log.info("writeForm()으로 가기----------");
		return MODULE + "/write";
	}
	
	//등록 처리
	@PostMapping("/write.do")
	public String write(ImageVO vo,RedirectAttributes rttr,HttpSession session,
			Long perPageNum,HttpServletRequest request ) throws Exception {
		//글쓰기 처리 시 로그인- session안에 id에 대한 정보가 있기땜에 id저장가능.
		vo.setId( ( (LoginVO)session.getAttribute("login") ).getId());
		//강제로그인 테스트
		//vo.setId("test");
		log.info("write() 처리 vo : " + vo);
		//파일 저장(중복제거)한 후 + vo 객체의 fileName에 multipartFile의 정보를 저장해 놓는다.
		vo.setFileName(FileUtil.upload("/upload/"+MODULE, vo.getMultipartFile(), request));
		log.info("upload() 후 FileName : " + vo);
		//DB에 저장 저장
		service.write(vo);
		//이미지 첨부 후 redirect 하기 전 프로그램 1추간 멈추기
		//list로 갈때 파일 처리가 마무리 되지 않은 상태에서 표시해서 새로 등록한 파일이 보이지 않았음.
		Thread.sleep(1000);	
		//글쓰기 완료후 사용자에게 처리 내용을 보여줄 메시지를 보낸다.
		rttr.addFlashAttribute("msg","글 작성완료");
		
		return "redirect:list.do?perPageNum="+perPageNum;
	}
	
	//사진 바꾸기 - no,지울 기존의 파일이름(deleteFileName),바꿀파일(multiPartFile) <- ImageVO
	// page,perPageNum <-pageObject
	@PostMapping("/changeImage.do")
	public String changeImage(ImageVO vo,RedirectAttributes rttr,
			PageObject pageObject,HttpServletRequest request) throws Exception {
		log.info("changeImage().vo : " + vo);
		log.info("changeImage().pageObject : " +pageObject);
		//1.새로운 파일 올리기
		//파일 저장(중복제거)한 후 + vo 객체의 fileName에 multipartFile의 정보를 저장해 놓는다.
		vo.setFileName(FileUtil.upload("/upload/"+MODULE, vo.getMultipartFile(), request));
		log.info("changeImage() 후 FileName : " + vo);
		//2.DB정보 수정
		service.changeImage(vo);
		//3.기존의 deleteFileName지우기
		//RealPath를 잡고 -> remove하기(remove시 toFile()할때 실제적인 위치인 RealPath가 필요하기 때문)
		FileUtil.remove(FileUtil.getRealPath("", vo.getDeleteFileName(), request));
		//view로 갈때 파일 처리가 마무리 되지 않은 상태에서 표시해서 새로 등록한 파일이 보이지 않았음.
		Thread.sleep(3000);	
		rttr.addFlashAttribute("msg","이미지 파일 수정 완료");
		return "redirect:view.do?no="+vo.getNo()+
				"&page="+pageObject.getPage()+"&perPageNum="+pageObject.getPerPageNum();
	}
	//수정 폼 : 제목과 내용만 수정가능
	@GetMapping("/update.do")
	public String updateForm(ImageVO vo,@ModelAttribute PageObject pageObject,
			Model model) throws Exception {
		model.addAttribute("vo",service.view(vo));
		return MODULE + "/update";
	}
	//수정 처리
	@PostMapping("/update.do")
	public String update(ImageVO vo,PageObject pageObject,
			RedirectAttributes rttr) throws Exception {
		log.info("update().vo : " + vo);
		service.update(vo);
		rttr.addFlashAttribute("msg","글 수정 완료");
		return "redirect:view.do?no="+vo.getNo()+
				"&page="+pageObject.getPage()+"&perPageNum="+pageObject.getPerPageNum();
	}
	//삭제
	@GetMapping("/delete.do")
	public String delete(ImageVO vo,Long perPageNum,HttpServletRequest request,
			RedirectAttributes rttr) throws Exception {
		log.info("delete().vo : " + vo);
		//1.DB에서 파일이름 삭제
		int result = service.delete(vo);
		if(result == 0) throw new Exception("이미지 게시판 삭제 실패");
		//2.서버에서 파일 지우기(파일의 realPath필요)
		FileUtil.remove(FileUtil.getRealPath("", vo.getDeleteFileName(), request));
		rttr.addFlashAttribute("msg","글 삭제 완료");
		return "redirect:list.do?page=1&perPageNum="+perPageNum;
	}
}