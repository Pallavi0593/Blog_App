package com.Bikkadit.blog.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bikkadit.blog.Services.CommentService;
import com.Bikkadit.blog.config.Appconstants;
import com.Bikkadit.blog.payloads.CommentDto;

@RestController
@RequestMapping("/api")
public class CommentController {

	Logger logger = LoggerFactory.getLogger(CommentController.class);
	@Autowired
	private CommentService commentService;

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to Create Comment on Post by user
	 * @param commentDto
	 * @param postId
	 * @param userId
	 * @return
	 */
	@PostMapping("user/{userId}/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId,
			@PathVariable Integer userId) {

		logger.info("Request Entering for Creating New Record comment for Post where PostId {} by User userId {}",
				+postId, userId);
		CommentDto comment = this.commentService.createComment(commentDto, postId, userId);
		logger.info("Comment Crreated Successfully by user  for Post where PostId {} and userId {}", +postId, userId);
		return new ResponseEntity<CommentDto>(comment, HttpStatus.CREATED);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to Delete Comment from Post
	 * @param commentId
	 * @return
	 */
	@DeleteMapping("/comments/{commentId}")
	public ResponseEntity<String> DeleteComment(@PathVariable Integer commentId) {
		logger.info("Request Entering for Deleting  comment  where commentId{}", +commentId);
		this.commentService.DeleteComment(commentId);

		logger.info("comment Deleted Successfully  where commentId{}", +commentId);
		return new ResponseEntity<String>(Appconstants.USER_DELETE, HttpStatus.OK);
	}

}
