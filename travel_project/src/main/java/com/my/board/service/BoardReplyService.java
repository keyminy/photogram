package com.my.board.service;

import java.util.List;

import com.my.board.vo.BoardReplyVO;
import com.webjjang.util.PageObject;

public interface BoardReplyService {
	
	//1.댓글 리스트 보기
	public List<BoardReplyVO> list(PageObject pageObject,Long no) throws Exception;
	
	//3.댓글 글쓰기
	public int write(BoardReplyVO vo) throws Exception;
	
	//4.댓글 글수정
	public int update(BoardReplyVO vo) throws Exception;
	
	//5.댓글 글삭제
	public int delete(BoardReplyVO vo) throws Exception;
}
