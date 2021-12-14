package com.my.board.mapper;

import java.util.List;
import java.util.Map;

import com.my.board.vo.BoardReplyVO;
import com.webjjang.util.PageObject;

public interface BoardReplyMapper {
	//1.댓글 리스트 보기
	//매개변수로 pageObject와 no를 2개 넘겨야함..
	// Map - "pageObject" : pageObject , "no" : no
	public List<BoardReplyVO> list(Map<String,Object> map);
	
	//1-1.댓글 총 글 갯수 구하기
	public Long getTotalRow(Long no);
	
	// 2.댓글 보기 생략 - (리스트에 다 표시가 되므로)
	
	//3.댓글 글쓰기
	public int write(BoardReplyVO vo);
	
	//4.댓글 글수정
	public int update(BoardReplyVO vo);
	
	//5.댓글 글삭제
	public int delete(BoardReplyVO vo);
}
