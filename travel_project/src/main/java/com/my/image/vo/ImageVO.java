package com.my.image.vo;

import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ImageVO {
	private Long no;
	private String title;
	private String content;
	private String id;
	private String name; //member테이블에 있는것 가져옴 join필요
	private Date writeDate;
	private String fileName;
	private String myLiked;
	private long likeCnt;
	
	//파일 한개를 첨부하는걸로 작성함. - 처리를 위한 중간단계 개념의 변수.
	//UploadController에서 넘어오는 파일의 타입이 MultipartFile이라 String fileName과 안맞음.
	private MultipartFile multipartFile; //input tag에 name값을 이걸로 해야한다.
	//그 후, multipartFile이 받고 fileName에 넣어주어서 하자. 
	//즉,fileName은 DB와 연결되어있는거고, multipartFile은 write.jsp와 연결되어있다.
	
	//이미지 update,delete시 지워질 파일 정보
	private String deleteFileName;
}
