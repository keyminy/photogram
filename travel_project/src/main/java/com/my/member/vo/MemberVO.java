package com.my.member.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MemberVO {
	private String id;
	private String pw;
	private String name;
	private String gender;
	//다음과 같은 날짜형식으로 받겠다.
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date birth;
	private String tel;
	private String email;
	private Date regDate;
	private Date conDate;
	private String status;
	private int gradeNo;
	private String gradeName;
	private String photo;
	
	//파일 한개를 첨부하는걸로 작성함. - 처리를 위한 중간단계 개념의 변수.
	//input:file에서 넘어오는 파일의 타입이 MultipartFile이라 String fileName과 안맞음.
	private MultipartFile multipartFile; //input tag에 name값을 이걸로 해야한다.
	//그 후, multipartFile이 받고 photo에 넣어주어서 하자. 
	//즉,photo는 DB와 연결되어있는거고, multipartFile은 register.jsp와 연결되어있다.
	
	//이미지 update,delete시 지워질 파일 정보
	private String deleteFileName;
}
