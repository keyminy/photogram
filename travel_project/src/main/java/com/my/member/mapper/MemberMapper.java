package com.my.member.mapper;

import com.my.member.vo.LoginVO;
import com.my.member.vo.MemberVO;

public interface MemberMapper {
	public LoginVO login(LoginVO vo);
	public int register(MemberVO vo);
	public String checkId(MemberVO vo);
}
