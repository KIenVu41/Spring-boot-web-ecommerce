package com.dev.service;

import java.util.List;

import com.dev.model.CategoryDTO;

public interface CategoryService {
	public List<CategoryDTO> search(String name, int start, int length);
	
	public List<CategoryDTO> listAll();
	
	public void add(CategoryDTO categoryDTO);
	
	public void update(CategoryDTO categoryDTO);
	
	public void delete(int id);
	
	public CategoryDTO getCategoryById(int id);
	
	public int count();
}
