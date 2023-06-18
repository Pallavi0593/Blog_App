package com.Bikkadit.blog.Controllers;




import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Bikkadit.blog.Services.UserService;
import com.Bikkadit.blog.config.Appconstants;
import com.Bikkadit.blog.payloads.UserDto;
import com.Bikkadit.blog.payloads.UserResponse;

@RestController
@RequestMapping("/api")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserService userservice;

//post create user

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to Create User
	 * @param userdto
	 * @return
	 */
	@PostMapping("/User")
	public ResponseEntity<UserDto> createuser(@Valid @RequestBody UserDto userdto) {
		logger.info("Request Entering for Creating New Record in User");
		UserDto createUser = this.userservice.CreateUser(userdto);
		logger.info("Record inserted Successfully");
		return new ResponseEntity<UserDto>(createUser, HttpStatus.CREATED);
	}

	// put update user
	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to Update User
	 * @param userdto
	 * @param userId
	 * @return updated user
	 */
	@PutMapping("/User/{userId}")
	public ResponseEntity<UserDto> updateuser(@Valid @RequestBody UserDto userdto, @PathVariable Integer userId) {

		logger.info("Request Entering for updating Existing Record in  user with userId :{}", userId);
		UserDto updateUser = this.userservice.updateUser(userdto, userId);
		logger.info("Record Updated  Successfully with userId:{}", userId);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.CREATED);

	}

	// delete user
	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to Delete User
	 * @param userId
	 * @return
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<String> Deleteuser(@PathVariable Integer userId) {
		logger.info("Request Entering to delete Records from User With userid:{}", userId);
		this.userservice.deleteUserById(userId);
		logger.info("Record  delete Successfully  from user With userid:{}", userId);
		return new ResponseEntity<>(Appconstants.USER_DELETE, HttpStatus.OK);
	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to get User By Id
	 * @param userId
	 * @return
	 */
	@GetMapping("/User/{userId}")
	public ResponseEntity<UserDto> getuserByid(@PathVariable Integer userId) {
		logger.info("Request Entering to get Records from User With userid:{}", userId);
		logger.info("Successfully get Records from User With userid:{}", userId);
		return ResponseEntity.ok(this.userservice.getUserById(userId));

	}

	/**
	 * @author PALLAVI YEOLA
	 * @apiNote This Api is used to get User By Id
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	@GetMapping("/Users")
	public ResponseEntity<UserResponse> getAlluserResponse(
			@RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "10", required = false) Integer pageSize) {

		logger.info("Request Entering to get All Records from User");

		logger.info("All records Get Successfully");
		return ResponseEntity.ok(this.userservice.getAllUserResponse(pageNumber, pageSize));
	}

//	@DeleteMapping("/User/{userid}")
//	public ResponseEntity<ApiResponse> Deleteuser(@PathVariable Integer userid) {
//		this.userservice.deleteUserById(userid);
//     //return ResponseEntity.ok(Map.of("Message","User deleted Successfully"));
//		// return new ResponseEntity(Map.of("Message","User deleted Successfully"),HttpStatus.OK);
//
//		return new ResponseEntity<ApiResponse>(new ApiResponse("Record Deleted Successfully", true), HttpStatus.OK);
//	}

	// Get Get al user
//	@GetMapping("/Users")
//	public ResponseEntity<List<UserDto>> getAlluser() {
//		
//		logger.info("Request Entering to get All Records from User");
//		
//		logger.info("All records Get Successfully");
//		return ResponseEntity.ok(this.userservice.getAlluser());
//	}
}
