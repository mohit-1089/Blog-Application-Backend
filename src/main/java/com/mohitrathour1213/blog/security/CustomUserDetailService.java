package com.mohitrathour1213.blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.mohitrathour1213.blog.exceptions.ResourceNotFoundException;
import com.mohitrathour1213.blog.repositories.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepo userRepo;
	 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// load user from DB using the user name (here email)
		return this.userRepo.findByEmail(username).orElseThrow(()-> new ResourceNotFoundException("User","Email "+username,0));
	}
}
