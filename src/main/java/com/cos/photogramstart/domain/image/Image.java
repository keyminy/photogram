package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String caption; //오늘 나 너무 피곤해!
	private String postImageUrl; //file이 아닌 url경로이다.
	//사진을 전송받아 그 사진을 서버의 특정 폴더에 저장하고, db에는 그 저장된 경로를 insert함
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name = "userId")
	@ManyToOne(fetch = FetchType.EAGER)
	private User user;
	
	//이미지 좋아요
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image")
	private List<Likes> likes;
	
	@Transient
	private boolean likeState;
	
	@Transient
	private int likeCount;
	
	//이미지 댓글 넣기.
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy="image")
	private List<Comment> comments;
	
	//db에는 항상 이게 필요해요 : 이 데이터가 언제들어왔는지?
	private LocalDateTime createDate;
	
	//db에 데이터가 insert되기 직전에 실행하기 @PrePersist
	@PrePersist
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
