package com.my.qna.vo;

import java.util.Date;

import lombok.Data;

import lombok.ToString;

@Data
@ToString
public class QnaVO {
	
	private long no;
	private String title;
	private String content;
	private String id;
	private String name;
	private Date writeDate;
	private long hit;
	private long refNo;
	private long ordNo;
	private long levNo;
	private long parentNo;
}
