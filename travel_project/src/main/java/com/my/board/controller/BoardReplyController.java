package com.my.board.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.my.board.service.BoardReplyService;
import com.my.board.vo.BoardReplyVO;
import com.webjjang.util.PageObject;

import lombok.extern.log4j.Log4j;

@RequestMapping("/reply")
@RestController
@Log4j
public class BoardReplyController {
	
	@Autowired
	@Qualifier("brsi")
	private BoardReplyService service;
	
	//1.게시판 댓글 리스트
	//url : localhost:8081/reply/list.do?no=127
	@GetMapping(value = "list.do",
			produces = {
					MediaType.APPLICATION_XML_VALUE,
					MediaType.APPLICATION_JSON_UTF8_VALUE})
	public ResponseEntity<Map<String,Object>> list(
			@RequestParam(defaultValue = "1") long repPage,
			@RequestParam(defaultValue = "5") long repPerPageNum,
			long no) throws Exception{
		Map<String,Object> map = new HashMap<String,Object>();
		
		//댓글에 대한 페이지 정보
		//생성자에의해 page : 1,perPageNum : 5로 셋팅 + startRow,endRow 계산됨
		PageObject replyPageObject = new PageObject(repPage,repPerPageNum);
		log.info("list().replyPageObject : " + replyPageObject +" , no : " + no);
		
		map.put("pageObject", replyPageObject);
		map.put("list",service.list(replyPageObject, no));
		//자바스크립트로 map정보 JSON으로 넘어간다.
		return new ResponseEntity<>(map,HttpStatus.OK);
	}
	
	//2.댓글 보기 (/view.do) : 생략 - 리스트에 내용이 다 나오므로..
	
	//3-1.댓글 등록 폼 : 생략 - /board/view.do에 포함 되어 있다.
	
	//3-2.댓글 등록 처리 : /write.do - post
	@PostMapping(value="/write.do",
			consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
			produces = {"application/text; charset=utf-8"})
	public ResponseEntity<String> write(@RequestBody BoardReplyVO vo) throws Exception{
		log.info("write().vo : " + vo);
		int writeCount = service.write(vo);
		log.info("작성 결과 : " + writeCount);
		return new ResponseEntity<>("댓글이 등록 되었습니다.",HttpStatus.OK);
	}
	
	//4-1.댓글 수정 폼 : 생략 - /board/view.do에 포함되어있다.
	
	//4-2.댓글 수정처리 : /update.do - PatchMapping 이용.
	//넘어오는 데이터 : JSON, parsing필요
	@PatchMapping(value="/update.do",
			consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
			produces = {"application/text; charset=utf-8"})
	public ResponseEntity<String> update(@RequestBody BoardReplyVO vo) throws Exception{
		log.info("update().vo : " + vo);
		
		String msg = "게시판 글 수정이 성공적으로 되었습니다.";
		HttpStatus status = HttpStatus.OK;
		
		int updateCount = service.update(vo);
		if(updateCount==0) {
			msg= "게시판 수정 실패 - 정보를 확인해 주세요";
			status = HttpStatus.NOT_MODIFIED; //304번
		}
		log.info("수정 결과 : " + msg);
		return new ResponseEntity<>(msg,status);
	}
	
	//5.댓글 삭제 : /delete.do - DeleteMapping이용
	@DeleteMapping(value="/delete.do",
			consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE},
			produces = {"application/text; charset=utf-8"})
	public ResponseEntity<String> delete(@RequestBody BoardReplyVO vo) throws Exception{
		log.info("delete().vo : " + vo);
		String msg = "게시판 글삭제가 성공적으로 되었습니다.";
		HttpStatus status = HttpStatus.OK;
		
		int deleteCount = service.delete(vo);
		if(deleteCount==0) {
			msg= "게시판 삭제 실패 - 비밀번호가 틀렸습니다.";
			status = HttpStatus.NOT_MODIFIED; //304번
		}
		log.info("수정 결과 : " + msg);
		return new ResponseEntity<>(msg,status);
	}
}
