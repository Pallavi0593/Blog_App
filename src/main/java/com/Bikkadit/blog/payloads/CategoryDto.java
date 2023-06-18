package com.Bikkadit.blog.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {
	

	   private Integer categoryId;
	   
	   @NotBlank
	   @Size(min=4,max=1000,message = "Minimum Size of Category titile is 4")
	    private String catogorytitle;
	   
	   @NotBlank
	   @Size(min=10,message = "Minimum Size of Category titile is 10")
	 private String catogorydesciption;
	   
	 
	 //  private List<Post> posts = new ArrayList<>();
}
