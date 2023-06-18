package com.Bikkadit.blog.Controllers;



import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Bikkadit.blog.Repositories.UserRepo;
import com.Bikkadit.blog.Security.JwtTokenHelper;
import com.Bikkadit.blog.Security.SecurityConstant;
import com.Bikkadit.blog.Services.UserService;
import com.Bikkadit.blog.entities.User;
import com.Bikkadit.blog.exceptions.InvalidUserNameAndPasswordException;
import com.Bikkadit.blog.payloads.JwtAuthRequest;
import com.Bikkadit.blog.payloads.JwtAuthResponse;
import com.Bikkadit.blog.payloads.UserDto;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserService userService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private ModelMapper mapper;
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> createToken(@RequestBody JwtAuthRequest request) throws Exception {
		logger.info("Enter userName and Password from Client and Call Authenticate Method");
		this.authenticate(request.getUserName(), request.getPassword());

		logger.info("Enter into UserDetail Service to load UserDetails using UserName");

		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUserName());
		User user = this.userRepo.findByEmail(request.getUserName()).get();
		UserDto userDto = this.mapper.map(user, UserDto.class);
		
		logger.info("By Using user Details to GenerateToken Call  Method from JetTokenHelper  ");
		String token = this.jwtTokenHelper.generateToken(userDetails);

		logger.info("Generated Token to set As A Response ");
		JwtAuthResponse response = new JwtAuthResponse();
		response.setToken(token);
		response.setUser(userDto);

		logger.info("Will get token As Response  ");
		
		return new ResponseEntity<JwtAuthResponse>(response, HttpStatus.OK);

	}

	private void authenticate(String userName, String password) throws Exception {

		logger.info("To AthenticateToken Call UserNamePasswordAuthenticationToken ");
		UsernamePasswordAuthenticationToken athenticationToken = new UsernamePasswordAuthenticationToken(userName,
				password);
		
		try {
			logger.info("Pass Token to Authenticate User");
			
			this.authenticationManager.authenticate(athenticationToken);
			
		} catch (BadCredentialsException e) {
			System.out.println("Invalid Details");

			throw new InvalidUserNameAndPasswordException(SecurityConstant.INVALID_USERNAME_PASSWORD);
		}
	}
/**
 * @author Pallavi Yeola
 * @apiNote This Api is used To Registered New User
 * @param userDto
 * @return
 */
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@Valid @RequestBody UserDto userDto) {
		
		logger.info("Entering into UserService Layer To register new User");
		UserDto registerNewUser = this.userService.RegisterNewUser(userDto);
		logger.info("User Registered Successfully");
		return new ResponseEntity<UserDto>(registerNewUser, HttpStatus.CREATED);

	}
}
