package com.mohitrathour1213.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mohitrathour1213.blog.entities.Category;
import com.mohitrathour1213.blog.entities.Post;
import com.mohitrathour1213.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByCategory(Category category);
	
	List<Post> findByUser(User user);
	
	List<Post> findByTitleContaining(String title);

}
