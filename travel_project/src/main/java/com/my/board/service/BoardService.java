package com.my.board.service;

import java.util.List;

import com.my.board.vo.BoardVO;
import com.webjjang.util.PageObject;

public interface BoardService {
	
	//1.게시판 리스트
	public List<BoardVO> list(PageObject pageObject) throws Exception;
	
	//2.게시판 글보기
	public BoardVO view(Long no,String mode) throws Exception;
	
	//3.게시판 글쓰기
	public int write(BoardVO vo) throws Exception;
	
	//4.게시판 글수정
	public int update(BoardVO vo) throws Exception;
	
	//5.게시판 글삭제
	public int delete(BoardVO vo) throws Exception;
}
