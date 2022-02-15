package com.dev.dao;

import java.util.List;

import com.dev.entity.Category;

public interface CategoryDAO {
	public int count();
	
	public List<Category> search(String name, int start, int length);
	
	public List<Category> listAll();
	
	public void add(Category category);
	
	public void update(Category category);
	
	public void delete(Category category);
	
	public Category getCategoryById(int id);
}
