package com.Bikkadit.blog.Services;

import java.util.List;


import com.Bikkadit.blog.payloads.PostDto;
import com.Bikkadit.blog.payloads.PostResponse;

public interface PostService {
	//create post
public PostDto Createpost(PostDto postDto,Integer userId,Integer categoryId);

//update post

public PostDto updatePost(PostDto postDto,Integer postId);

//Get All post



//DeletePost
public void DeletePostById(Integer postId);

//Get Single Post 
public PostDto getPostById(Integer postId);

//GetPost By USer
public  List<PostDto>  getPostByUser(Integer userId);

//GetPost By Category
public  List<PostDto>  getPostByCategory(Integer CategoryId);

//search post by Keyword
public  List<PostDto>  getPostByKeyword(String keyword);

//List<PostDto> getAllPost();

public List<PostDto> getAllPost(Integer pageNumber,Integer pageSize);

public PostResponse getAllPostRespnse(Integer pageNumber,Integer pageSize);

public PostResponse getAllPostRespnseBysort(Integer pageNumber,Integer pageSize,String sortBy,String sortDir);
}
