package com.Bikkadit.blog.Controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Bikkadit.blog.Services.FileService;
import com.Bikkadit.blog.Services.PostService;
import com.Bikkadit.blog.config.Appconstants;
import com.Bikkadit.blog.payloads.PostDto;
import com.Bikkadit.blog.payloads.PostResponse;

@RestController
@RequestMapping("/api/")
public class PostController {

	Logger logger = LoggerFactory.getLogger(PostController.class);

	@Autowired
	private PostService postservice;

	@Autowired
	private FileService fileService;

	@Value("${project.image}")
	private String path;

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to create Post
	 * @param postDto
	 * @param userId
	 * @param categoryId
	 * @return
	 */
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId,
			@PathVariable Integer categoryId) {
		logger.info("Request Entering for Creating New Record in  Post by User with userId {} and for categoryId {}", userId,categoryId);
		PostDto createpost = postservice.Createpost(postDto, userId, categoryId);
		logger.info("Post Created Successfully by user With userId {}", userId);
		return new ResponseEntity<PostDto>(createpost, HttpStatus.CREATED);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to Get Post By particular User
	 * @param userId
	 * @return
	 */
	@GetMapping("/user/{userId}/posts")
	public ResponseEntity<List<PostDto>> getPostByuser(@PathVariable Integer userId) {
		logger.info("Request Entering to get Records for post With userId:{}", userId);
		List<PostDto> postByUser = this.postservice.getPostByUser(userId);
		logger.info("Record Get Successfully with userId):{}", userId);
		return new ResponseEntity<List<PostDto>>(postByUser, HttpStatus.OK);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to Get Post By Category
	 * @param categoryId
	 * @return
	 */
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		logger.info("Request Entering to get Records for Post With CategoryId:{}", categoryId);
		List<PostDto> postByCategory = this.postservice.getPostByCategory(categoryId);
		logger.info("Record Get Successfully with categoryId:{}", categoryId);
		return new ResponseEntity<List<PostDto>>(postByCategory, HttpStatus.OK);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to Get All Post By Some Sorting Order In page
	 *          Format
	 * @param pageNumber
	 * @param pageSize
	 * @param sortBy
	 * @param sortDir
	 * @return
	 */
	@GetMapping("/posts")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = Appconstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = Appconstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = Appconstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = Appconstants.SORT_DIR, required = false) String sortDir) {
		logger.info("Request Entering to get  all Records for Post ");
		PostResponse allPostRespnse = this.postservice.getAllPostRespnseBysort(pageNumber, pageSize, sortBy, sortDir);
		logger.info("Successfully get  all Records from Post ");
		return new ResponseEntity<PostResponse>(allPostRespnse, HttpStatus.OK);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to Get Post by PostId
	 * @param postId
	 * @return
	 */
	@GetMapping("/posts/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		logger.info("Request Entering to get Records for Post By PostId:{} ", postId);
		PostDto postById = this.postservice.getPostById(postId);
		logger.info("Successfully get   Record from Post By PostId ");
		return new ResponseEntity<PostDto>(postById, HttpStatus.OK);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to Delete Post By PostId
	 * @param postId
	 * @return
	 */
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> DeletePostById(@PathVariable Integer postId) {
		logger.info("Request Entering to Delete Records for Post By PostId:{} ", postId);
		this.postservice.DeletePostById(postId);
		logger.info("Record Deleted Successfully for Post With PostId:{} ", postId);
		return new ResponseEntity<String>(Appconstants.USER_DELETE, HttpStatus.OK);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to Update Post By PostId
	 * @param postDto
	 * @param postId
	 * @return
	 */
	@PutMapping("/posts/{postId}")
	public ResponseEntity<PostDto> UpdatePostById(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId) {
		logger.info("Request Entering to Update Records for Post By PostId:{} ", postId);
		PostDto updatePost = this.postservice.updatePost(postDto, postId);
		logger.info("Record updated Successfully for Post With PostId:{} ", postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to get Post by KeyWord
	 * @param keywords
	 * @return
	 */
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords) {
		List<PostDto> postByKeyword = this.postservice.getPostByKeyword(keywords);
		return new ResponseEntity<List<PostDto>>(postByKeyword, HttpStatus.OK);

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to upload Image in Post
	 * @param image
	 * @param postId
	 * @return
	 * @throws IOException
	 */
	@PostMapping("/posts/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(@RequestPart("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException {

		PostDto postDto = this.postservice.getPostById(postId);

		String uploadImage = this.fileService.UploadImage(path, image);
		postDto.setImagename(uploadImage);
		PostDto updatePost = this.postservice.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is Used to Download Image from Post
	 * @param imageName
	 * @param response
	 * @throws IOException
	 */
	@GetMapping(value = "/post/image/{imagename}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void Download(@PathVariable("imagename") String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getResource(path, imageName);
		StreamUtils.copy(resource, response.getOutputStream());
	}

	// @GetMapping("/posts")
//	public ResponseEntity<List<PostDto>> getAllPost() {
//		logger.info("Request Entering to get  all Records for Post ");
//		List<PostDto> getAllPost = this.postservice.getAllPost();
//		logger.info("Successfully get  all Records from Post ");
//		return new ResponseEntity<List<PostDto>>(getAllPost, HttpStatus.OK);
//
//	}

//	@GetMapping("/posts")
//	public ResponseEntity<List<PostDto>> getAllPost
//	(@RequestParam(value="pageNumber",defaultValue="0",required = false) Integer pageNumber,
//	@RequestParam(value="pageSize",defaultValue = "5",required = false )Integer pageSize) {
//		logger.info("Request Entering to get  all Records for Post ");
//		List<PostDto> getAllPost = this.postservice.getAllPost(pageNumber,pageSize);
//		logger.info("Successfully get  all Records from Post ");
//		return new ResponseEntity<List<PostDto>>(getAllPost, HttpStatus.OK);
//
//	}

//	@GetMapping("/posts")
//	public ResponseEntity<PostResponse> getAllPost
//	(@RequestParam(value="pageNumber",defaultValue="0",required = false) Integer pageNumber,
//	@RequestParam(value="pageSize",defaultValue = "10",required = false )Integer pageSize) {
//		logger.info("Request Entering to get  all Records for Post ");
//	PostResponse allPostRespnse = this.postservice.getAllPostRespnse(pageNumber, pageSize);
//		logger.info("Successfully get  all Records from Post ");
//		return new ResponseEntity<PostResponse>(allPostRespnse, HttpStatus.OK);
//
//	}

}
