package com.cos.photogramstart.service;

import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public User 회원프로필(int userid) {
		//해당 유저가 들고 있는 모든 사진 가져오기
		// SELECT * FROM image WHERE userid = :userid; 
		User userEntity = userRepository.findById(userid)
										.orElseThrow(()->{
											throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
										});
		System.out.println("======================");
		userEntity.getImages().get(0); //이거할때 fetch.LAZY 발동하여 
		//select images0_.userId as userid5_0_0_, images0_.id as id1_0_0_, images0_.id as id1_0_1_, images0_.caption as caption2_0_1_, images0_.createDate as createda3_0_1_, images0_.postImageUrl as postimag4_0_1_, images0_.userId as userid5_0_1_ 
		//from Image images0_ where images0_.userId=?
		return userEntity;
	}
	
	@Transactional
	public User 회원수정(int id,User user) {
		//1.영속화하기
//		User userEntity= userRepository.findById(126).orElseThrow(new Supplier<IllegalArgumentException>() {
//			@Override
//			public IllegalArgumentException get() {
//				return new IllegalArgumentException("찾을 수 없는 id 입니다.");
//			}
//		});
		User userEntity= userRepository.findById(id)
				.orElseThrow(()->{return new CustomValidationApiException("찾을 수 없는 id 입니다.");});
		//2.영속화된 오브젝트를 수정 - 더티체킹일어나서 -> (업데이트가 완료됨)
		userEntity.setName(user.getName());
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		userEntity.setPassword(encPassword);
		
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity; //더티체킹이 일어나서 업데이트가 완료됨.
	}
}
