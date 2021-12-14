package com.my.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.notice.mapper.NoticeMapper;
import com.my.notice.service.NoticeServiceImpl;
import com.my.notice.vo.NoticeVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
@Qualifier("nsi")
public class NoticeServiceImpl implements NoticeService {

	@Autowired
	private NoticeMapper mapper;
	
	@Override
	public List<NoticeVO> list(PageObject pageObject) throws Exception {
		pageObject.setTotalRow(mapper.getTotalRow(pageObject));
		//setTotalRow : startRow와 endRow계산
		log.info("service 계산 후 pageObject : " + pageObject);
		return mapper.list(pageObject);
	}

	@Override
	public NoticeVO view(Long no) throws Exception {
		return mapper.view(no);
	}

	@Override
	public int write(NoticeVO vo) throws Exception {	
		return mapper.write(vo);
	}

	@Override
	public int update(NoticeVO vo) throws Exception {
		return mapper.update(vo);
	}

	@Override
	public int delete(NoticeVO vo) throws Exception {
		return mapper.delete(vo);
	}

}
