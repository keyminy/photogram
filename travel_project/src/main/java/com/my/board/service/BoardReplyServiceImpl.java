package com.my.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.my.board.mapper.BoardMapper;
import com.my.board.mapper.BoardReplyMapper;
import com.my.board.vo.BoardReplyVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;


@Service
@Qualifier("brsi")
@Log4j
public class BoardReplyServiceImpl implements BoardReplyService {
	
	@Autowired
	private BoardReplyMapper mapper;
	@Autowired
	private BoardMapper boardMapper;
	
	
	@Override
	public List<BoardReplyVO> list(PageObject pageObject, Long no) throws Exception {
		//게시판(부모) 글 번호(no)에 맞는 전체 댓글 갯수 가져오기
		pageObject.setTotalRow(mapper.getTotalRow(no));
		log.info("list().PageObject : " + pageObject + ", no : " + no);
		//Map만들기 ["pageObject" : pageObject , "no" : no]
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("pageObject", pageObject);
		map.put("no", no);
		return mapper.list(map);
	}

	@Transactional
	@Override
	public int write(BoardReplyVO vo) throws Exception {
		log.info("wirte().vo : " + vo);
		boardMapper.updateReplyCnt(vo.getNo(), 1);
		return mapper.write(vo);
	}

	@Override
	public int update(BoardReplyVO vo) throws Exception {
		log.info("update().vo : " + vo);
		return mapper.update(vo);
	}

	@Transactional
	@Override
	public int delete(BoardReplyVO vo) throws Exception {
		log.info("delete().vo : " + vo);
		boardMapper.updateReplyCnt(vo.getNo(), -1);
		return mapper.delete(vo);
	}
}
