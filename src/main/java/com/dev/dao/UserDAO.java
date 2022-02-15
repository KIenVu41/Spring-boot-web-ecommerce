package com.dev.dao;

import java.util.List;

import com.dev.entity.User;

public interface UserDAO {
	public int count();
	
	public List<User> search(String name, int start, int length);
	
	public List<User> listAll();
	
	public void add(User user);
	
	public void update(User user);
	
	public void delete(User user);
	
	public User getUserById(int id);
	
	public User getUserByUsername(String username);
	
	public User findByEmail(String email);
	
	public void changeEnable(int id, boolean status);
	
}
