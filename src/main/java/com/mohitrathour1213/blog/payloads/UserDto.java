package com.mohitrathour1213.blog.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty(message = "Name can't be empty")
	private String name;
	
	@NotEmpty
	@Size(min = 4,message = "Password must be greater than 4 character")
	private String password;
	
	@NotEmpty
	private String about;
	
	@Email
	private String email;

}
