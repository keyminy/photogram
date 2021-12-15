package com.cos.photogramstart.web.dto;

import java.util.Map;

import com.cos.photogramstart.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
	private int code; //응답을 하기위해 코드를 만듬 1(성공), -1(실패)
	private String message;
	//private Map<String,String> errorMap;
	//private User user; //userobject 리턴(데이터를 리턴할떄)
	private T data;
}
