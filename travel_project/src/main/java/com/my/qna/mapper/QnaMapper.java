package com.my.qna.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.my.board.vo.BoardVO;
import com.my.member.vo.LoginVO;
import com.my.qna.vo.QnaVO;
import com.webjjang.util.PageObject;

public interface QnaMapper {
	//1.qna 리스트 보기
	public List<QnaVO> list(PageObject pageObject);
	//1-1.qna 총 글 갯수 구하기
	public Long getTotalRow();

	//2.qna 글보기
	public QnaVO view(Long no);
	
	//2-1.qna 조회수 증가
	public void inc(Long no);
	
	//3.qna 질문하기
	public int question(QnaVO vo);
	
	//4-1.qna 답변하기 전 ordNo올리기
	public int inc_ordNo(QnaVO vo);
	
	//4-2.qna 답변하기
	public int answer(QnaVO vo);
	
	//4.게시판 글수정
	public int update(QnaVO vo);
	
	//5.게시판 글삭제
	public int delete(QnaVO vo);
}
