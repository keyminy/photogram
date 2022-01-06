package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.subscribe.Subscribe;
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
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 db를 따라간다.(=Identity전략쓰기)
	private int id;
	private String caption; //오늘 나 너무 피곤해!
	private String postImageUrl; //file이아닌 Url이라고 적힌 이유?(경로를 저장함)
//사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장할것임 -> DB에는 그 저장된 경로를 insert함
	
	@JsonIgnoreProperties({"images"}) //user가 가져오는 images는 가져오지마!
	@JoinColumn(name="userId") //FK의 이름 정하기
	@ManyToOne(fetch = FetchType.EAGER) //EAGER전략 : JOIN해서 가져옴
	private User user; //이미지를 누가 업로드 했는지 알아야하므로
	
	//<나중에 필요한거> : 1.이미지 좋아요
	
	//2.이미지에 댓글 기능넣을때
	
	//db에는 항상 이게 필요해요 : 이 데이터가 언제들어왔는지?
	private LocalDateTime createDate;
	
	//db에 데이터가 insert되기 직전에 실행하기 @PrePersist
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	//오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User부분을 출력되지 않게함
	@Override
	public String toString() {
		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl 
				+ ", createDate=" + createDate + "]";
	}
	
	
}
