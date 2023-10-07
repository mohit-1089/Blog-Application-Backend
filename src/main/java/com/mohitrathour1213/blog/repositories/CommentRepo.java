package com.mohitrathour1213.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohitrathour1213.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment,Integer> {
	

}
