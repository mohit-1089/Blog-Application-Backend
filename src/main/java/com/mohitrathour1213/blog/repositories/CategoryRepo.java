package com.mohitrathour1213.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mohitrathour1213.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer> {
	

}
