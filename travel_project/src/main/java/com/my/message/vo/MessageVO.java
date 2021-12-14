package com.my.message.vo;

import lombok.Data;

@Data
public class MessageVO {
	private long no;
	private String content, sender, senderName, sendDate, accepter, accepterName, acceptDate;
}
