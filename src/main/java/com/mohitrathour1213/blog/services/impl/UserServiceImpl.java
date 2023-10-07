package com.mohitrathour1213.blog.services.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.mohitrathour1213.blog.exceptions.*;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mohitrathour1213.blog.config.AppConstants;
import com.mohitrathour1213.blog.entities.User;
import com.mohitrathour1213.blog.payloads.UserDto;
import com.mohitrathour1213.blog.repositories.RoleRepo;
import com.mohitrathour1213.blog.repositories.UserRepo;
import com.mohitrathour1213.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	// create user
	@Override
	public UserDto createUser(UserDto userDto) {
		
		User user = this.dtoToUser(userDto);
		User savedUser= this.userRepo.save(user);
		return this.userToDto(savedUser);
	}

	// update user
	
	@Override
	public UserDto updateUser(UserDto userDto, int userId) { 
		
		User user = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",userId)); 
		
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setEmail(userDto.getEmail());
		user.setAbout(userDto.getAbout());
		
		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);
		
		
	}
	
	//get userById

	@Override
	public UserDto getUserById(int id) {
		
		User user = this.userRepo.findById(id)
				.orElseThrow(()-> new ResourceNotFoundException("User","id",id));
		
		
		return this.userToDto(user);
	}
	
	// getAllUser

	@Override
	public List<UserDto> getAllUsers() {
		
		List<User> users = this.userRepo.findAll();
		
		List<UserDto> userDtos = users.stream().map(user-> this.userToDto(user)).collect(Collectors.toList());
		return userDtos;
	}

	// delete user
	
	@Override
	public void deleteUser(int id) {
		
		
		User user = this.userRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("User", "id", id));
		
		this.userRepo.delete(user);
		
	}
	
	private User dtoToUser(UserDto userDto)
	{
	    User user = this.modelMapper.map(userDto, User.class);	    
	    return user;
	}
	
	private UserDto userToDto(User user)
	{
		UserDto userDto= this.modelMapper.map(user, UserDto.class);
		
		return userDto;
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		user.setRoles(Set.of(this.roleRepo.findById(AppConstants.NORMAL_USER).get()));
		User createdUser=this.userRepo.save(user);
		System.out.println(user.getPassword());
		return this.modelMapper.map(createdUser, UserDto.class);
	}

}
