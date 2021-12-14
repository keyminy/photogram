package com.my.image.mapper;

import java.util.List;

import com.my.image.vo.ImageVO;

import com.webjjang.util.PageObject;

public interface ImageMapper {

	//1.이미지 갤러리 리스트
	public List<ImageVO> list(PageObject pageObject);
	
	//1-1.전체 데이터의 갯수 - 화면 표시용 - 페이지 네이션
	public Long getTotalRow(PageObject pageObject);
	
	//2.이미지 보기
	public ImageVO view(ImageVO vo);
	
	//3.이미지 글쓰기
	public int write(ImageVO vo);
	
	//4-1.이미지 파일 수정
	public int changeImage(ImageVO vo);
	
	//4-2.이미지 게시판 정보 수정
	public int update(ImageVO vo);
	
	//5.이미지 지우기.
	public int delete(ImageVO vo);
	
	//6.좋아요 처리
	public int dolike(ImageVO vo);
	//7.좋아요 취소
	public int likeCancle(ImageVO vo);
}