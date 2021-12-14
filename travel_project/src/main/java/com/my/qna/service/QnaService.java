package com.my.qna.service;

import java.util.List;

import com.my.qna.vo.QnaVO;
import com.webjjang.util.PageObject;

public interface QnaService {
	
	//1.qna게시판 리스트
	public List<QnaVO> list(PageObject pageObject) throws Exception;
	
	//2.qna게시판 글보기
	public QnaVO view(Long no,String mode) throws Exception;
	
	//3.qna게시판 질문하기
	public int question(QnaVO vo) throws Exception;
	
	//4.qna게시판 답변하기
	public int answer(QnaVO vo) throws Exception;
	
	//4.qna게시판 글수정
	public int update(QnaVO vo) throws Exception;
	
	//5.qna게시판 글삭제
	public int delete(QnaVO vo) throws Exception;
}
