package com.dev.dao;

import java.util.List;

import com.dev.entity.Review;

public interface ReviewDAO {
	public int count();
	
	public List<Review> search(String name, int start, int length);
	
	public List<Review> listAll();
	
	public void add(Review review);
	
	public void update(Review review);
	
	public void delete(Review review);
	
	public Review findById(int id);
}
