package com.my.image.service;

import java.util.List;

import com.my.image.vo.ImageVO;

import com.webjjang.util.PageObject;

public interface ImageService {

	// 1.이미지 갤러리 리스트
	public List<ImageVO> list(PageObject pageObject) throws Exception;

	// 2.이미지 보기 : no만 넘기면 되므로
	public ImageVO view(ImageVO vo) throws Exception;

	// 3.이미지게시판 글 작성.
	public int write(ImageVO vo) throws Exception;

	// 4-1.이미지 파일 수정
	public int changeImage(ImageVO vo) throws Exception;
	// no와 fileName이 바뀌어야하므로 ImageVO받기

	// 4-2.이미지 게시판 수정처리 - 제목과 내용만 수정가능.
	public int update(ImageVO vo) throws Exception;

	// 5.이미지 삭제
	public int delete(ImageVO vo) throws Exception;
	
	//6.좋아요 처리

	public int dolike(ImageVO vo) throws Exception;

	//7.좋아요 취소
	public int likeCancle(ImageVO vo) throws Exception;
}
