package com.dev.service;

import java.util.List;

import com.dev.model.CommentDTO;

public interface CommentService {
	public List<CommentDTO> search(String name, int start, int length);
	
	public List<CommentDTO> listAll();
	
	public List<CommentDTO> findByProductId(int id);
	
	public CommentDTO findById(int id);
	
	public void add(CommentDTO comment);
	
	public void update(CommentDTO comment);
	
	public void delete(int id);
	
	public int count();
}
