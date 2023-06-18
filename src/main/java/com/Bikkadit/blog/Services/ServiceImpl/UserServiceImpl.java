package com.Bikkadit.blog.Services.ServiceImpl;

import java.util.List;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Bikkadit.blog.Repositories.RoleRepo;
import com.Bikkadit.blog.Repositories.UserRepo;
import com.Bikkadit.blog.Services.UserService;
import com.Bikkadit.blog.config.Appconstants;

import com.Bikkadit.blog.entities.Roles;
import com.Bikkadit.blog.entities.User;
import com.Bikkadit.blog.exceptions.ResourceNotFoundException;
import com.Bikkadit.blog.payloads.UserDto;
import com.Bikkadit.blog.payloads.UserResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepo userrepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private PasswordEncoder passwordEncoder;
    @Autowired
	private RoleRepo roleRepo;
	/**
	 * @author Pallavi Yeola
	 * @apiNote This api is used to registered New User
	 */
	@Override
	public UserDto RegisterNewUser(UserDto userDto) {
		
		log.info("Enterging Into Persistence to Register New User");
		User user = this.mapper.map(userDto, User.class);
		
		//Encode our Password
		log.info("Encode Plain Password provided by user");
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//How To paly with Roles
		Roles role = this.roleRepo.findById(Appconstants.NORMAL_USER).get();
	
		user.getRoles().add(role);
		User newUSer = this.userrepo.save(user);
		UserDto userDto2 = this.mapper.map(newUSer, UserDto.class);
		
		log.info("User Registered Successfully");
		return userDto2;
	}
	@Override
	public UserDto CreateUser(UserDto userDto) {
		log.info("Enterging Into Persistence to create User");
		User user = this.DtotoUser(userDto);

		User saveuser = userrepo.save(user);

		UserDto usertoDto = this.usertoDto(saveuser);
		log.info("User Created Successfully");
		return usertoDto;
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		log.info("Enterging Persistence  to update  User with userId :{}", userId);
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updateduser = this.userrepo.save(user);
		UserDto usertoDto = this.usertoDto(updateduser);
		log.info("Successfully Updated  User with userId :{}", userId);
		return usertoDto;
	}
	@Override
	public UserDto getUserById(Integer userId) {
		log.info("Enterging Persistence to get User with userId :{}", userId);
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));

		log.info("Get User with userId :{}", userId);
		return this.usertoDto(user);
	}

	@Override
	public List<UserDto> getAlluser() {
		log.info("Enterging Persistence to get All Users ");
		List<User> findAll = userrepo.findAll();
		List<UserDto> list = findAll.stream().map((user) -> this.usertoDto(user)).collect(Collectors.toList());
		log.info("All users Get Successfully ");
		return list;
	}

	@Override
	public void deleteUserById(Integer userId) {
		log.info("Enterging Persistence to Delete User with userId :{}", userId);
		User user = userrepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "id", userId));
		this.userrepo.delete(user);
		log.info("Successfully deleted User with userId :{}", userId);
	}

	public User DtotoUser(UserDto userdto) {
		User user2 = this.mapper.map(userdto, User.class);
//		User user = new User();
//		user.setUserid(userdto.getUserid());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
		return user2;

	}

	public UserDto usertoDto(User user) {

		UserDto userDto = this.mapper.map(user, UserDto.class);
//		UserDto userdto = new UserDto();
//		userdto.setUserid(user.getUserid());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setPassword(user.getPassword());
//		userdto.setAbout(user.getAbout());
		return userDto;

	}

	

	@Override
	public UserResponse getAllUserResponse(Integer pageNumber, Integer pageSize) {
		log.info("Enterging Persistence to get All Users ");
		
	PageRequest of = PageRequest.of(pageNumber, pageSize);
Page<User> page = userrepo.findAll(of);
List<User> content = page.getContent();
		List<UserDto> list = content.stream().map((user) -> this.usertoDto(user)).collect(Collectors.toList());
		log.info("All users Get Successfully ");
		UserResponse userResponse=new UserResponse();
		userResponse.setContent(list);
		userResponse.setPageNumber(page.getNumber());
		userResponse.setPageSize(page.getSize());
		userResponse.setTotalElements(page.getTotalElements());
		userResponse.setTotalPages(page.getTotalPages());
		userResponse.setLastpage(page.isLast());
		return userResponse;
	}

	

}
