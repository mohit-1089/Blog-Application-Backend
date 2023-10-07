package com.mohitrathour1213.blog.services.impl;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mohitrathour1213.blog.entities.Category;
import com.mohitrathour1213.blog.exceptions.ResourceNotFoundException;
import com.mohitrathour1213.blog.payloads.CategoryDto;
import com.mohitrathour1213.blog.repositories.CategoryRepo;
import com.mohitrathour1213.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		
		Category cat = this.modelMapper.map(categoryDto, Category.class);
		Category savedCat = this.categoryRepo.save(cat); 
		
		return this.modelMapper.map(savedCat, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(int categoryId, CategoryDto categoryDto) {
		
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category","Category Id", categoryId));
		
		categoryDto.setCategoryId(categoryId);
		cat=this.modelMapper.map(categoryDto, Category.class);
		Category updatedCategory = this.categoryRepo.save(cat);
		
	    return this.modelMapper.map(updatedCategory,CategoryDto.class);
		
	}

	@Override
	public CategoryDto getCategory(int categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category","Id", categoryId));
	
		
		return this.modelMapper.map(cat, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getCategory() {
		
		List<Category> allCat = this.categoryRepo.findAll();
		
		List<CategoryDto> allCatDto = allCat.stream().map(cat-> this.modelMapper.map(cat, CategoryDto.class)).collect(Collectors.toList());
		return allCatDto;
	}

	@Override
	public void deleteCategory(int categoryId) {
		
		Category cat = this.categoryRepo.findById(categoryId).
				orElseThrow(()-> new ResourceNotFoundException("Category","Category Id", categoryId));
		
		this.categoryRepo.delete(cat);
		
	}

}
