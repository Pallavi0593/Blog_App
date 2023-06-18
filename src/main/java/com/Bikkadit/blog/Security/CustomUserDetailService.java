package com.Bikkadit.blog.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Bikkadit.blog.Repositories.UserRepo;

import com.Bikkadit.blog.entities.User;
import com.Bikkadit.blog.exceptions.ResourceNotFoundException;
@Service
public class CustomUserDetailService implements UserDetailsService{
	@Autowired
	private UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		//loading user from Database From Username
		User user = this.userRepo.findByEmail(username).orElseThrow(()->new ResourceNotFoundException("User","email:"+username, 0));
		
		return user;
	}

}
