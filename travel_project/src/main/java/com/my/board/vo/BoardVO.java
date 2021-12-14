package com.my.board.vo;

import java.util.Date;

import lombok.Data;

@Data
public class BoardVO {
	private Long no;
	private String title;
	private String content;
	private String writer;
	private Date writeDate;
	private Long hit;
	private String pw;
	
	private int replyCnt;
}
