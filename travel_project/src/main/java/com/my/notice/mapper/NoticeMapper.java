package com.my.notice.mapper;

import java.util.List;


import com.my.notice.vo.NoticeVO;
import com.webjjang.util.PageObject;

public interface NoticeMapper {
	//1.게시판 리스트 보기
		public List<NoticeVO> list(PageObject pageObject);
		//1-1.게시판 총 글 갯수 구하기
		public Long getTotalRow(PageObject pageObject);
		
		//2.게시판 글보기
		public NoticeVO view(Long no);
		
		//3.게시판 글쓰기
		public int write(NoticeVO vo);
		
		//4.게시판 글수정
		public int update(NoticeVO vo);
		
		//5.게시판 글삭제
		public int delete(NoticeVO vo);
}
