package com.cos.photogramstart.domain.likes;

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

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
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
@Table(
		uniqueConstraints = {
			@UniqueConstraint(
				name="likes_uk",
				columnNames = {"imageId","userId"}
			)
		}
	)
public class Likes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	/*좋아요는 어떤 이미지를 누가 좋아했는지 알아야함*/
	@JoinColumn(name="imageId")
	@ManyToOne
	private Image image;//하나의 이미지는 여러개의 좋아요 가능 N:1
	//하나의 좋아요를 여러 이미지가?? 말 성립 X
	
	//오류가 터지고 나서 User의 Image안들고 가게 해보자.
	@JoinColumn(name="userId")
	@JsonIgnoreProperties({"images"})
	@ManyToOne
	private User user;//한 명의 유저는 좋아요 여러번 가능 N:1
	
	private LocalDateTime createDate;
	//db에 데이터가 insert되기 직전에 실행하기 @PrePersist
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
