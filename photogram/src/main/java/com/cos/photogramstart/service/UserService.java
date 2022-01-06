package com.cos.photogramstart.service;

import java.util.function.Supplier;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
	
	private final UserRepository userRepository;
	private final SubscribeRepository subscribeRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId,int principalId) { //principalId:현재 로그인한 사용자 id
		//해당 유저가 들고 있는 모든 사진 가져오기
		// SELECT * FROM image WHERE userid = :userid; 
		UserProfileDto dto = new UserProfileDto();
		User userEntity = userRepository.findById(pageUserId)
										.orElseThrow(()->{
											throw new CustomException("해당 프로필 페이지는 없는 페이지 입니다.");
										});
		//System.out.println("======================");
		//userEntity.getImages().get(0); //이거할때 fetch.LAZY 발동하여 
		//select images0_.userId as userid5_0_0_, images0_.id as id1_0_0_, images0_.id as id1_0_1_, images0_.caption as caption2_0_1_
		//, images0_.createDate as createda3_0_1_, images0_.postImageUrl as postimag4_0_1_, images0_.userId as userid5_0_1_ 
		//from Image images0_ where images0_.userId=?
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId==principalId); //1이면 페이지 주인,-1은 주인이 아님
		dto.setImageCount(userEntity.getImages().size());
		
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		//이제 dto에 담으면됨 : 이처럼 dto를 만들면 페이지에 출력만 하면되어 효과적임
		dto.setSubscribeState(subscribeState==1);
		dto.setSubscribeCount(subscribeCount);
		
		
		return dto;
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
