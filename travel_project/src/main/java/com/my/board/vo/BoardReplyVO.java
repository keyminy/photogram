package com.my.board.vo;

import java.util.Date;

import lombok.Data;

@Data
public class BoardReplyVO {
	private Long rno;
	private Long no;
	private String content;
	private String writer;
	private Date writeDate;
	private String pw;
}
