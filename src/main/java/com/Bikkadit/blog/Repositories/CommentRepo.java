package com.Bikkadit.blog.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Bikkadit.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{


}
