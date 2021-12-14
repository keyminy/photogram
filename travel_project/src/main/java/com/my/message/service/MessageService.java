package com.my.message.service;

import java.util.List;

import com.my.message.vo.MessageVO;
import com.webjjang.util.PageObject;

public interface MessageService {
	//1.메시지 리스트
	public List<MessageVO> list(PageObject pageObject) throws Exception;
	
	//2.메시지 글보기
	public MessageVO view(Long no,MessageVO vo) throws Exception;
	
	//3.메시지 글쓰기
	public int write(MessageVO vo) throws Exception;

	//4.메시지 글삭제
	public int delete(MessageVO vo) throws Exception;
}
