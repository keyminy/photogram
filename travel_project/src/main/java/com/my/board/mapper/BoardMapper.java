package com.my.board.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.my.board.vo.BoardVO;
import com.webjjang.util.PageObject;

public interface BoardMapper {
	
	//1.게시판 리스트 보기
	public List<BoardVO> list(PageObject pageObject);
	//1-1.게시판 총 글 갯수 구하기
	public Long getTotalRow(PageObject pageObject);
	//1-2.게시판의 댓글 갯수 뿌리기
	public void updateReplyCnt(@Param("no") Long no, 
			@Param("amount") int amount);
	
	//2.게시판 글보기
	public BoardVO view(Long no);
	
	//2-1.게시판 조회수 증가
	public void inc(Long no);
	
	//3.게시판 글쓰기
	public int write(BoardVO vo);
	
	//4.게시판 글수정
	public int update(BoardVO vo);
	
	//5.게시판 글삭제
	public int delete(BoardVO vo);
}
