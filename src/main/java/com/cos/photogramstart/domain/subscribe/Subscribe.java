package com.cos.photogramstart.domain.subscribe;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder //19강 빌드업 패턴쓰기위해?..
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //db에 테이블을 생성하기
@Table(
		uniqueConstraints = {
			@UniqueConstraint(
				name="subscribe_uk",
				columnNames = {"fromUserId","toUserId"}
			)
		}
	)
public class Subscribe {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 db를 따라간다.(=Identity전략쓰기)
	private int id;
	
	//구독하는 유저
	@JoinColumn(name = "fromUserId")
	@ManyToOne // subscribe가 many,user가 one
	private User fromUser;
	//구독 받는 유저
	@JoinColumn(name = "toUserId")
	@ManyToOne
	private User toUser;
	
	//db에는 항상 이게 필요해요 : 이 데이터가 언제들어왔는지?
	private LocalDateTime createDate;
	
	
	//db에 데이터가 insert되기 직전에 실행하기 @PrePersist
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
