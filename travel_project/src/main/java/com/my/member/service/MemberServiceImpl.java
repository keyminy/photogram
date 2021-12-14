package com.my.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.member.mapper.MemberMapper;
import com.my.member.vo.LoginVO;
import com.my.member.vo.MemberVO;

@Service
@Qualifier("msi")
public class MemberServiceImpl implements MemberService{
	
	@Autowired
	private MemberMapper mapper;
	
	@Override
	public LoginVO login(LoginVO vo) throws Exception {
		return mapper.login(vo);
	}

	@Override
	public int register(MemberVO vo) throws Exception {
		return mapper.register(vo);
	}

	@Override
	public String checkId(MemberVO vo) throws Exception{
		String result = mapper.checkId(vo);
		return result;
	}
	
}
