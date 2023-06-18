package com.Bikkadit.blog.Services.ServiceImpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Bikkadit.blog.Repositories.CommentRepo;
import com.Bikkadit.blog.Repositories.PostRepo;
import com.Bikkadit.blog.Repositories.UserRepo;
import com.Bikkadit.blog.Services.CommentService;
import com.Bikkadit.blog.entities.Comment;
import com.Bikkadit.blog.entities.Post;
import com.Bikkadit.blog.entities.User;
import com.Bikkadit.blog.exceptions.ResourceNotFoundException;
import com.Bikkadit.blog.payloads.CommentDto;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CommentServiceImpl implements CommentService {
	@Autowired
	private CommentRepo commentRepo;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper mapper;

	@Autowired
	private UserRepo userRepo;
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId, Integer userId) {
		log.info("Entering into persistance  Call to save Comment for post with postID {}", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user", "userId", userId));
		
		Comment comment = this.mapper.map(commentDto, Comment.class);
		
		comment.setPost(post);
		comment.setUser(user);
		Comment savecomment = this.commentRepo.save(comment);
		CommentDto commentDto2 = this.mapper.map(savecomment, CommentDto.class);

		log.info("Comment Save Successfully for post with postID {}", postId);
		return commentDto2;
		
	
	}
	

	@Override
	public void DeleteComment(Integer commentId) {
		log.info("Entering into persistance  Call to Delete Comment  with commentID {}", commentId);
		Comment comment= this.commentRepo.findById(commentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment", "commentId", commentId));
		
		this.commentRepo.delete(comment);
		log.info("Comment Delete Successfullywith CommentId {}", commentId);
	}


}
