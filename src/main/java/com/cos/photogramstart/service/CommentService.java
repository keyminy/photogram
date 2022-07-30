package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.comment.CommentRepository;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CommentService {

	private final CommentRepository commentRepository;
	private final UserRepository userRepository;
	
	@Transactional
	public Comment 댓글쓰기(String content,int imageId,int userId) {
		//댓글 쓰기 : 누가 썻는지 알아야함 <- 매개변수로 받자
		
		/* Tip : 가짜객체 만들기(id값만 담아서 insert하기)
		 * 실제로 DB에는 해당 ID값만 들어가면 되니 */
		Image image = new Image();
		image.setId(imageId);
		
		User userEntity = userRepository.findById(userId).orElseThrow(()->{
			throw new CustomApiException("해당 userId를 찾지 못했습니다.");
		});
		
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setUser(userEntity);
		comment.setImage(image);
		
		return commentRepository.save(comment);
	}
	
	@Transactional
	public void 댓글삭제(int id) {
		try {
			commentRepository.deleteById(id);
		} catch (Exception e) {
			throw new CustomApiException(e.getMessage());
		}
	}
}
