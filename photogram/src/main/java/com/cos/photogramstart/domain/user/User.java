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
	//@Id : No identifier specified for entity(entity에 대한 pk가 없어서 없으면 오류..)
	//@GeneratedValue : auto_increment
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 db를 따라간다.(=Identity전략쓰기)
	private int id;
	
	@Column(length=20,unique=true)
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
	
	//양방향 매핑 : image 테이블 정보가져오기(46강)
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY) //한명의 User는 여러 Image를 만들 수 있다. 1:N
	//mappedBy : 1.나는 연관관계의 주인이 아니다, 그러므로 테이블에 컬럼을 만들지마.
	//2.User를 Select할때 해당 User id로 등록된 iamge들을 다 가져와.
	@JsonIgnoreProperties({"user"}) //JSON으로 파싱할 때, Image클래스 내부에 있는 User user의 getter호출 못하게
	private List<Image> images;
	//Eager로할때
	//select user0_.id as id1_2_0_, user0_.bio as bio2_2_0_, user0_.createDate as createda3_2_0_, user0_.email as email4_2_0_, user0_.gender as gender5_2_0_, user0_.name as name6_2_0_, user0_.password as password7_2_0_, user0_.phone as phone8_2_0_, user0_.profileImageUrl as profilei9_2_0_, user0_.role as role10_2_0_, user0_.username as usernam11_2_0_, user0_.website as website12_2_0_
	//, images1_.userId as userid5_0_1_, images1_.id as id1_0_1_, images1_.id as id1_0_2_, images1_.caption as caption2_0_2_, images1_.createDate as createda3_0_2_, images1_.postImageUrl as postimag4_0_2_, images1_.userId as userid5_0_2_ 
	//from User user0_ left outer join Image images1_ on user0_.id=images1_.userId where user0_.id=?
	
	
	//db에는 항상 이게 필요해요 : 이 데이터가 언제들어왔는지?
	private LocalDateTime createDate;
	
	//db에 데이터가 insert되기 직전에 실행하기 @PrePersist
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
