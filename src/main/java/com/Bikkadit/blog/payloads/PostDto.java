package com.Bikkadit.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
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
public class PostDto {

	private Integer postId;

	@NotBlank
	@Size(min = 10, max = 200, message = "Title Length too Small")
	private String title;

	@NotBlank
	@Size(min = 10, message = "Minimum Size of Category titile is 10")
	private String content;

	@NotNull
	@Valid
	private String imagename;

	private Date addedDate;

	private CategoryDto category;

	private UserDto user;

	private Set<CommentDto> comments = new HashSet<>();
}
