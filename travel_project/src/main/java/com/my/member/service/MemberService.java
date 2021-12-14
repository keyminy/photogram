package com.my.member.service;

import com.my.member.vo.LoginVO;
import com.my.member.vo.MemberVO;

public interface MemberService {

	// * 회원 가입 처리
	public int register(MemberVO vo) throws Exception;
	// * 아이디 중복 체크
	public String checkId(MemberVO vo) throws Exception;
	
	// * 로그인 처리 (Post)
	public LoginVO login(LoginVO vo) throws Exception;

}
