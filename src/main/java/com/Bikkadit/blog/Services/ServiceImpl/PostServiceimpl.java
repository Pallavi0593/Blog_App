package com.Bikkadit.blog.Services.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.Bikkadit.blog.Repositories.CategoryRepo;
import com.Bikkadit.blog.Repositories.PostRepo;
import com.Bikkadit.blog.Repositories.UserRepo;
import com.Bikkadit.blog.Services.PostService;
import com.Bikkadit.blog.entities.Category;
import com.Bikkadit.blog.entities.Post;
import com.Bikkadit.blog.entities.User;
import com.Bikkadit.blog.exceptions.ResourceNotFoundException;
import com.Bikkadit.blog.payloads.PostDto;
import com.Bikkadit.blog.payloads.PostResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class PostServiceimpl implements PostService {

	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PostRepo postRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private UserRepo userrepo;

	@Override
	public PostDto Createpost(PostDto postDto, Integer userId, Integer categoryId) {
		log.info("Enterging Into Persistence to create Post");
		User user = userrepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Userid", userId));

		Category catogery = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "categoryid", categoryId));

		Post post = this.mapper.map(postDto, Post.class);
		post.setImagename("default.png");
		// post.setImagename(postDto.getImagename());
		post.setAddedDate(new Date());
		post.setCategory(catogery);
		post.setUser(user);

		Post post2 = postRepo.save(post);
		log.info("Post Added Successfully");
		log.info("Created Post in database return to Controller");
		return this.mapper.map(post2, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postdto, Integer postId) {
		log.info("Enterging Into Persistence to Update Post with postId:{}", postId);
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		post.setTitle(postdto.getTitle());
		post.setContent(postdto.getContent());
		post.setImagename(postdto.getImagename());
		Post post2 = this.postRepo.save(post);

		PostDto postDto2 = this.mapper.map(post2, PostDto.class);
		log.info("Post Updated Sucessfully with postId:{}", postId);
		return postDto2;
	}

	@Override
	public void DeletePostById(Integer postId) {
		log.info("Enterging Into Persistence to Delete Post with postId:{}", postId);
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("post", "postId", postId));
		this.postRepo.delete(post);
		log.info("Delete Post Successfully");
	}

	@Override
	public PostDto getPostById(Integer postId) {
		log.info("Enterging Into Persistence to Get Post with postId:{}", postId);
		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "PostId", postId));
		log.info("Post Return Successfully with postId:{}", postId);
		return this.mapper.map(post, PostDto.class);

	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		log.info("Enterging Into Persistence to Get Post with userId:{}", userId);
		User user = this.userrepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Userid", userId));
		List<Post> list = this.postRepo.findByUser(user);
		List<PostDto> collect = list.stream().map((u) -> this.mapper.map(u, PostDto.class))
				.collect(Collectors.toList());
		log.info("Successfully return  Post with userId:{}", userId);
		return collect;
	}

	@Override
	public List<PostDto> getPostByCategory(Integer CategoryId) {
		log.info("Enterging Into Persistence to Get Post with CategoryId:{}", CategoryId);
		Category category = this.categoryRepo.findById(CategoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "Categoryid", CategoryId));
		List<Post> list = this.postRepo.findByCategory(category);
		List<PostDto> collect = list.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
		log.info("Successfully return  Post with CategoryId:{}", CategoryId);
		return collect;
	}

	@Override
	public List<PostDto> getPostByKeyword(String keyword) {
		List<Post> listOfpost = this.postRepo.findByTitleContaining(keyword);
		List<PostDto> listofPoseDto = listOfpost.stream().map((post) -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		return listofPoseDto;
	}
// Get All Users
//	@Override
//	public List<PostDto> getAllPost() {
//		log.info("Enterging Into Persistence to Get  All Post ");
//		List<Post> list = postRepo.findAll();// to get All records
//		List<PostDto> list2 = list.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
//		log.info("Successfully Fetch All Records from database  ");
//		return list2;
//	}

	// Get All users using Pages
	@Override
	public List<PostDto> getAllPost(Integer pageNumber, Integer pageSize) {
		log.info("Enterging Into Persistence to Get  All Post in Page form ");
		// int pageSize=5; //How many records Are on one page
		// int pageNumber=1;//provide Static Value

		PageRequest of = PageRequest.of(pageNumber, pageSize);
		Page<Post> findAll = postRepo.findAll(of);
		List<Post> list = findAll.getContent();

		List<PostDto> list2 = list.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
		log.info("Successfully Fetch All Records from database  ");
		return list2;
	}

	@Override
	public PostResponse getAllPostRespnse(Integer pageNumber, Integer pageSize) {
		log.info("Enterging Into Persistence to Get  All Post in Page form ");

		PageRequest of = PageRequest.of(pageNumber, pageSize);
		Page<Post> page = postRepo.findAll(of); // return page of post
		List<Post> list = page.getContent();

		List<PostDto> list2 = list.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
		log.info("Successfully Fetch All Records from database  ");
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(list2);
		postResponse.setPageNumber(page.getNumber()); // return no of current Slices
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements()); // total elements from page
		postResponse.setTotalPages(page.getTotalPages()); // total pages to store all records
		postResponse.setLastpage(page.isLast()); // is current page is last or not
		return postResponse;

	}

	@Override
	public PostResponse getAllPostRespnseBysort(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		log.info("Enterging Into Persistence to Get  All Post in Page form ");

		Sort sort = (sortDir.equalsIgnoreCase("asc")) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

		PageRequest of = PageRequest.of(pageNumber, pageSize, sort);

		Page<Post> page = postRepo.findAll(of); // return page of post
		List<Post> list = page.getContent();

		List<PostDto> list2 = list.stream().map((p) -> mapper.map(p, PostDto.class)).collect(Collectors.toList());
		log.info("Successfully Fetch All Records from database  ");
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(list2);
		postResponse.setPageNumber(page.getNumber()); // return no of current Slices
		postResponse.setPageSize(page.getSize());
		postResponse.setTotalElements(page.getTotalElements()); // total elements from page
		postResponse.setTotalPages(page.getTotalPages()); // total pages to store all records
		postResponse.setLastpage(page.isLast()); // is current page is last or not
		return postResponse;

	}
}
