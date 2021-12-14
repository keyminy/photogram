package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service // 1.Ioc등록됨 , 2.트랜잭션 관리를 해줌
@RequiredArgsConstructor
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional
	public User 회원가입(User user) {
		//password 암호화 과정(20강)
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); //암호화된 pw가됨
		user.setPassword(encPassword); //user객체에 암호화된 pw넣기
		user.setRole("ROLE_USER"); //회원가입한 모든 user에게 ROLE_USER라는 권한주기
		//회원가입 진행(Repository가 필요하다)
		User userEntity = userRepository.save(user);
		return userEntity;
	}
}
