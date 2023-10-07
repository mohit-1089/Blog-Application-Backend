package com.mohitrathour1213.blog.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends RuntimeException{
	
	String resourceName;
	String fieldName;
	long id;
	
	
	public ResourceNotFoundException(String resourceName, String fieldName, long id) {
		super(String.format("%s not found with this %s : %s", resourceName,fieldName,id));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.id = id;
	}
}
