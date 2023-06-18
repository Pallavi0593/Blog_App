package com.Bikkadit.blog.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bikkadit.blog.entities.Category;
import com.Bikkadit.blog.entities.Post;
import com.Bikkadit.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	public List<Post>  findByUser(User user);
	public List<Post>  findByCategory(Category catogery);
	
	public List<Post> findByTitleContaining(String title);
	
	//if error InvalidDataAccessApi
	//@Query("select p from post p where p title Like:key")
	//public List<Post> searchByTitle(@Param("Key")String title);
	
}
