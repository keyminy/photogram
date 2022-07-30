package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //19강 빌드업 패턴쓰기위해?..
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //db에 테이블을 생성하기
public class User {
	//@Id : No identifier specified for entity(entity에 대한 pk가 없어서..)
	//@GeneratedValue : auto_increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 db를 따라간다.(=Identity전략쓰기)
	private int id;
	@Column(length = 20, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website; //웹사이트(null가능)
	private String bio; //자기소개(null가능)
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	private String profileImageUrl; //작성자 사진
	private String role; //권한
	
	@OneToMany(mappedBy = "user",fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"user"})
	private List<Image> images;
	
	//db에는 항상 이게 필요해요 : 이 데이터가 언제들어왔는지?
	private LocalDateTime createDate;
	
	
	//db에 데이터가 insert되기 직전에 실행하기 @PrePersist
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", name=" + name + ", website="
				+ website + ", bio=" + bio + ", email=" + email + ", phone=" + phone + ", gender=" + gender
				+ ", profileImageUrl=" + profileImageUrl + ", role=" + role +  ", createDate="
				+ createDate + "]";
	}
	
}
