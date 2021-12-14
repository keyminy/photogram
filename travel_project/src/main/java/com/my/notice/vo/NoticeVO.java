package com.my.notice.vo;

import lombok.Data;

@Data
public class NoticeVO {
	private long no;
	private String title, content, startDate, endDate, writeDate, updateDate;
}
