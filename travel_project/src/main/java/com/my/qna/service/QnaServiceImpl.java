package com.my.qna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.qna.mapper.QnaMapper;
import com.my.qna.vo.QnaVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Service
@Qualifier("qsi")
@Log4j
public class QnaServiceImpl implements QnaService{
	
	@Autowired
	private QnaMapper mapper;
	
	@Override
	public List<QnaVO> list(PageObject pageObject) throws Exception {
		pageObject.setTotalRow(mapper.getTotalRow());
		return mapper.list(pageObject);
	}

	@Override
	public QnaVO view(Long no, String mode) throws Exception {
		if(mode.equals("view")) {
			mapper.inc(no);
		}
		return mapper.view(no);
	}
	@Override
	public int question(QnaVO vo) throws Exception {
		return mapper.question(vo);
	}
	@Transactional
	@Override
	public int answer(QnaVO vo) throws Exception {
		mapper.inc_ordNo(vo);
		return mapper.answer(vo);
	}

	@Override
	public int update(QnaVO vo) throws Exception {
		return mapper.update(vo);
	}

	@Override
	public int delete(QnaVO vo) throws Exception {
		return mapper.delete(vo);
	}
	
}
