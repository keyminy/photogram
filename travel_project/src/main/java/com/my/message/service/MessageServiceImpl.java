package com.my.message.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.message.service.MessageServiceImpl;
import com.my.message.mapper.MessageMapper;
import com.my.message.vo.MessageVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
@Qualifier("msi")
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageMapper mapper;
	
	@Override
	public List<MessageVO> list(PageObject pageObject) throws Exception {
		pageObject.setTotalRow(mapper.getTotalRow(pageObject));
		//setTotalRow : startRow와 endRow계산
		log.info("service 계산 후 pageObject : " + pageObject);
		return mapper.list(pageObject);
	}

	@Transactional
	@Override
	public MessageVO view(Long no,MessageVO vo) throws Exception {
		mapper.acceptDate_update(vo); //읽지않은 메시지를 읽음으로 처리
		return mapper.view(no);
	}

	@Override
	public int write(MessageVO vo) throws Exception {	
		return mapper.write(vo);
	}

	@Override
	public int delete(MessageVO vo) throws Exception {
		return mapper.delete(vo);
	}
}
