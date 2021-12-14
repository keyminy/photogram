package com.my.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.my.board.mapper.BoardMapper;
import com.my.board.vo.BoardVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
@Qualifier("bsi")
public class BoardServiceImpl implements BoardService{
	
	@Autowired
	private BoardMapper mapper;
	
	@Override
	public List<BoardVO> list(PageObject pageObject) throws Exception {
		pageObject.setTotalRow(mapper.getTotalRow(pageObject));
		//setTotalRow : startRow와 endRow계산
		log.info("service 계산 후 pageObject : " + pageObject);
		return mapper.list(pageObject);
	}

	@Override
	public BoardVO view(Long no, String mode) throws Exception {
		if(mode.equals("view")) {
			mapper.inc(no);
		}
		return mapper.view(no);
	}

	@Override
	public int write(BoardVO vo) throws Exception {	
		return mapper.write(vo);
	}

	@Override
	public int update(BoardVO vo) throws Exception {
		return mapper.update(vo);
	}

	@Override
	public int delete(BoardVO vo) throws Exception {
		return mapper.delete(vo);
	}
	
}
