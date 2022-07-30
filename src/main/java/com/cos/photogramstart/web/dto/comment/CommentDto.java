package com.cos.photogramstart.web.dto.comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.sun.istack.NotNull;

import lombok.Data;

@Data
public class CommentDto {
	
	@NotBlank
	private String content;
	
	@NotNull
	private Integer imageId;
	//toEntity가 필요 없다.
}
