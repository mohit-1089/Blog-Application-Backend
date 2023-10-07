package com.mohitrathour1213.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

import com.mohitrathour1213.blog.exceptions.AppException;
import com.mohitrathour1213.blog.payloads.JwtAuthRequest;
import com.mohitrathour1213.blog.payloads.JwtAuthResponse;
import com.mohitrathour1213.blog.payloads.UserDto;
import com.mohitrathour1213.blog.security.JwtTokenHelper;
import com.mohitrathour1213.blog.services.UserService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> creatToken(@RequestBody JwtAuthRequest request){
		this.authenticate(request.getUsername(), request.getPassword());
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		String token = this.jwtTokenHelper.generateToken(userDetails);
		JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
		jwtAuthResponse.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse,HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto){
		UserDto newUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(newUser,HttpStatus.CREATED);
	}
	
	private void authenticate(String username, String password){
		UsernamePasswordAuthenticationToken authernticationToken = new UsernamePasswordAuthenticationToken(username, password);
	    try {
	    	this.authenticationManager.authenticate(authernticationToken);
	    }
	    catch (BadCredentialsException e){
	    	throw new AppException("Invalid User Name or Password !!");
	    }	
	}
}
