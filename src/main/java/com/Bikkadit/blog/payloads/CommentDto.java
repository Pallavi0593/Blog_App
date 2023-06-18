package com.Bikkadit.blog.payloads;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
	
	private Integer CommentId;
    
	@NotNull
	@Size(min=10,max=100,message = "Content length At least !0 characters")
	private String content;

private UserDto user;

}
