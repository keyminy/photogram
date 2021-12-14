package com.my.notice.service;

import java.util.List;

import com.my.notice.vo.NoticeVO;
import com.webjjang.util.PageObject;

public interface NoticeService {
	//1.게시판 리스트
	public List<NoticeVO> list(PageObject pageObject) throws Exception;
	
	//2.게시판 글보기
	public NoticeVO view(Long no) throws Exception;
	
	//3.게시판 글쓰기
	public int write(NoticeVO vo) throws Exception;
	
	//4.게시판 글수정
	public int update(NoticeVO vo) throws Exception;
	
	//5.게시판 글삭제
	public int delete(NoticeVO vo) throws Exception;
}
