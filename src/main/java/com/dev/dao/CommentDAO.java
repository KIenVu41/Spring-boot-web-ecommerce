package com.dev.dao;

import java.util.List;

import com.dev.entity.Comment;

public interface CommentDAO {
	public List<Comment> search(String name, int start, int length);
	
	public List<Comment> listAll();
	
	public void add(Comment comment);
	
	public void update(Comment comment);
	
	public void delete(Comment comment);
	
	public int count();
	
	public Comment findById(int id);
	
	public List<Comment> findByProductId(int id);
}
