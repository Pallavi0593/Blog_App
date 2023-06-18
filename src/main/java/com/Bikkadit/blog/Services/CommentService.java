package com.Bikkadit.blog.Services;

import com.Bikkadit.blog.payloads.CommentDto;

public interface CommentService {
 public CommentDto createComment(CommentDto commentDto,Integer postId,Integer userId);
 
 public void DeleteComment(Integer commentId);
}
