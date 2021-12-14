package com.my.member.vo;

import lombok.Data;

@Data
public class LoginVO {
	private String id;
	private String pw;
	private String name;
	private int gradeNo;
	private String gradeName;
	private String photo;
}
