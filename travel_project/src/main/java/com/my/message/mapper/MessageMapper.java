package com.my.message.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.my.message.vo.MessageVO;
import com.webjjang.util.PageObject;

public interface MessageMapper {
		//1.메시지 리스트 보기
		public List<MessageVO> list(PageObject pageObject);
		//1-1.메시지 총 글 갯수 구하기
		public Long getTotalRow(PageObject pageObject);
		
		//2.메시지 글보기
		public MessageVO view(Long no);
		//2-1.메시지 읽음상태 처리
		public int acceptDate_update(MessageVO vo);
		
		//3.메시지 글쓰기
		public int write(MessageVO vo);
		
		//4.메시지 글삭제
		public int delete(MessageVO vo);
}
