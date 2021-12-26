package com.cos.photogramstart.web.dto.user;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	@NotBlank
	private String name; //필수
	@NotBlank
	private String password; //필수
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	//안줘도 되는 데이터를 entity로 만들면 위험함 코드 수정필요
	public User toEntity() {
		return User.builder()
				   .name(name) //필수값으로 validation체크 필요
				   .password(password) //패스워드 기재 안하면 db에 공백이 기재되므로 validation검사 필료
				   .website(website)
				   .bio(bio)
				   .phone(phone)
				   .gender(gender)
				   .build();
	}
}
