package com.my.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.my.image.util.FileUtil;
import com.my.member.service.MemberService;
import com.my.member.vo.LoginVO;
import com.my.member.vo.MemberVO;

import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/member")
@Log4j
public class MemberController {
	
	@Autowired
	@Qualifier("msi")
	private MemberService service;
	
	private final String MODULE = "member";

	//* 회원 가입 폼
	@GetMapping("/register.do")
	public String registerForm() {
		return MODULE+"/register";
	}
	//* 회원 가입 처리
	@PostMapping("/register.do")
	public String register(MemberVO vo,HttpServletRequest request,
			RedirectAttributes rttr) throws Exception {
		log.info("register().vo : " + vo);
		//1.파일을 서버에 올리기
		vo.setPhoto(FileUtil.upload("/upload/"+MODULE, vo.getMultipartFile(), request));
		//2.DB(photo)에 저장
		service.register(vo);
		rttr.addFlashAttribute("msg","회원가입이 완료되었습니다.");
		return "redirect:/image/list.do";
	}
	//* 아이디 중복 체크 - ajax사용 /ajax URL 밑으로 만들기 : MemberAjaxController에 맵핑
	public String idCheck(String id) {
		return "";
	}

	//* 로그인 폼(Get) - 아이디,비밀번호 정보 받기.
	@GetMapping("/loginForm.do")
	public String loginForm() {
		return MODULE + "/login";
	}
	//* 로그인 처리 (Post)
	@PostMapping("/login.do")
	public String login(LoginVO vo,HttpSession session,RedirectAttributes rttr) throws Exception {
		log.info("login().vo : " + vo);//넘어온 vo확인
		LoginVO loginVO = service.login(vo); //id,pw가 틀리면 loginVO = null(데이터를 못가져온다)
		log.info("login().loginVO : " + loginVO);//넘어온 loginVO확인
		//예외처리
		if(loginVO == null) throw new Exception("아이디나 비밀번호를 확인해주세요.");
		session.setAttribute("login", loginVO);
		rttr.addFlashAttribute("msg","정상적으로 로그인이 되었습니다.");
		return "redirect:/main/main.do";
	}
	//* 로그아웃
	@GetMapping("/logout.do")
	public String logout(HttpSession session,RedirectAttributes rttr) {
		//로그아웃 처리
		session.removeAttribute("login"); // session을 다 지우진 않고 로그인한 정보만 지움.
		rttr.addFlashAttribute("msg","정상적으로 로그아웃이 되었습니다.");
		return "redirect:/image/list.do";
	}

}
