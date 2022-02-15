package com.dev.service;

import java.util.List;

import com.dev.model.ReviewDTO;

public interface ReviewService {
	public List<ReviewDTO> search(String name, int start, int length);
	
	public List<ReviewDTO> listAll();
	
	public void add(ReviewDTO reviewDTO);
	
	public void update(ReviewDTO reviewDTO);
	
	public void delete(int id);
	
	public ReviewDTO findById(int id);
	
	public int count();
}
