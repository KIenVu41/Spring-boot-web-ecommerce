package com.dev.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dev.dao.CategoryDAO;
import com.dev.entity.Category;
import com.dev.model.CategoryDTO;
import com.dev.service.CategoryService;

@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	CategoryDAO categoryDAO;

	@Override
	public List<CategoryDTO> search(String name, int start, int length) {
		// TODO Auto-generated method stub
		List<Category> categories = categoryDAO.search(name, start, length);
		List<CategoryDTO> categoriesDTO = new ArrayList<CategoryDTO>();
		
		for(Category c: categories) {
			CategoryDTO categoryDTO = new CategoryDTO();
			
			categoryDTO.setId(c.getId());
			categoryDTO.setName(c.getName());
			
			categoriesDTO.add(categoryDTO);
		}
		return categoriesDTO;
	}

	@Override
	public void add(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		Category category = new Category();
		
		category.setId(categoryDTO.getId());
		category.setName(categoryDTO.getName());
		
		categoryDAO.add(category);
	}

	@Override
	public void update(CategoryDTO categoryDTO) {
		// TODO Auto-generated method stub
		Category category = categoryDAO.getCategoryById(categoryDTO.getId());
		if(category != null) {
			category.setId(categoryDTO.getId());
			category.setName(categoryDTO.getName());
			
			categoryDAO.update(category);
		}	
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		Category category = categoryDAO.getCategoryById(id);
		if(category != null) {
			categoryDAO.delete(category);
		}	
	}

	@Override
	public CategoryDTO getCategoryById(int id) {
		// TODO Auto-generated method stub
		Category category = categoryDAO.getCategoryById(id);
		
		CategoryDTO categoryDTO = new CategoryDTO();
		
		categoryDTO.setId(category.getId());
		categoryDTO.setName(category.getName());
		
		return categoryDTO;
	}

	@Override
	public int count() {
		// TODO Auto-generated method stub
		return categoryDAO.count();
	}

	@Override
	public List<CategoryDTO> listAll() {
		// TODO Auto-generated method stub
		List<Category> categories = categoryDAO.listAll();
		List<CategoryDTO> categoriesDTO = new ArrayList<CategoryDTO>();
		
		for(Category c: categories) {
			CategoryDTO categoryDTO = new CategoryDTO();
			
			categoryDTO.setId(c.getId());
			categoryDTO.setName(c.getName());
			
			categoriesDTO.add(categoryDTO);
		}
		return categoriesDTO;
	}
	
}
