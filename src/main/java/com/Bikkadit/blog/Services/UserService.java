package com.Bikkadit.blog.Services;

import java.util.List;

import com.Bikkadit.blog.payloads.UserDto;
import com.Bikkadit.blog.payloads.UserResponse;

public interface UserService {
	public UserDto RegisterNewUser(UserDto userDto);

	public UserDto CreateUser(UserDto userdto);
	
	public UserDto updateUser(UserDto userDto,Integer userId);
	
	public UserDto getUserById(Integer userId);
	
	public List<UserDto> getAlluser();
	
 public void deleteUserById(Integer userId);
 
 public UserResponse getAllUserResponse(Integer pageNumber,Integer pageSize);
}
